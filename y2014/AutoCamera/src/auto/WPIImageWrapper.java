/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package auto;
import edu.wpi.first.wpijavacv.*;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 *
 * @author pkb
 */
public class WPIImageWrapper extends WPIImage {

    public WPIImageWrapper(WPIImage wpiImage) {
        super(wpiImage.getBufferedImage());
    }

    public IplImage getIplImage() {
        return image;
    }
}
