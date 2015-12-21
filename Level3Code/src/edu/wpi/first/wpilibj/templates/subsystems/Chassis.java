/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author appasamysm
 */
public class Chassis extends Subsystem {
    //chassis inherits subsystem's things. It extends it~
    
    public static final double DEADZONE_RANGE = 0.1;
    //DEADZONE_RANGE is  a defined space on the joystick where the robot will not move. 
    //It's 0.1 because of math and Kathyrn Henrickson (aka trial and error). 
    
    private final Jaguar leftJag = new Jaguar(RobotMap.LEFT_JAG);
    //we're defining where the leftJag is on the robot with RobotMap
    
    private final Jaguar rightJag = new Jaguar(RobotMap.RIGHT_JAG);
    //we're defining where the rightJag is on the robot with RobotMap
    
    public void startJags(double yCoordinate, double xCoordinate)
    {
        rightJag.set(getDeadzoneValue(yCoordinate) - getDeadzoneValue(xCoordinate));
        leftJag.set(getDeadzoneValue(yCoordinate) + getDeadzoneValue(xCoordinate));
        //from the drive command, we are adjusting the coordinates for the deadzone 
        //on the xy graph for the Jag 
        //once again it's subtracting getDeadzoneValue because of math (trial and error)
    }
    
    public void stopJags(){
        leftJag.set(0.0);
        rightJag.set(0.0);
        //here we're setting the Jags to zero. When the motors stop, the Jags are zero. 
        //so when the speed is zero, the jags don't move.
    }

    private double getDeadzoneValue(double joystickValue){
        if (joystickValue > DEADZONE_RANGE) {
            return (joystickValue / (1 - DEADZONE_RANGE)) - DEADZONE_RANGE;
        } else if (joystickValue < (-1 * DEADZONE_RANGE)) {
            return (joystickValue / (1 - DEADZONE_RANGE)) + DEADZONE_RANGE;
        } else {
            return 0.0;
        }//end else
        //this determines if the coordinate is in the deadzone and acts accordingly
    }

    protected void initDefaultCommand() {
        //a method that had to be there (/) 
    }
}
