/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.objects;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author Sonia and Bryce
 */
public class CoolWheels {
    
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Jaguar rightJag;
    private Jaguar leftJag;
    
    public CoolWheels(Encoder leftE, Encoder rightE, Jaguar rightJ, Jaguar leftJ)
    {
        leftEncoder = leftE;
        rightEncoder = rightE;
        rightJag = rightJ;
        leftJag = leftJ;
    }
    
}
