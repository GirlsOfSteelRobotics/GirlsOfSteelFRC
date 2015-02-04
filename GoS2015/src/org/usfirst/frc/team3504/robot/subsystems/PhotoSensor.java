package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PhotoSensor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private DigitalInput psensor_lightinput;
	private DigitalInput psensor_darkinput;
	
	public PhotoSensor() {
		
		psensor_lightinput = new DigitalInput(RobotMap.PHOTOSENSOR_CHANNEL_LIGHTINPUT);
    	psensor_darkinput = new DigitalInput(RobotMap.PHOTOSENSOR_CHANNEL_DARKINPUT);	
		
	}
	
	public boolean getLightValue() {
		return psensor_lightinput.get();
	}
	
	public boolean getDarkValue() {
		return psensor_darkinput.get();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

