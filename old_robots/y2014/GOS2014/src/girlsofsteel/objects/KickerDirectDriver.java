/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.objects;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author Sylvie
 * 
 * This is specifically for the kicker, which needs to get to a position
 * quickly (the pid is too slow over the short distance)
 * 
 * Assumptions:
 * The distance per pulse for the encoder has already been set correctly
 * The distance is in METERS 
 */
public class KickerDirectDriver {
    
    Encoder leftEncoder;
    Encoder rightEncoder;
    Jaguar leftJag;
    Jaguar rightJag;
    private final double JAG_TOP_FORWARD_SPEED = 1;
    private final double JAG_TOP_BACKWARDS_SPEED = -1;
    
    public KickerDirectDriver(Encoder leftEncoder, Encoder rightEncoder, Jaguar leftJag, Jaguar rightJag) {
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.leftJag = leftJag;
        this.rightJag = rightJag;
    }
    
    /*
    Method that sends the top speed to the jags as long as the setpoint 
    has not been reached. 
    */
    public void moveTillSetpoint(double setpoint) {
        rightEncoder.start();
        leftEncoder.start();
        double leftDistance;
        double rightDistance;
        boolean rightLagging = false; //Indicates if the speed of the right has been reduced to match the left
        boolean leftLagging = false; //Indicates if the speed of the left has been reduced to match the right
        double leftJagSpeed = signed(setpoint) * setpoint;
        double rightJagSpeed = signed(setpoint) * setpoint;
        while(leftEncoder.getDistance() < setpoint) { //While you're not there
            leftDistance = leftEncoder.getDistance();
            rightDistance = rightEncoder.getDistance();
            if(leftDistance - rightDistance > 0) { //
                
            }
        }
    }
    
    public double signed(double value) {
        if(value < 0) {
            return -1;
        }
        else if(value > 0) {
            return 1;
        }
        return 0;
    }
    
    
}
