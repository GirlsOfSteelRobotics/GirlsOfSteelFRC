package com.girlsofsteelrobotics.atlas.objects;

/**
 *
 * @author Heather
 */
//import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera {
    //magic value must change
    double heightOfGoal = 2.57; //actually height of goal - height of robot CHANGE
    double distance = 0;
     //Need to change this command into meters and not inches
    double initialVelocity = 8.65;
    double gravity = 9.82;
    public static boolean isHot = false;
            
    public Camera() {
        distance = this.getDistanceToTarget();
    }

    public static boolean isConnected() {
        return NetworkTable.getTable("camera").isConnected();
    }
    
    public static boolean isGoalHot() {
        //System.out.println("CAMERAAA IS HOTTTTTTTTTTTTTTTTTTTTTTTTTTT: " + NetworkTable.getTable("camera").getBoolean("isHot",false));
        return NetworkTable.getTable("camera").getBoolean("isHot",false);
    }

    public static boolean foundTarget() {
        return isConnected() && (NetworkTable.getTable("camera").getBoolean("isTargetLeft")||NetworkTable.getTable("camera").getBoolean("isTargetRight"));
    }

    public double getDistanceToTarget() { // -1 to 1 -> position horizontally of the backboard on the screen
        //Need to change this into metrics
        //return NetworkTable.getTable("camera").getNumber("distance", 0);
        return NetworkTable.getTable("camera").getNumber("distance", 0);
    }

    public double getImageTargetRatio() { // -1 to 1 -> position horizontally of the backboard on the screen
        //Need to change this into metrics
        //return NetworkTable.getTable("camera").getNumber("distance", 0);
        return NetworkTable.getTable("camera").getNumber("targetRatio", 0);
    }
    
   public double getVerticalAngleOffset() {
        double x = distance;
        double y = heightOfGoal; //needs to be changed based on initial height of robot
        double g = gravity;
        double v = initialVelocity; 
        //make sure all numbers are in metric units
        double positiveAngle = Math.atan(square(v)+Math.sqrt(fourthPower(v)-g*(g*square(x)+2*y*square(v)))/g*x);
        double negativeAngle = Math.atan(square(v)-Math.sqrt(fourthPower(v)-g*(g*square(x)+2*y*square(v)))/g*x);
        //return angle;
        return negativeAngle;
    }
    
    //mathUtils didn't have a squaring function (had to make our own)
     private double square(double num1) {
        double squareNum1 = num1 * num1;
        return squareNum1;
    }
    
    private double fourthPower(double num1) {
        double powerFourNum1 = num1 * num1 * num1 * num1;
        return powerFourNum1;
    }
}
