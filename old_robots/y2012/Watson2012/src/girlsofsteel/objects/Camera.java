package girlsofsteel.objects;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.networktables.NetworkTable;;

public class Camera {

    public static double getXDistance(){
        //distance from the center of the turret to the backboard.
        double xDistance;
        xDistance = NetworkTable.getTable("camera").getDouble("distanceToBottomTarget", 0);
        return xDistance;
    }

    public static double getImageTargetRatio(){
      return NetworkTable.getTable("camera").getDouble("imageTargetRatio", 0);
    }

    public static boolean isConnected() {
        return NetworkTable.getTable("camera").isConnected();
    }

    public static boolean foundTarget() {
        return isConnected()
                && NetworkTable.getTable("camera").getBoolean("foundTarget", false);
    }

    public static double getHorizontalDifference() { // -1 to 1 -> position horizontally of the backboard on the screen
        return NetworkTable.getTable("camera").getDouble("xDifference", 0);
    }

    public static double getDiffAngle(){
        double angle = (47.0/2.0)*(Math.PI/180.0);//maximum angle for the camera view (in one direction) -> changed to radians
        double yDistanceToTarget = getHorizontalDifference()*Math.tan(angle);
        //calculating the yDistance from the center of the camera frame to target
        //normally multiplied by the xDistance to Target, but in calculating the angle (below)
        //you need to divide by the xDistance so it is not necessary to multiply then divide
        double angleToTarget = MathUtils.atan(yDistanceToTarget);//finding the angle difference from the center to the target
        return angleToTarget * 180 / Math.PI;//change into degrees
        /*
        double xDifference = getHorizontalDifference();
        double xDistance = NetworkTable.getTable("camera").getDouble("distanceToBottomTarget", 0)*(0.0254/1.0);
        return MathUtils.atan(xDifference/xDistance) * 180 / Math.PI;
         */
    }

    /**
     * Returns the angle between where the camera is facing and the target (in degrees)
     * @return
     */

    public static double getXDifference(){
        double xDifference;
        double angle = (47.0/2.0)*(Math.PI/180.0); //maximum angle for the camera view (in one direction) -> changed to radians
        xDifference = getHorizontalDifference() * Math.tan(angle);
        //half of the camera view (tan of angle * x distance from target *
        //the xDistance is not in this equation because
        return xDifference;
    }

     public static double getDistanceToTopTarget(){
        double xDistance = getXDistance();
        //4290.25 is the hight of the top target minus camera hight.
        double distanceToTopTarget = Math.sqrt(xDistance*xDistance + 4290.25*(0.0254/1.0));
        return distanceToTopTarget;
    }

    public static double getAngleToTopTarget(){
        double xDistance = getXDistance();
        double distanceToTopTarget = getDistanceToTopTarget();
        double angleToTopTarget = MathUtils.acos(xDistance) / distanceToTopTarget;
        return angleToTopTarget;
    }

}
