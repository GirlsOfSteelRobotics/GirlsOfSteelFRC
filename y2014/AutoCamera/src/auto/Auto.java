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

public class Auto extends WPICameraExtension {

    IntegerProperty redThreshold = new IntegerProperty(this, "Red Maximum", 130);
    IntegerProperty greenThreshold = new IntegerProperty(this, "Green Minimum", 80);
    IntegerProperty differenceInPixels = new IntegerProperty(this, "Difference In Pixels", 7);
    IntegerProperty dialteInt = new IntegerProperty(this, "amount dialate", 8);
    IntegerProperty erodeInt = new IntegerProperty(this, "amount erode", 8);
    IntegerProperty approxInt = new IntegerProperty(this, "Poly Approximation", 8);
    DoubleProperty uperHeightWidthRaio = new DoubleProperty(this, "Uper Height Width Ratio", 9);
    DoubleProperty lowerHeightWidthRatio = new DoubleProperty(this, "Lower Height Width Ratio", 3);
    
    double heightAverage = 0;
    double trueAverageHeight = 0;
    double widthAverage = 0;
    double trueAverageWidth = 0;
    double heightWidthRatio = 0;
    boolean targetDetected = false;

    //  double uperHeightWidthRaioLim = uperHeightWidthRaio.getValue();
    //  double lowerHeightWidthRatioLim = lowerHeightWidthRatio.getValue();
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

            // Get the biggest and second biggest rectangles
            WPIPolygon biggestPoly = null;
            WPIPolygon secondBiggestPoly = null;
            WPIPolygon thirdBiggestPoly = null;
            WPIPolygon fourthBiggestPoly = null;

            int maxArea = 0;
            int secondMaxArea = 0;

            //Finds the biggest polygon.
            for (WPIContour contour : contours) {
                // Approximate the contour into a polygon@@@@
                WPIPolygon polygon = contour.approxPolygon(approxInt.getValue());

                // Make sure it has 4 points
                WPIPoint[] points = polygon.getPoints();

                //aray for 4 biggest rectangles. Will contain their areas
          

                if (points.length == 4) {
                    // Turn into a rectangle

                    // Check to see if this is the biggest polygon so far
                    int currArea = polygon.getArea();

                    AutoRectangle rec = new AutoRectangle(polygon.getPoints());

                    heightAverage = (rec.getRightHeight() + rec.getLeftHeight()) / 2;
                    widthAverage = (rec.getBottomWidth() + rec.getTopWidth()) / 2;
                    heightWidthRatio = (widthAverage / heightAverage);



                      if ((heightWidthRatio < uperHeightWidthRaio.getValue()) && (heightWidthRatio > lowerHeightWidthRatio.getValue())) {

                    if (currArea > maxArea) {
                        // Move the biggest rectangle to be the second biggest
                        secondMaxArea = maxArea;
                        secondBiggestPoly = biggestPoly;

                        // Set the new one to our biggest rectangle
                        maxArea = currArea;
                        biggestPoly = polygon;
                    } else if (currArea > secondMaxArea) {
                        
                        /*
                         * if not bigger than the biggest polygon, but
                         * bigger than the second biggest polygon, then the
                         * current rectangle is made the second biggest
                         */
                        secondMaxArea = currArea;
                        secondBiggestPoly = polygon;
                    } 
                  } 
                }
            }


            // Draw the biggest polygon green
            if (biggestPoly != null) {
                rawImage.drawPolygon(biggestPoly, WPIColor.CYAN, 3);
            }

            // Check to see if the second biggest is inside the biggest
            if (secondBiggestPoly != null) {

                // Turn the polygons into rectangles
                AutoRectangle smallRect = new AutoRectangle(secondBiggestPoly.getPoints());
                AutoRectangle bigRect = new AutoRectangle(biggestPoly.getPoints());

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
                    rawImage.drawPolygon(secondBiggestPoly, WPIColor.MAGENTA, 2);

                } else {
                    // Get rid of them because they FAILED!
                    biggestPoly = null;
                    secondBiggestPoly = null;
                }
            } else {
                // There was no smaller polygon, so get rid of the bigger one
                biggestPoly = null;
            }

            // If not null, then biggestPoly is the  top target
            if (biggestPoly != null) {

                AutoRectangle target = new AutoRectangle(biggestPoly.getPoints());

                heightAverage = (target.getRightHeight() + target.getLeftHeight()) / 2;
                widthAverage = (target.getBottomWidth() + target.getTopWidth()) / 2;
                heightWidthRatio = widthAverage / heightAverage;

                targetDetected = true;

                NetworkTable.getTable("SmartDashboard").putNumber("height width ratio", heightWidthRatio);

//                System.out.println(topTargetHeightAverage);
//                topTargetTrueAverageHeight = topTargetHeightAverage / rawImage.getHeight();
                NetworkTable.getTable("SmartDashboard").putNumber("target height", heightAverage);

//                System.out.println(topTargetWidthAverage);
//                topTargetTrueAverageWidth = topTargetWidthAverage / rawImage.getWidth();
                NetworkTable.getTable("SmartDashboard").putNumber("target width", widthAverage);

                // Find the difference between x values
                double centerImageX = rawImage.getWidth() / 2;
                double centerRectX = target.getCenterpoint().getX();
                double difference = centerRectX - centerImageX;

                // rescale to make the difference -1 if all the way left, 1 if all the way right
                difference *= 2.0 / rawImage.getWidth();

                // Draw it
                rawImage.drawPolygon(biggestPoly, WPIColor.RED, 2);

                // Send the difference to the robot
                NetworkTable table = NetworkTable.getTable("camera");
                table.putBoolean("foundTarget", targetDetected);
                table.putNumber("xDifference", difference);
                table.putNumber("targetPixelHeight", heightAverage);
                table.putNumber("targetPixelWidth", widthAverage);


            } else {
                // no target visible
                targetDetected = false;
                NetworkTable table = NetworkTable.getTable("camera");
                table.putBoolean("foundTarget", targetDetected);
                table.putNumber("xDifference", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkTable.getTable("SmartDashboard").putBoolean("target Detected", targetDetected);
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
}