/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.sherlock;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.wpijavacv.*;
/**
 *
 * @author user
 */
public class CamFeed extends WPICameraExtension{
    @Override
    public WPIImage processImage(WPIColorImage rawImage) {
        return rawImage;
        
    }
}
