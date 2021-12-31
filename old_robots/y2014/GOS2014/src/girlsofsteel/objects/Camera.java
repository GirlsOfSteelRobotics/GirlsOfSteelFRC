package girlsofsteel.objects;

/**
 * @author Heather
 */

import edu.wpi.first.wpilibj.networktables.NetworkTable;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Camera {
    //magic value must change
    private static final double m_heightOfGoal = 2.57; //actually height of goal - height of robot CHANGE
    //Need to change this command into meters and not inches
    private static final double m_initialVelocity = 8.65;
    private static final double m_gravity = 9.82;
    private final double m_distance;
    private boolean m_isHot;

    public Camera() {
        m_distance = this.getDistanceToTarget();
    }

    public static boolean isConnected() {
        return NetworkTable.getTable("camera").isConnected();
    }

    public static boolean isGoalHot() {
        //System.out.println("CAMERAAA IS HOTTTTTTTTTTTTTTTTTTTTTTTTTTT: " + NetworkTable.getTable("camera").getBoolean("isHot",false));
        return NetworkTable.getTable("camera").getBoolean("isHot", false);
    }

    public static boolean foundTarget() {
        return isConnected() && (NetworkTable.getTable("camera").getBoolean("isTargetLeft", false) || NetworkTable.getTable("camera").getBoolean("isTargetRight", false));
    }

    public final double getDistanceToTarget() { // -1 to 1 -> position horizontally of the backboard on the screen
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
        double x = m_distance;
        double y = m_heightOfGoal; //needs to be changed based on initial height of robot
        double g = m_gravity;
        double v = m_initialVelocity;
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
