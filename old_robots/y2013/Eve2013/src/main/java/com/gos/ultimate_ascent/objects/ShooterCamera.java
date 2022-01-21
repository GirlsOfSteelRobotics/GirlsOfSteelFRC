/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.objects;

/**
 * @author Heather
 */
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ShooterCamera {
    private static final double positionOneAngleOffset = -7.2;
    private static final double positionTwoAngleOffset = 0;

    //magic value must change
    private static final double epsilon = 5;
    private static double heightOfDesiredPosition;
    private static double widthOfDesiredPosition;

    public ShooterCamera() {
        SmartDashboard.putNumber("top arget angle offset", getTopDiffAngle());
        SmartDashboard.putNumber("top target angle offset", getSideDiffAngle());
    }

    public static int getLocation() {
        if (atShootPositionOne()) {
            return PositionInfo.BACK_RIGHT;
        } else if (atShootPositionTwo()) {
            return PositionInfo.BACK_LEFT;
        }
        return PositionInfo.NO_POSITION;
    }

    public static double getLocationOffsetAngle() {
        if (atShootPositionOne()) {
            return positionOneAngleOffset;
        } else if (atShootPositionTwo()) {
            return positionTwoAngleOffset;
        }
        return 0;
    }

    private static double getTopTargetPixelHeight() {
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("topTargetPixelHeight").getDouble(0);
    }

    private static double getTopTargetPixelWidth() {
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("topTargetPixelWidth").getDouble(0);
    }

    private static double getSideTargetPixelHeight() {
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("sideTargetPixelHeight").getDouble(0);
    }

    private static double getSideTargetPixelWidth() {
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("sideTargetPixelWidth").getDouble(0);
    }

    public static boolean isConnected() {
        return NetworkTableInstance.getDefault().getTable("camera").isConnected();
    }

    public static boolean foundTopTarget() {
        return isConnected() && NetworkTableInstance.getDefault().getTable("camera").getEntry("foundTopTarget").getBoolean(false);
    }

    public static boolean foundSideTarget() {
        return isConnected() && NetworkTableInstance.getDefault().getTable("camera").getEntry("foundSideTarget").getBoolean(false);
    }

    private static double getTopHorizontalDifference() { // -1 to 1 -> position horizontally of the backboard on the screen
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("topXDifference").getDouble(0);
    }

    private static double getSideHorizontalDifference() { // -1 to 1 -> position horizontally of the backboard on the screen
        return NetworkTableInstance.getDefault().getTable("camera").getEntry("sideXDifference").getDouble(0);
    }

    public static double getTopDiffAngle() {
        if (foundTopTarget()) {
            double angle = (47.0 / 2.0) * (Math.PI / 180.0); //maximum angle for the camera view (in one direction) -> changed to radians
            double yDistanceToTarget = getTopHorizontalDifference() * Math.tan(angle);
            //calculating the yDistance from the center of the camera frame to target
            //normally multiplied by the xDistance to Target, but in calculating the angle (below)
            //you need to divide by the xDistance so it is not necessary to multiply then divide
            double angleToTarget = Math.atan(yDistanceToTarget); //finding the angle difference from the center to the target
            return angleToTarget * 180 / Math.PI; //change into degrees
        }
        return 0;
    }

    public static double getSideDiffAngle() {
        if (foundSideTarget()) {
            double angle = (47.0 / 2.0) * (Math.PI / 180.0); //maximum angle for the camera view (in one direction) -> changed to radians
            double yDistanceToTarget = getSideHorizontalDifference() * Math.tan(angle);
            //calculating the yDistance from the center of the camera frame to target
            //normally multiplied by the xDistance to Target, but in calculating the angle (below)
            //you need to divide by the xDistance so it is not necessary to multiply then divide
            double angleToTarget = Math.atan(yDistanceToTarget); //finding the angle difference from the center to the target
            return angleToTarget * 180 / Math.PI; //change into degrees
        }
        return 0;
    }

    /**
     * Returns the angle between where the camera is facing and the target (in
     * degrees)
     */
    public static double getXDifferenceTopTarget() {
        double xDifference;
        double angle = (47.0 / 2.0) * (Math.PI / 180.0); //maximum angle for the camera view (in one direction) -> changed to radians
        xDifference = getTopHorizontalDifference() * Math.tan(angle);
        //half of the camera view (tan of angle * x distance from target *
        //the xDistance is not in this equation because
        return xDifference;
    }

    public static double getXDifferenceSideTarget() {
        double xDifference;
        double angle = (47.0 / 2.0) * (Math.PI / 180.0); //maximum angle for the camera view (in one direction) -> changed to radians
        xDifference = getSideHorizontalDifference() * Math.tan(angle);
        //half of the camera view (tan of angle * x distance from target *
        //the xDistance is not in this equation because
        return xDifference;
    }

    private static boolean atShootPositionOne() {
        heightOfDesiredPosition = 63;
        widthOfDesiredPosition = 205;
        //check width and height are in desired position
        return (foundTopTarget() && getTopTargetPixelHeight() > heightOfDesiredPosition - epsilon && getTopTargetPixelHeight() < epsilon + heightOfDesiredPosition && getTopTargetPixelWidth() > widthOfDesiredPosition - epsilon && getTopTargetPixelWidth() < epsilon + widthOfDesiredPosition);
    }

    private static boolean atShootPositionTwo() {
        heightOfDesiredPosition = 1;
        widthOfDesiredPosition = 2;
        //check width and height are in desired position
        return (foundSideTarget() && getSideTargetPixelHeight() > heightOfDesiredPosition - epsilon && getSideTargetPixelHeight() < epsilon + heightOfDesiredPosition && getSideTargetPixelWidth() > widthOfDesiredPosition - epsilon && getSideTargetPixelWidth() < epsilon + widthOfDesiredPosition);
    }
}
