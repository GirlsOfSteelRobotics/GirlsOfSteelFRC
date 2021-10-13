              /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.DoubleProperty;
import edu.wpi.first.smartdashboard.properties.IntegerProperty;
import edu.wpi.first.wpijavacv.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Auto extends WPICameraExtension {

    IntegerProperty redThreshold = new IntegerProperty(this, "Red Maximum", 130);
    IntegerProperty greenThreshold = new IntegerProperty(this, "Green Minimum", 80);
    IntegerProperty differenceInPixels = new IntegerProperty(this, "Difference In Pixels", 7);
    IntegerProperty dialteInt = new IntegerProperty(this, "amount dialate", 8);
    IntegerProperty erodeInt = new IntegerProperty(this, "amount erode", 8);
    IntegerProperty approxInt = new IntegerProperty(this, "Poly Approximation", 8);
    DoubleProperty topUperHeightWidthRaio = new DoubleProperty(this, "Uper Height Width Ratio", 9);
    DoubleProperty topLowerHeightWidthRatio = new DoubleProperty(this, "Lower Height Width Ratio", 3);
    double targetHeightAverage = 0;
    double targetWidthAverage = 0;
    double targetHeightWidthRatio = 0;
    double topTargetHeightAverage = 0;
    double topTargetWidthAverage = 0;
    double topTargetHeightWidthRatio = 0;
    boolean topTargetDetected = false;
    double sideTargetHeightAverage = 0;
    double sideTargetWidthAverage = 0;
    double sideTargetHeightWidthRatio = 0;
    boolean sideTargetDetected = false;

    @Override
    public void init() {
        super.init();
        NetworkTable.getTable("SmartDashboard").putBoolean("top target Detected", topTargetDetected);
        NetworkTable.getTable("SmartDashboard").putBoolean("side target Detected", sideTargetDetected);
    }

    @Override
    public WPIImage processImage(WPIColorImage rawImage) {

        if (rawImage == null) {
            return rawImage;
        }

        try {
            //Gets the 3 channels.
            WPIGrayscaleImage red = rawImage.getRedChannel();
            WPIGrayscaleImage green = rawImage.getGreenChannel();
            // WPIColorImage DERP = IplImage;
            //Thresholding! YAY!
            WPIBinaryImage thresholdg = green.getThreshold(greenThreshold.getValue());
            WPIBinaryImage thresholdr = red.getThresholdInverted(redThreshold.getValue());

            //combining 2 images with thresholds.
            WPIBinaryImage threshold = thresholdg;
            threshold.and(thresholdr);
            threshold.dilate(dialteInt.getValue());//@@@
            threshold.erode(erodeInt.getValue());//@@@

            //finding contours! :)
            WPIContour[] contours = threshold.findContours();
            rawImage.drawContours(contours, WPIColor.BLUE, 2);
            WPIPolygon[] polygons = new WPIPolygon[contours.length];
            ArrayList<WPIPolygon> recsList = new ArrayList<WPIPolygon>();
            //Finds the biggest polygon.
//            for (int x = 0; x < 10; x++) {
//                for (WPIContour contour : contours) {
            for (int i = 0; i < contours.length; i++) {

                // Approximate the contour into a polygon@@@@
                polygons[i] = contours[i].approxPolygon(approxInt.getValue());
                //   rawImage.drawPolygons(polygons, WPIColor.WHITE, 3);
                WPIPoint[] points = polygons[i].getPoints();
                // Make sure it has 4 points
                if (points.length == 4) {
                    // Turn into apolygons[i].getPoints();
                    // Make sure it has  rectangle
                    recsList.add(polygons[i]);
                }

            }

            WPIPolygon[] rectangles = recsList.toArray(new WPIPolygon[recsList.size()]);

            rawImage.drawPolygons(rectangles, WPIColor.WHITE, 3);
            Arrays.sort(rectangles, new PolygonAreaCompare());
            //   rectangles2 = rectangles;
            ArrayList<WPIPolygon> targetList = new ArrayList<WPIPolygon>();
            // Check to see if there is a poly inside of each poly
//            for (int x = 0; x < 4; x++) {
            for (int i = 0; i < rectangles.length; i++) {
                for (int j = i + 1; j < rectangles.length; j++) {
                    // Turn the polygons into rectangles

                    AutoRectangle bigRect = new AutoRectangle(rectangles[i].getPoints());
                    AutoRectangle smallRect = new AutoRectangle(rectangles[j].getPoints());

                    // Finds the centerpoints of the rectangles
                    WPIPoint bigCenterpoint = bigRect.getCenterpoint();
                    WPIPoint smallCenterpoint = smallRect.getCenterpoint();

                    // Checks to see whether we're actually look at the target
                    // (does it by making sure centerpoints are close)
                    if ((smallCenterpoint.getX() < bigCenterpoint.getX() + differenceInPixels.getValue()
                            && smallCenterpoint.getX() > bigCenterpoint.getX() - differenceInPixels.getValue())
                            && (smallCenterpoint.getY() < bigCenterpoint.getY() + differenceInPixels.getValue()
                            && smallCenterpoint.getY() > bigCenterpoint.getY() - differenceInPixels.getValue())) {
                        // Draw the second biggest magenta ONLY WHEN YOU GOT THE TARGET.
                        rawImage.drawPolygon(rectangles[j], WPIColor.CYAN, 2);
                        targetList.add(rectangles[i]);
                    }


                }
            }
//            }
            WPIPolygon[] targets = targetList.toArray(new WPIPolygon[targetList.size()]);
            Arrays.sort(targets, new PolygonAreaCompare());

            // If not null, then biggestPoly is the  top target
            if (targets.length >= 2) {

                AutoRectangle sideTarget = new AutoRectangle(targets[0].getPoints());
                AutoRectangle topTarget = new AutoRectangle(targets[1].getPoints());

                sideTargetHeightAverage = (sideTarget.getRightHeight() + sideTarget.getLeftHeight()) / 2;
                sideTargetWidthAverage = (sideTarget.getBottomWidth() + sideTarget.getTopWidth()) / 2;
                sideTargetHeightWidthRatio = (sideTargetWidthAverage / sideTargetHeightAverage);
                sideTargetDetected = true;

                topTargetHeightAverage = (sideTarget.getRightHeight() + sideTarget.getLeftHeight()) / 2;
                topTargetWidthAverage = (sideTarget.getBottomWidth() + sideTarget.getTopWidth()) / 2;
                topTargetHeightWidthRatio = (topTargetWidthAverage / topTargetHeightAverage);
                topTargetDetected = true;

                NetworkTable.getTable("SmartDashboard").putNumber("side target height width ratio", sideTargetHeightWidthRatio);
                NetworkTable.getTable("SmartDashboard").putNumber("side target height", sideTargetHeightAverage);
                NetworkTable.getTable("SmartDashboard").putNumber("side target width", sideTargetWidthAverage);
                NetworkTable.getTable("SmartDashboard").putNumber("top target height width ratio", topTargetHeightWidthRatio);
                NetworkTable.getTable("SmartDashboard").putNumber("top target height", topTargetHeightAverage);
                NetworkTable.getTable("SmartDashboard").putNumber("top target width", topTargetWidthAverage);

                // Find the difference between x values
                double centerImageX = rawImage.getWidth() / 2;

                double centerSideX = sideTarget.getCenterpoint().getX();
                double sideDifference = centerSideX - centerImageX;
                double centerTopX = topTarget.getCenterpoint().getX();
                double topDifference = centerTopX - centerImageX;

                // rescale to make the difference -1 if all the way left, 1 if all the way right
                sideDifference *= 2.0 / rawImage.getWidth();
                topDifference *= 2.0 / rawImage.getWidth();

                // Draw it
                rawImage.drawPolygon(targets[0], WPIColor.YELLOW, 2);
                rawImage.drawPolygon(targets[1], WPIColor.RED, 2);

                // Send the difference to the robot
                NetworkTable table = NetworkTable.getTable("camera");
                table.putNumber("sideXDifference", sideDifference);
                table.putNumber("sideTargetPixelHeight", sideTargetHeightAverage);
                table.putNumber("sideTargetPixelWidth", sideTargetWidthAverage);
                table.putBoolean("foundSideTarget", sideTargetDetected);
                table.putNumber("topXDifference", topDifference);
                table.putNumber("topTargetPixelHeight", topTargetHeightAverage);
                table.putNumber("topTargetPixelWidth", topTargetWidthAverage);
                table.putBoolean("foundTopTarget", topTargetDetected);

            } else if (targets.length == 1) {

                AutoRectangle target = new AutoRectangle(targets[0].getPoints());
                targetHeightAverage = (target.getRightHeight() + target.getLeftHeight()) / 2;
                targetWidthAverage = (target.getBottomWidth() + target.getTopWidth()) / 2;
                targetHeightWidthRatio = (targetWidthAverage / targetHeightAverage);

                if (targetHeightWidthRatio > topLowerHeightWidthRatio.getValue()) {

                    AutoRectangle topTarget = target;

                    topTargetHeightAverage = targetHeightAverage;
                    topTargetWidthAverage = targetWidthAverage;
                    topTargetHeightWidthRatio = targetHeightWidthRatio;

                    topTargetDetected = true;
                    sideTargetDetected = false;

                    NetworkTable.getTable("SmartDashboard").putNumber("top target height width ratio", topTargetHeightWidthRatio);
                    NetworkTable.getTable("SmartDashboard").putNumber("top target height", topTargetHeightAverage);
                    NetworkTable.getTable("SmartDashboard").putNumber("top target width", topTargetWidthAverage);

                    // Find the difference between x values
                    double centerImageX = rawImage.getWidth() / 2;
                    double centerTopX = topTarget.getCenterpoint().getX();
                    double topDifference = centerTopX - centerImageX;

                    // rescale to make the difference -1 if all the way left, 1 if all the way right
                    topDifference *= 2.0 / rawImage.getWidth();

                    // Draw it
                    rawImage.drawPolygon(targets[0], WPIColor.RED, 2);

                    // Send the difference to the robot
                    NetworkTable table = NetworkTable.getTable("camera");
                    table.putBoolean("foundSideTarget", sideTargetDetected);
                    table.putNumber("topXDifference", topDifference);
                    table.putNumber("topTargetPixelHeight", topTargetHeightAverage);
                    table.putNumber("topTargetPixelWidth", topTargetWidthAverage);
                    table.putBoolean("foundTopTarget", topTargetDetected);

                } else {
                    AutoRectangle sideTarget = target;

                    sideTargetHeightAverage = targetHeightAverage;
                    sideTargetWidthAverage = targetWidthAverage;
                    sideTargetHeightWidthRatio = targetHeightWidthRatio;

                    sideTargetDetected = true;
                    topTargetDetected = false;

                    NetworkTable.getTable("SmartDashboard").putNumber("side target height width ratio", sideTargetHeightWidthRatio);
                    NetworkTable.getTable("SmartDashboard").putNumber("side target height", sideTargetHeightAverage);
                    NetworkTable.getTable("SmartDashboard").putNumber("side target width", sideTargetWidthAverage);

                    // Find the difference between x values
                    double centerImageX = rawImage.getWidth() / 2;
                    double centerSideX = sideTarget.getCenterpoint().getX();
                    double sideDifference = centerSideX - centerImageX;

                    // rescale to make the difference -1 if all the way left, 1 if all the way right
                    sideDifference *= 2.0 / rawImage.getWidth();

                    // Draw it
                    rawImage.drawPolygon(targets[0], WPIColor.YELLOW, 2);

                    // Send the difference to the robot
                    NetworkTable table = NetworkTable.getTable("camera");
                    table.putBoolean("foundTopTarget", topTargetDetected);
                    table.putNumber("sideXDifference", sideDifference);
                    table.putNumber("sideTargetPixelHeight", sideTargetHeightAverage);
                    table.putNumber("sideTargetPixelWidth", sideTargetWidthAverage);
                    table.putBoolean("foundSideTarget", sideTargetDetected);
                }
            } else {
                topTargetDetected = false;
                sideTargetDetected = false;
                NetworkTable table = NetworkTable.getTable("camera");
                table.putBoolean("foundTopTarget", topTargetDetected);
                table.putBoolean("foundSideTarget", sideTargetDetected);
                table.putNumber("sideXDifference", 0);
                table.putNumber("topXDifference", 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkTable.getTable("SmartDashboard").putBoolean("top target Detected", topTargetDetected);
        NetworkTable.getTable("SmartDashboard").putBoolean("side target Detected", sideTargetDetected);
        return rawImage;
    }
//    public void processIPLImage(WPIColorImage img) {
//        WPIImageWrapper wrp = new WPIImageWrapper(img);
//        IplImage ipl = wrp.getIplImage();
//
//    }
// Finds distance between two points. 

    public double distance(WPIPoint point1, WPIPoint point2) {
        int dx = point1.getX() - point2.getX();
        int dy = point1.getY() - point2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    //Finds the point at the center of the image.
    public WPIPoint centerOfImage(WPIColorImage rawImage) {
        int halfOfTheHeight = rawImage.getHeight() / 2;
        int halfOfTheWidth = rawImage.getWidth() / 2;
        return new WPIPoint(halfOfTheWidth, halfOfTheHeight);
    }

    private static class PolygonAreaCompare implements Comparator<WPIPolygon> {

        @Override
        public int compare(WPIPolygon p1, WPIPolygon p2) {
            return (p2.getArea() - p1.getArea());
        }
    }
}