package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.TestPhotoSensor;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author arushibandi
 */
public class PhotoSensor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private AnalogInput psensor_lightinput;
	private AnalogInput psensor_darkinput;
	
	public PhotoSensor() {
		
		psensor_lightinput = new AnalogInput(RobotMap.PHOTOSENSOR_CHANNEL_LIGHTINPUT);
    	psensor_darkinput = new AnalogInput(RobotMap.PHOTOSENSOR_CHANNEL_DARKINPUT);	
		
	}
	
	public int getLightValue() {
		return psensor_lightinput.getValue();
	}
	
	public int getDarkValue() {
		return psensor_darkinput.getValue();
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand (new TestPhotoSensor());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

