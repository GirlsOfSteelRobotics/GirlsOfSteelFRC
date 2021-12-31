/*
 * A planner class that will connect position PID with velocity PID.
 * See LSPB for more information

 Point a refers to when the robot should stop accelerating and the velocity
 graph reaches the flat part. Point b refers to when the robot should start
 deccelerating and slants back down. Point c is the last point of the velocity
 trapezoid and is also the base of the trapezoid.

 ToDO Prepare for special case when total distance is less than 1.5(?)

 */

package girlsofsteel.objects;

/**
 * @author Sylvie
 */
public class LSPBPIDPlanner {

    private double m_velocity;
    private final double m_acceleration;
    private boolean m_negativeSetpoint;
    private double m_desiredOverallDistance;
    private double m_lastAccelerating;
    private double m_lastConstant;
    private double m_pointA; //time when graph changes from accelerating to constant
    private double m_pointB;  //time when graph changes from constant to decelerating
    private double m_pointC; //time when graph ends
    private double m_trianglePoint;
    private double m_triangleEndpoint;
    private double m_yInterceptOfDecelerating;
    private boolean m_triangle;

    /*
     This constructor defaults the velocity and acceleration
     */
    public LSPBPIDPlanner() {
        //To use the LSPB You must first 1.) call calculateVelocityGraph()
        //2.) Call getDesiredPosition() in some loop getting updated times
        m_acceleration = 6; //The default is the chassis's acceleration
        //System.out.println("Changed Acceleration!: " + acceleration);
        m_velocity = 3;
    }

    public LSPBPIDPlanner(double acceleration) {
        this.m_acceleration = acceleration;
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
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public void calculateVelocityGraph(double desiredDistance) {
//        pointA = (velocity/acceleration);
//        pointB = (desiredDistance/velocity);
//        pointC = pointA + pointB;
        if (desiredDistance < 0) {
            m_negativeSetpoint = true;
            desiredDistance *= -1;
        } else {
            m_negativeSetpoint = false;
        }
        m_desiredOverallDistance = desiredDistance;
        if (m_desiredOverallDistance > 1.5) { //based off of the chassis case
            //System.out.println("Velocity: " + velocity + "\tacceleration: " + acceleration);
            m_pointC = (m_desiredOverallDistance / m_velocity) + (m_velocity / m_acceleration);
            m_pointB = m_pointC - (m_velocity) / m_acceleration;
            m_pointA = m_pointC - m_pointB;
            m_yInterceptOfDecelerating = m_velocity + (m_acceleration * m_pointB);
            //System.out.println("Point A: " + pointA + "\tPointB: " + pointB + "\tPointC: " + pointC + "\tDesiredDistance: " + desiredOverallDistance);
        } else {
            m_triangle = true;
            specialTriangleCase();
            //System.out.println("Top of Triangle: " + trianglePoint + "\tEnd of the Triangle: " + triangleEndpoint);
        }
    }

    /*
     Returns the corresponding position setpoint given time
     */
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public double getDesiredPosition(double time) {
        time /= 1000; //To get milliseconds into seconds
        //System.out.println("Time in seconds: " + time);
        if (m_triangle) {
            if (time < m_trianglePoint) {
                return accelerating(time);
            } else {
                return decelerating(time);
            }
        } else {
            if (time < m_pointA) {
                return accelerating(time);
            } else if (time >= m_pointA && time < m_pointB) {
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
        double setpoint = ((time * time) * m_acceleration) / (2.0);
        m_lastAccelerating = setpoint;
        //System.out.println("In accelerating zone! Sent this setpoint: " + setpoint);
        if (m_negativeSetpoint) {
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
        double setpoint = (m_velocity * time) - (m_velocity * m_pointA) + m_lastAccelerating;
        m_lastConstant = setpoint;
        //System.out.println("In the constant zone! Sent this setpoint: " + setpoint + "");
        if (m_negativeSetpoint) {
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
        double setpoint;

        if (m_triangle) {
            setpoint = (((-m_acceleration * (time * time)) / 2.0)
                + (m_yInterceptOfDecelerating * time)) - (((-m_acceleration
                * (m_trianglePoint * m_trianglePoint)) / 2.0) + (m_yInterceptOfDecelerating * m_trianglePoint));
            if (time >= m_triangleEndpoint) {
                setpoint = m_desiredOverallDistance;
            } else {
                setpoint += m_lastAccelerating;
            }
        } else {
            setpoint = (((-m_acceleration * (time * time)) / 2.0)
                + (m_yInterceptOfDecelerating * time)) - (((-m_acceleration
                * (m_pointB * m_pointB)) / 2.0) + (m_yInterceptOfDecelerating * m_pointB));
            if (time >= m_pointC) {
                setpoint = m_desiredOverallDistance;
            } else {
                setpoint += m_lastConstant;//Adds on the cumulative sum from the acceleration and constant sections
            }
        }
        //System.out.println("In the decelerating zone! Sent this setpoint: " + setpoint);
        if (m_negativeSetpoint) {
            setpoint *= -1;
        }
        return setpoint;
    }

    public boolean done(double time) {
        return time > m_pointC;
    }

    public void specialTriangleCase() {
        m_trianglePoint = Math.sqrt(m_desiredOverallDistance / m_acceleration);
        m_triangleEndpoint = m_trianglePoint * 2;
        //The y-intercept of the decelerating side of the triangle
        m_velocity = m_acceleration * m_trianglePoint;
        m_yInterceptOfDecelerating = 2 * m_acceleration * m_trianglePoint;
    }

    public double getAcceleration() {
        return m_acceleration;
    }

    public double getDesiredDistance() {
        return m_desiredOverallDistance;
    }
}
