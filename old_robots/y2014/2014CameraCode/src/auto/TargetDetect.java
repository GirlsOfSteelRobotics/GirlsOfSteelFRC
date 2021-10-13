              /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.BooleanProperty;
import edu.wpi.first.smartdashboard.properties.DoubleProperty;
import edu.wpi.first.smartdashboard.properties.IntegerProperty;
import edu.wpi.first.wpijavacv.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class TargetDetect extends WPICameraExtension {

    IntegerProperty redThreshold = new IntegerProperty(this, "Red Maximum", 80);
    IntegerProperty greenThreshold = new IntegerProperty(this, "Green Minimum", 55);
    IntegerProperty blueThreshold = new IntegerProperty(this, "Blue Maximum", 90);
    IntegerProperty dialteInt = new IntegerProperty(this, "amount dialate", 4);
    IntegerProperty erodeInt = new IntegerProperty(this, "amount erode", 3);
    IntegerProperty approxInt = new IntegerProperty(this, "Poly Approximation", 9);
    DoubleProperty areaThreshold = new DoubleProperty(this, "Area thresh", 50);
    DoubleProperty slope = new DoubleProperty(this, "Slope", 51.438);//based on meter data
    DoubleProperty yInt = new DoubleProperty(this, "Y Intercept", 0.5577);
    BooleanProperty isSlopeFromTable = new BooleanProperty(this, "Is Slope From Table", false);
    BooleanProperty readFromFile = new BooleanProperty(this, "readFromFile", false);
    // DoubleProperty staticHeightWidthRatio = new DoubleProperty(this, "Static Height Width Ratio", 3);
    double targetSlope;
    double heightRatio;
    boolean isTargetRight;
    boolean isTargetLeft;
    boolean isHot = false;
    boolean firstFrame = true;

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
    double distance;

    static int imageNum = 0;
    static int counter = 0;
    //boolean readFromFile = false;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public WPIImage processImage(WPIColorImage rawImage) {
        if (rawImage == null) {
            return rawImage;
        }

        NetworkTable.getTable("SmartDashboard").putString("Camera File Number", "ex 10");
        
        if (readFromFile.getValue()) {
            BufferedImage placeHolder; //put in a random value so 
            String imagNumber = NetworkTable.getTable("SmartDashboard").getString("Camera File Number");
            try {
                placeHolder = ImageIO.read(new File("c:\\Users\\heather\\Documents\\cameraPic" + (imagNumber) + ".png"));
                rawImage = new WPIColorImage(placeHolder);
            } catch (IOException ex) {
                Logger.getLogger(TargetDetect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Gets an image every 10 frames and only gets 10 images
        if (counter++ % 10 == 0 && imageNum < 100) {
            try {
                BufferedImage image = null;
                //try {
                image = rawImage.getBufferedImage();
                File outputfile = new File("c:\\Users\\heather\\Documents\\cameraPic" + (imageNum++) + ".png");

                //boolean flag = ImageIO.getUseCache();
                System.out.println("Making image");
                ImageIO.write(image, "png", outputfile);

            } catch (IOException ex) {
                System.out.println("Threw exception");
                Logger.getLogger(TargetDetect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                System.out.println("Unknown exception: " + e.toString());
            }
        }
        System.out.println("past ImageIO");

        if (isSlopeFromTable.getValue().booleanValue()) {
            slope.setValue(NetworkTable.getTable("camera").getNumber("Slope"));
            yInt.setValue(NetworkTable.getTable("camera").getNumber("yInt"));
        }
        try {
            //Gets the 3 channels.
            WPIGrayscaleImage red = rawImage.getRedChannel();
            WPIGrayscaleImage green = rawImage.getGreenChannel();
            WPIGrayscaleImage blue = rawImage.getBlueChannel();
            // WPIColorImage DERP = IplImage;
            //Thresholding! YAY!
            WPIBinaryImage thresholdg = green.getThreshold(greenThreshold.getValue());
            WPIBinaryImage thresholdr = red.getThresholdInverted(redThreshold.getValue());
            WPIBinaryImage thresholdb = blue.getThresholdInverted(blueThreshold.getValue());
            //combining 2 images with thresholds.
            WPIBinaryImage thresholdedImage = thresholdg;
            thresholdedImage.and(thresholdr);
            thresholdedImage.and(thresholdb);

            thresholdedImage.erode(erodeInt.getValue());//@@@
            thresholdedImage.dilate(dialteInt.getValue());//@@

            //finding contours! :)
            WPIContour[] contours = thresholdedImage.findContours();
            rawImage.drawContours(contours, WPIColor.BLUE, 2);
            WPIPolygon[] polygons = new WPIPolygon[contours.length];
            ArrayList<WPIPolygon> recsList = new ArrayList<WPIPolygon>();
            //Finds the biggest polygon.
//            for (int x = 0; x < 10; x++) {
//                for (WPIContour contour : contours) {
            for (int i = 0; i < polygons.length; i++) {

                // Approximate the contour into a polygon@@@@
                polygons[i] = contours[i].approxPolygon(approxInt.getValue());
                rawImage.drawPolygons(polygons, WPIColor.WHITE, 3);
                WPIPoint[] points = polygons[i].getPoints();
                // Make sure it has 4 points
                if (points.length == 4) {
                    // Turn into apolygons[i].getPoints();
                    // Make sure it has  rectangle
                    recsList.add(polygons[i]);
                }

            }

            WPIPolygon[] rectangles = recsList.toArray(new WPIPolygon[recsList.size()]);
            WPIPolygon[] targetRecs = new WPIPolygon[4];

//            for (int i = 0; i < rectangles.length; i++) {
//                if (rectangles[i].getArea() > areaThreshold.getValue()) {
//                    targetRecs[i] = rectangles[i];
//                }
//            }
//            Arrays.sort(targetRecs, new PolygonAreaCompare());
            ArrayList<WPIPolygon> dynamicTargetList = new ArrayList<WPIPolygon>();
            ArrayList<WPIPolygon> staticTargetList = new ArrayList<WPIPolygon>();
            //sorts rectangles into vertical(static) and horizontal(dynamic) arrays
            for (int i = 0; i < rectangles.length; i++) {
                AutoRectangle currentRectangle = new AutoRectangle(rectangles[i].getPoints());

                if (currentRectangle.averageWidthHeightRatio() < 1) {
                    staticTargetList.add(rectangles[i]);

                } else if (currentRectangle.averageWidthHeightRatio() > 1) {
                    dynamicTargetList.add(rectangles[i]);
                }
            }
            WPIPolygon[] dynamicTargets = dynamicTargetList.toArray(new WPIPolygon[dynamicTargetList.size()]);
            WPIPolygon[] staticTargets = staticTargetList.toArray(new WPIPolygon[staticTargetList.size()]);

            rawImage.drawPolygons(rectangles, WPIColor.GRAY, 3);
            rawImage.drawPolygons(dynamicTargets, WPIColor.YELLOW, 3);
            rawImage.drawPolygons(staticTargets, WPIColor.MAGENTA, 3);

            if (dynamicTargets.length == staticTargets.length) {
                isHot = true;
                AutoRectangle dTarget = new AutoRectangle(dynamicTargets[0].getPoints());
                AutoRectangle sTarget = new AutoRectangle(staticTargets[0].getPoints());

                targetSlope = getSlope(dTarget.getCenterpoint(), sTarget.getCenterpoint());
                NetworkTable.getTable("SmartDashboard").putNumber("slope", targetSlope);
                if (targetSlope > 0) {
                    isTargetRight = true;
                    isTargetLeft = false;
                } else if (targetSlope < 0) {
                    isTargetLeft = true;
                    isTargetRight = false;
                } else {
                    isTargetLeft = false;
                    isTargetRight = false;
                }

                heightRatio = rawImage.getHeight() / sTarget.averageHeight();

                distance = (slope.getValue() * heightRatio + yInt.getValue()) / 39.37;
                NetworkTable.getTable("SmartDashboard").putNumber("camera distance", distance);
                NetworkTable.getTable("camera").putNumber("distance", distance);

            } else {
                isHot = false;
                isTargetLeft = false;
                isTargetRight = false;
            }
            NetworkTable.getTable("SmartDashboard").putNumber("targetRatio", heightRatio);
            NetworkTable.getTable("camera").putNumber("targetRatio", heightRatio);
            NetworkTable.getTable("camera").putBoolean("isHot", isHot);
            NetworkTable.getTable("SmartDashboard").putBoolean("isHot", isHot);
            NetworkTable.getTable("SmartDashboard").putBoolean("isTargetRight", isTargetRight);
            NetworkTable.getTable("SmartDashboard").putBoolean("isTargetLeft", isTargetLeft);
            NetworkTable.getTable("camera").putBoolean("isTargetRight", isTargetRight);
            NetworkTable.getTable("camera").putBoolean("isTargetLeft", isTargetLeft);
            NetworkTable.getTable("camera").putBoolean("isConnected", true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        firstFrame = false;
        return rawImage;
    }
//    public void processIPLImage(WPIColorImage img) {
//        WPIImageWrapper wrp = new WPIImageWrapper(img);
//        IplImage ipl = wrp.getIplImage();
//
//    }
    //Finds distance between two points. 

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

    public double getSlope(WPIPoint point1, WPIPoint point2) {
        double dy = point1.getY() - point2.getY();
        double dx = point1.getX() - point2.getX();
        return (-dy / dx);
    }
}
