package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @authors annika and ziya
 */
public class Fingers extends Subsystem {
	private DigitalInput leftLimit;
	private DigitalInput rightLimit;
	private Solenoid fingersLeftSolenoid;
	private Solenoid fingersRightSolenoid;
	
	public Fingers()
	{
		/*
		LeftEncoder = new Encoder(RobotMap.LEFT_FINGER_ENCODER_A, RobotMap.LEFT_FINGER_ENCODER_B);
		RightEncoder = new Encoder(RobotMap.RIGHT_FINGER_ENCODER_A, RobotMap.RIGHT_FINGER_ENCODER_B);
		LeftTalon = new Talon(RobotMap.LEFT_FINGER_TALON);
		RightTalon = new Talon(RobotMap.RIGHT_FINGER_TALON);
		LeftLimit = new DigitalInput(RobotMap.LEFT_FINGER_LIMIT);
		RightLimit = new DigitalInput(RobotMap.RIGHT_FINGER_LIMIT);
		FingersDown = new Solenoid(0);
		**/
	}
	
	public void fingerDown(){
		fingersLeftSolenoid.set(false);
		fingersRightSolenoid.set(false);
	}
	
	public void fingerUp(){	
		fingersLeftSolenoid.set(true);
		fingersRightSolenoid.set(true);
	}
	
	public void fingersLeftSolenoid(){
		fingersLeftSolenoid.set(true);				//pushes the fingers down using pneumatics
	}
	
	public void fingersRightSolenoid(){
		fingersRightSolenoid.set(true);
	}
	public void fingerStop()
	{
		//leftTalon.stopMotor();
		//rightTalon.stopMotor();
	}
	
//	public boolean getLimit()
	{
//		return (leftLimit.get() && rightLimit.get());
	}
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

