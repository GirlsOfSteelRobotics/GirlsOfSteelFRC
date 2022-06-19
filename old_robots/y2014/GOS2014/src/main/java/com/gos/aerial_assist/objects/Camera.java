package com.gos.aerial_assist.objects;

/**
 * @author Heather
 */

import edu.wpi.first.networktables.NetworkTableInstance;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Camera {
    //magic value must change
    private static final double HEIGHT_OF_GOAL = 2.57; //actually height of goal - height of robot CHANGE
    //Need to change this command into meters and not inches
    private static final double INITIAL_VELOCITY = 8.65;
    private static final double GRAVITY = 9.82;
    private final double m_distance;
    private boolean m_isHot;

    public Camera() {
        m_distance = this.getDistanceToTarget();
    }

    public static boolean isConnected() {
        return NetworkTableInstance.getDefault().isConnected();
    }

    public static boolean isGoalHot() {
        //System.out.println("CAMERAAA IS HOTTTTTTTTTTTTTTTTTTTTTTTTTTT: " + NetworkTableInstance.getDefault().getTable("camera").getEntry("isHot").getBoolean(false));
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("isHot").getBoolean(false);
    }

    public static boolean foundTarget() {
        return isConnected() && (NetworkTableInstance.getDefault().getTable("camera").getEntry("isTargetLeft").getBoolean(false) || NetworkTableInstance.getDefault().getTable("camera").getEntry("isTargetRight").getBoolean(false));
    }

    public final double getDistanceToTarget() { // -1 to 1 -> position horizontally of the backboard on the screen
        //Need to change this into metrics
        //return NetworkTableInstance.getDefault().getTable("camera").getEntry("distance").getDouble(0);
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("distance").getDouble(0);
    }

    public double getImageTargetRatio() { // -1 to 1 -> position horizontally of the backboard on the screen
        //Need to change this into metrics
        //return NetworkTableInstance.getDefault().getTable("camera").getEntry("distance").getDouble(0);
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("targetRatio").getDouble(0);
    }

    public double getVerticalAngleOffset() {
        double x = m_distance;
        double y = HEIGHT_OF_GOAL; //needs to be changed based on initial height of robot
        double g = GRAVITY;
        double v = INITIAL_VELOCITY;
        //make sure all numbers are in metric units
        //        double positiveAngle = Math.atan(square(v)+Math.sqrt(fourthPower(v)-g*(g*square(x)+2*y*square(v)))/g*x);
        //return angle;
        return Math.atan(square(v) - Math.sqrt(fourthPower(v) - g * (g * square(x) + 2 * y * square(v))) / g * x);
    }

    //mathUtils didn't have a squaring function (had to make our own)
    private double square(double num1) {
        return num1 * num1;
    }

    private double fourthPower(double num1) {
        return num1 * num1 * num1 * num1;
    }

    public boolean isHot() {
        return m_isHot;
    }

    public void setIsHot(boolean isHot) {
        m_isHot = isHot;
    }
}
