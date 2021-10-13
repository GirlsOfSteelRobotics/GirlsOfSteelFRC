/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supercamerafuntime;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.wpijavacv.*;

/**
 *
 * @author user
 */
public class CameraFunTime extends WPICameraExtension {

    @Override
    public WPIImage processImage(WPIColorImage rawImage) {

        WPIGrayscaleImage red = rawImage.getRedChannel();
        WPIGrayscaleImage blue = rawImage.getBlueChannel();
        WPIGrayscaleImage green = rawImage.getGreenChannel();

        WPIBinaryImage thresholdr = red.getThreshold(90);
        thresholdr.dilate(2);
        WPIBinaryImage thresholdb = blue.getThresholdInverted(100);
        WPIBinaryImage thresholdg = green.getThresholdInverted(100);
        WPIBinaryImage threshold = thresholdb.getAnd(thresholdg);
        threshold.and(thresholdr);
        threshold.erode(2);
        threshold.dilate(6);
        WPIContour[] contours = threshold.findContours();
        rawImage.drawContours(contours, WPIColor.BLUE, 5);
        WPIPolygon bigPoly = null;
        int maxArea = 0;

        for (WPIContour contour : contours) {
            WPIPolygon polygon = contour.approxPolygon(8);
            int currArea = polygon.getArea();
            WPIPoint[] points = polygon.getPoints();
            if (points.length == 4 && currArea > maxArea) {
                maxArea = currArea;
                bigPoly = polygon;
            }
        }
        if (bigPoly != null) {
            rawImage.drawPolygon(bigPoly, WPIColor.GREEN, 5);
            double distance = 1385.65 / bigPoly.getWidth() + 0.09;
            System.out.println(distance);
        } else {
        }


        return rawImage;
    }
}

