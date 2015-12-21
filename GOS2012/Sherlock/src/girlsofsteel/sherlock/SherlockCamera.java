/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.sherlock;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.IntegerProperty;
import edu.wpi.first.wpijavacv.*;
import edu.wpi.first.wpilibj.networking.NetworkTable;
import edu.wpi.first.smartdashboard.properties.BooleanProperty;
import edu.wpi.first.smartdashboard.properties.DoubleProperty;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class SherlockCamera extends WPICameraExtension {

    IntegerProperty redThreshold = new IntegerProperty(this, "Red Maximum", 255);
    IntegerProperty blueThreshold = new IntegerProperty(this, "Blue Minimum", 150);
    IntegerProperty greenThreshold = new IntegerProperty(this, "Green Minimum", 150);
    IntegerProperty differenceInPixels = new IntegerProperty(this, "Difference In Pixels", 7);
    IntegerProperty dialteInt = new IntegerProperty(this, "amount dialate", 5);
    IntegerProperty erodeInt = new IntegerProperty(this, "amount erode", 5);
    IntegerProperty approxInt = new IntegerProperty(this, "Poly Approximation", 8);
    //IntegerProperty isSlopeFromTable = new IntegerProperty(this, "SlopeFromTable", 0);
    IntegerProperty tuneSettingInt = new IntegerProperty(this, "Auto Tune", 4);
    DoubleProperty slope = new DoubleProperty(this, "Slope", 0.95163);//based on meter data
    DoubleProperty yInt = new DoubleProperty(this, "Y Intercept", -0.17622);
    BooleanProperty isSlopeFromTable = new BooleanProperty(this, "Is Slope From Table", false);
    private int dataCount = 0;
    private double sumOfDistances = 0;
    private double sumOfAverageTargetHight = 0;
    private double sumOfCenterpointDifferences = 0;
    double distanceToBackWall;
    private WPIColorImage IplImage;

    @Override
    public WPIImage processImage(WPIColorImage rawImage) {
        if (rawImage == null) {
            return rawImage;

        }
        autoTune(rawImage, this.tuneSettingInt.getValue());
        if (isSlopeFromTable.getValue().booleanValue()) {
            slope.setValue(NetworkTable.getTable("camera").getDouble("Slope"));
            yInt.setValue(NetworkTable.getTable("camera").getDouble("yInt"));
        }

        try {
            //Gets the 3 channels.
         //   WPIGrayscaleImage red = rawImage.getRedChannel();
            WPIGrayscaleImage blue = rawImage.getBlueChannel();
          //  WPIGrayscaleImage green = rawImage.getGreenChannel();
            WPIColorImage DERP = IplImage;
            //Thresholding! YAY!
          //  WPIBinaryImage thresholdg = green.getThreshold(greenThreshold.getValue());
            WPIBinaryImage thresholdb = blue.getThreshold(blueThreshold.getValue());
           // WPIBinaryImage thresholdr = red.getThresholdInverted(redThreshold.getValue());

            //combining 3 images with thresholds.
            WPIBinaryImage threshold = thresholdb;
//                    getAnd(thresholdg);
//            threshold.and(thresholdr);
            threshold.dilate(dialteInt.getValue());//@@@
            threshold.erode(erodeInt.getValue());//@@@

            //finding contours! :)
            WPIContour[] contours = threshold.findContours();
            rawImage.drawContours(contours, WPIColor.BLUE, 2);

            // Get the biggest and second biggest rectangles
            WPIPolygon biggestPoly = null;
            WPIPolygon secondBiggestPoly = null;
            int maxArea = 0;
            int secondMaxArea = 0;
            //Finds the biggest polygon.
            for (WPIContour contour : contours) {
                // Approximate the contour into a polygon@@@@
                WPIPolygon polygon = contour.approxPolygon(approxInt.getValue());

                // Make sure it has 4 points
                WPIPoint[] points = polygon.getPoints();
                if (points.length == 4) {
                    // Turn into a rectangle
                    Rectangle rectangle = new Rectangle(points);

                    // TODO: Check other things (maybe?)

                    // Make sure the rectangle is the lower backboard
                    if (rectangle.getCenterpoint().getY() > (rawImage.getHeight() / 2)) {
                        // Check to see if this is the biggest polygon so far
                        int currArea = polygon.getArea();
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

//            WPIPoint lm = new WPIPoint(0, rawImage.getHeight() / 2);
//            WPIPoint rm = new WPIPoint(rawImage.getWidth(), rawImage.getHeight() / 2);
//            WPIPoint tm = new WPIPoint(rawImage.getWidth() / 2, 0);
//            WPIPoint bm = new WPIPoint(rawImage.getWidth() / 2, rawImage.getHeight());


            //            rawImage.drawLine(lm, rm, WPIColor.WHITE, 4);
//            rawImage.drawLine(tm, bm, WPIColor.WHITE, 4);

            // Draw the biggest polygon green
            if (biggestPoly != null) {
                rawImage.drawPolygon(biggestPoly, WPIColor.GREEN, 6);
            }

            // Check to see if the second biggest is inside the biggest
            if (secondBiggestPoly != null) {

                // Turn the polygons into rectangles
                Rectangle smallRect = new Rectangle(secondBiggestPoly.getPoints());
                Rectangle bigRect = new Rectangle(biggestPoly.getPoints());

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
                    rawImage.drawPolygon(secondBiggestPoly, WPIColor.MAGENTA, 6);

                } else {
                    // Get rid of them because they FAILED!
                    biggestPoly = null;
                    secondBiggestPoly = null;
                }
            } else {
                // There was no smaller polygon, so get rid of the bigger one
                biggestPoly = null;
            }

            // If not null, then biggestPoly is the target
            if (biggestPoly != null) {

                WPIPoint ric = centerOfImage(rawImage);

                WPIPoint topfirst = new WPIPoint(ric.getX() - biggestPoly.getWidth() / 2, 0);
                WPIPoint topsecond = new WPIPoint(ric.getX() + biggestPoly.getWidth() / 2, 0);
                WPIPoint bottomfirst = new WPIPoint(ric.getX() - biggestPoly.getWidth() / 2, rawImage.getHeight());
                WPIPoint bottomsecond = new WPIPoint(ric.getX() + biggestPoly.getWidth() / 2, rawImage.getHeight());


              //  rawImage.drawLine(topfirst, bottomfirst, WPIColor.WHITE, 5);
               // rawImage.drawLine(topsecond, bottomsecond, WPIColor.WHITE, 5);


                Rectangle rectangle = new Rectangle(biggestPoly.getPoints());

                // rawImage.drawRect(ric.getX() - biggestPoly.getWidth() / 2, ric.getY() - biggestPoly.getHeight() / 2, biggestPoly.getWidth(), biggestPoly.getHeight(), WPIColor.WHITE, 5);

                //FINDS THE DISTANCE TO THE TARGET!!
                double heightAverage = (rectangle.getRightHeight() + rectangle.getLeftHeight()) / 2;
                double trueAverageHight = heightAverage / rawImage.getHeight();

                //distanceToBackWall = (36.4964/ trueAverageHight);


                distanceToBackWall = (((slope.getValue().doubleValue() / trueAverageHight) + yInt.getValue().doubleValue()) / .0254);


                //will work when the distanceToBottomTarget code is fixed.
                // distance2BackWall: distance between shooter center & back wall in inches
                // 38(inch): width of red board
                // 15.5 (inch): distance from robot front to the shooter center
                // 38 + 11.5 = 49.5 (back), 38+15.5 = 53.5 (front)


                // Find the difference between x values
                double centerImageX = rawImage.getWidth() / 2;
                double centerRectX = rectangle.getCenterpoint().getX();
                double difference = centerRectX - centerImageX;

                // rescale to make the difference -1 if all the way left, 1 if all the way right
                difference *= 2.0 / rawImage.getWidth();


                if ((distanceToBackWall * (0.0254 / 1.0)) < 8) { //it is 8m
                    // Draw it
                    rawImage.drawPolygon(biggestPoly, WPIColor.RED, 10);
                    if (dataCount <= 10) {
                        dataCount++;
                        sumOfDistances += distanceToBackWall;
                        sumOfAverageTargetHight += trueAverageHight;
                        sumOfCenterpointDifferences += difference;


                    } else {
                        double meanDistance = (sumOfDistances / dataCount);
                        double meanAverageTargetHight = (sumOfAverageTargetHight / dataCount);
                        double meanDiffference = (sumOfCenterpointDifferences / dataCount);

                        // Send the difference to the robot
                        NetworkTable table = NetworkTable.getTable("camera");
                        table.putBoolean("foundTarget", true);
                        table.putDouble("xDifference", meanDiffference);
                        table.putDouble("distanceToBottomTarget", meanDistance);
                        table.putDouble("imageTargetRatio", meanAverageTargetHight);
                        
                        table.putDouble("topfirstx", topfirst.getX());
                        table.putDouble("topfirsty", topfirst.getY());
                        
                        table.putDouble("topsecondx", topsecond.getX());
                        table.putDouble("topsecondy", topsecond.getY());
                        
                        table.putDouble("bottomfirstx", bottomfirst.getX());
                        table.putDouble("bottomfirsty", bottomfirst.getY());
                        
                        table.putDouble("bottomsecondx", bottomsecond.getX());
                        table.putDouble("bottomsecondy", bottomsecond.getY());
                        
                        
                        // RobotTable.getTable

                        System.out.println(trueAverageHight + " --> "
                                + (distanceToBackWall - 49.5) + " inch");//NOT CORECT!! just for a test change back to 49.5
                        dataCount = 0;
                        sumOfDistances = 0;
                        sumOfAverageTargetHight = 0;
                        sumOfCenterpointDifferences = 0;

                    }

                    // RobotTable.getTable
                } else {// ignore target on the other side

                    NetworkTable table = NetworkTable.getTable("camera");
                    table.putBoolean("foundTarget", false);
                    table.putDouble("xDifference", 0);
                }
            } else {
                // no target visible
                NetworkTable table = NetworkTable.getTable("camera");
                table.putBoolean("foundTarget", false);
                table.putDouble("xDifference", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rawImage;
    }

    public void processIPLImage(WPIColorImage img) {
        WPIImageWrapper wrp = new WPIImageWrapper(img);
        IplImage ipl = wrp.getIplImage();

    }

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
    //Finds the distance between the center of the target and the center of the image.
    // public double getAmountOff(WPIPoint centerpoint, WPIPoint centerOfImage) {
    //    return distance(centerpoint, centerOfImage);
    // }
//if polly is to the right, its positive if to the left, negative.
    //  public double getDifference(WPIPoint centerpoint, WPIPoint centerOfImage, WPIColorImage rawImage) {
    //   return (centerpoint.getX() - centerOfImage.getX()) / rawImage.getWidth();
    //  }

    public boolean detectedTarget(WPIColorImage rawImage, boolean DERP) {
        try {
            //Gets the 3 channels.
            WPIGrayscaleImage red = rawImage.getRedChannel();
            WPIGrayscaleImage blue = rawImage.getBlueChannel();
            WPIGrayscaleImage green = rawImage.getGreenChannel();

            //Thresholding! YAY!
            WPIBinaryImage thresholdg = green.getThreshold(pGreen);
            WPIBinaryImage thresholdb = blue.getThreshold(pBlue);
            WPIBinaryImage thresholdr = red.getThresholdInverted(pRed);

            //combining 3 images with thresholds.
            WPIBinaryImage threshold = thresholdb.getAnd(thresholdg);
            threshold.and(thresholdr);
            threshold.dilate(pDial);//@@@
            threshold.erode(pErode);//@@@

            //finding contours! :)
            WPIContour[] contours = threshold.findContours();
            rawImage.drawContours(contours, WPIColor.BLUE, 2);

            // Get the biggest and second biggest rectangles
            WPIPolygon biggestPoly = null;
            WPIPolygon secondBiggestPoly = null;
            int maxArea = 0;
            int secondMaxArea = 0;
            //Finds the biggest polygon.
            for (WPIContour contour : contours) {
                // Approximate the contour into a polygon@@@@
                WPIPolygon polygon = contour.approxPolygon(approxInt.getValue());

                // Make sure it has 4 points
                WPIPoint[] points = polygon.getPoints();
                if (points.length == 4) {
                    // Turn into a rectangle
                    Rectangle rectangle = new Rectangle(points);


                    // Make sure the rectangle is the lower backboard
                    if (rectangle.getCenterpoint().getY() > (rawImage.getHeight() / 2)) {
                        // Check to see if this is the biggest polygon so far
                        int currArea = polygon.getArea();
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

            // Check to see if the second biggest is inside the biggest
            if (secondBiggestPoly != null) {

                // Turn the polygons into rectangles
                Rectangle smallRect = new Rectangle(secondBiggestPoly.getPoints());
                Rectangle bigRect = new Rectangle(biggestPoly.getPoints());

                // Finds the centerpoints of the rectangles
                WPIPoint bigCenterpoint = bigRect.getCenterpoint();
                WPIPoint smallCenterpoint = smallRect.getCenterpoint();


                // Checks to see whether we're actually look at the target
                // (does it by making sure centerpoints are close)
                if ((smallCenterpoint.getX() < bigCenterpoint.getX() + pDiffPix
                        && smallCenterpoint.getX() > bigCenterpoint.getX() - pDiffPix)
                        && (smallCenterpoint.getY() < bigCenterpoint.getY() + pDiffPix
                        && smallCenterpoint.getY() > bigCenterpoint.getY() - pDiffPix)) {

                    return true;
                }
            }
           



            return DERP;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    int pRed = 230;
    int pBlue = 230;
    int pGreen = 230;
    int pErode = 2;
    int pDial = 2;
    int pApproxInt = 9;
    int pDiffPix = 7;

    public boolean autoTune(WPIColorImage rawImage, int setting) {

        this.tuneSettingInt.setValue(0); // reset once it applied the new setting
        switch (setting) {
            case 0:
                // do nothing. take the default setting
                break;
            case 1: // basic setting that works under indirect light (armory + catalyst eve.)
                System.out.println("TUNING: basic setting that works under indirect light (armory + catalyst eve.)");
                redThreshold.setValue(255);
                blueThreshold.setValue(200);
                greenThreshold.setValue(250);
                differenceInPixels.setValue(7);
                dialteInt.setValue(3);
                erodeInt.setValue(1);
                approxInt.setValue(9);
                break;
            case 2: // morning 11:40am
                System.out.println("TUNING: morning 11:40am catalyst");
                redThreshold.setValue(255);
                blueThreshold.setValue(200);
                greenThreshold.setValue(250);
                differenceInPixels.setValue(7);
                dialteInt.setValue(2);
                erodeInt.setValue(1);
                approxInt.setValue(9);
                break;
            case 3: // midday 
                System.out.println("TUNING: morning 12:45pm catalyst");
                redThreshold.setValue(255);
                blueThreshold.setValue(200);
                greenThreshold.setValue(250);
                differenceInPixels.setValue(7);
                dialteInt.setValue(2);
                erodeInt.setValue(2);
                approxInt.setValue(9);
                break;
            case 4: // 5:40pm bright (Catalyst)
                System.out.println("TUNING: 5:40pm bright (Catalyst)");
                redThreshold.setValue(255);
                blueThreshold.setValue(220);
                greenThreshold.setValue(200);
                differenceInPixels.setValue(7);
                dialteInt.setValue(5);
                erodeInt.setValue(1);
                approxInt.setValue(9);
                break;
             case 5: //field pittsburgh
                System.out.println("TUNING: Pittsburgh regional field");
                redThreshold.setValue(255);
                blueThreshold.setValue(240);
                greenThreshold.setValue(240);
                differenceInPixels.setValue(7);
                dialteInt.setValue(3);
                erodeInt.setValue(1);
                approxInt.setValue(9);
                break;   
             case 6: //Black background
                System.out.println("TUNING: black background");
                redThreshold.setValue(255);
                blueThreshold.setValue(220);
                greenThreshold.setValue(220);
                differenceInPixels.setValue(7);
                dialteInt.setValue(2);
                erodeInt.setValue(0);
                approxInt.setValue(9);
                break;   

            default:
                System.out.println("Try autotuning. This will take a while. Ctrl-C to stop.");

        }
        return true;
    }
}
