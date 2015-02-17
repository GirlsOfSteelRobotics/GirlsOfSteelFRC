package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.TestUltrasonic;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 *
 */
public class UltrasonicSensor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private AnalogInput ultrasonic;
	
	public UltrasonicSensor()
	{
		ultrasonic = new AnalogInput(RobotMap.ULTRASONIC_CHANNEL);
	}
	
	public void getDistanceInches() {
		SmartDashboard.putNumber("ultrasonic", ultrasonic.getValue());
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TestUltrasonic());
    
    }
}

