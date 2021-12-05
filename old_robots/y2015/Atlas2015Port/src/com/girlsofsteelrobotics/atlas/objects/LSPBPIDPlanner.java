/*
 * A planner class that will connect position PID with velocity PID.
 * See LSPB for more information

 Point a refers to when the robot should stop accelerating and the velocity
 graph reaches the flat part. Point b refers to when the robot should start
 deccelerating and slants back down. Point c is the last point of the velocity
 trapezoid and is also the base of the trapezoid.

 ToDO Prepare for special case when total distance is less than 1.5(?)

 */
package com.girlsofsteelrobotics.atlas.objects;

/**
 *
 * @author Sylvie
 */
public class LSPBPIDPlanner {

    private double velocity;
    private final double acceleration;
    private boolean negativeSetpoint;
    private double desiredOverallDistance;
    private double lastAccelerating;
    private double lastConstant;
    private double pointA; //time when graph changes from accelerating to constant
    private double pointB;  //time when graph changes from constant to decelerating
    private double pointC; //time when graph ends
    private double trianglePoint;
    private double triangleEndpoint;
    private double yInterceptOfDecelerating;
    private boolean triangle = false;

    /*
     This constructor defaults the velocity and acceleration
     */
    public LSPBPIDPlanner() {
        //To use the LSPB You must first 1.) call calculateVelocityGraph()
        //2.) Call getDesiredPosition() in some loop getting updated times
        acceleration = 6; //The default is the chassis's acceleration
        //System.out.println("Changed Acceleration!: " + acceleration);
        velocity = 3;
        }

    public LSPBPIDPlanner(double acceleration) {
        this.acceleration = acceleration;
    }

    /*
     Sets the values of pointA, pointB, and pointC and also puts together the
     velocity graph.

     D = (x+y)v
     D = ((v/a) + y)v
     D/v = (v/a) + y
     D/v - (v/a) = B - A
     B = D/v

     x = A
     y = B-A
     x = (v/a)

     */
    public void calculateVelocityGraph(double desiredDistance) {
//        pointA = (velocity/acceleration);
//        pointB = (desiredDistance/velocity);
//        pointC = pointA + pointB;
        if (desiredDistance < 0) {
            negativeSetpoint = true;
            desiredDistance *= -1;
        } else {
            negativeSetpoint = false;
        }
        desiredOverallDistance = desiredDistance;
        if (desiredOverallDistance > 1.5) { //based off of the chassis case
            //System.out.println("Velocity: " + velocity + "\tacceleration: " + acceleration);
            pointC = (desiredOverallDistance / velocity) + (velocity / acceleration);
            pointB = pointC - (velocity) / acceleration;
            pointA = pointC - pointB;
            yInterceptOfDecelerating = velocity + (acceleration * pointB);
            //System.out.println("Point A: " + pointA + "\tPointB: " + pointB + "\tPointC: " + pointC + "\tDesiredDistance: " + desiredOverallDistance);
        } else {
            triangle = true;
            specialTriangleCase();
            //System.out.println("Top of Triangle: " + trianglePoint + "\tEnd of the Triangle: " + triangleEndpoint);
        }
    }

    /*
     Returns the corresponding position setpoint given time
     */
    public double getDesiredPosition(double time) {
        time /= 1000; //To get milliseconds into seconds
        //System.out.println("Time in seconds: " + time);
        if (triangle) {
            if (time < trianglePoint) {
                return accelerating(time);
            } else {
                return decelerating(time);
            }
        } else {
            if (time < pointA) {
                return accelerating(time);
            } else if (time >= pointA && time < pointB) {
                return constantVelocity(time);
            } else {
                return decelerating(time);
            }
        }
    }

    /*
     Integrates the accelerating part of the velocity graph to get position
     from time 0 to pointA
     */
    private double accelerating(double time) {
        //The integral of v(t) = at where a is some constant (your acceleration)
        double setpoint = ((time * time) * acceleration) / (2.0);
        lastAccelerating = setpoint;
        //System.out.println("In accelerating zone! Sent this setpoint: " + setpoint);
        if (negativeSetpoint) {
            setpoint *= -1;
        }
        return setpoint;
    }

    /*
     Integrates the constant part of the velocity graph to get position from
     pointA to pointB
     */
    private double constantVelocity(double time) {
        //The integral of v(t) = v where v is some constant (your velocity)
        double setpoint = (velocity * time) - (velocity * pointA) + lastAccelerating;
        lastConstant = setpoint;
        //System.out.println("In the constant zone! Sent this setpoint: " + setpoint + "");
        if (negativeSetpoint) {
            setpoint *= -1;
        }
        return setpoint;
    }

    /*
     Integrates the decelerating part of the velocity graph to get position
     from pointB to pointC
     */
    private double decelerating(double time) {
        //The integral of v(t) = -ax + v where a is some constant (your acceleration) and v is your velocity
        double setpoint = 0.0;

        if (triangle) {
            setpoint = (((-acceleration * (time * time)) / 2.0)
                    + (yInterceptOfDecelerating * time)) - (((-acceleration
                    * (trianglePoint * trianglePoint)) / 2.0) + (yInterceptOfDecelerating * trianglePoint));
            if (time >= triangleEndpoint) {
                setpoint = desiredOverallDistance;
            } else {
                setpoint += lastAccelerating;
            }
        } else {
            setpoint = (((-acceleration * (time * time)) / 2.0)
                    + (yInterceptOfDecelerating * time)) - (((-acceleration
                    * (pointB * pointB)) / 2.0) + (yInterceptOfDecelerating * pointB));
            if (time >= pointC) {
                setpoint = desiredOverallDistance;
            } else {
                setpoint += lastConstant;//Adds on the cumulative sum from the acceleration and constant sections
            }
        }
        //System.out.println("In the decelerating zone! Sent this setpoint: " + setpoint);
        if (negativeSetpoint) {
            setpoint *= -1;
        }
        return setpoint;
    }

    public boolean done(double time) {
        return time > pointC;
    }

    public void specialTriangleCase() {
        trianglePoint = Math.sqrt(desiredOverallDistance / acceleration);
        triangleEndpoint = trianglePoint * 2;
        //The y-intercept of the decelerating side of the triangle
        velocity = acceleration * trianglePoint;
        yInterceptOfDecelerating = 2 * acceleration * trianglePoint;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getDesiredDistance() {
        return desiredOverallDistance;
    }
}
