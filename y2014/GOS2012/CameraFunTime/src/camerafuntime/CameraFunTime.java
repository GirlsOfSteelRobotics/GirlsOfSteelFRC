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
        WPIGrayscaleImage green = rawImage.getGreenChannel ();

        WPIBinaryImage thresholdr = red.getThreshold(110);
        WPIBinaryImage thresholdb = blue.getThresholdInverted(85);
        WPIBinaryImage thresholdg = green.getThresholdInverted(85);
        WPIBinaryImage threshold = thresholdb.getAnd(thresholdg);
        threshold.and(thresholdr);
        threshold.erode(2);
        threshold.dilate(5);
        WPIContour[] contours = threshold.findContours();
        rawImage.drawContours(contours, WPIColor.BLUE, 5);


        return rawImage;
    }
}
