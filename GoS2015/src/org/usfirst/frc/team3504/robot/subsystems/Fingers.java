package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.RobotMap;

/**
 * @authors annika and ziya
 */
public class Fingers extends Subsystem {
	
	private Talon LeftTalon;
	private Talon RightTalon;
	private Encoder LeftEncoder;
	private Encoder RightEncoder;
	private DigitalInput LeftLimit;
	private DigitalInput RightLimit;
	private Solenoid FingersDown;
	
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
	
	//public void fingerDown()
	//{
	//	LeftTalon.set(.23);
	//	RightTalon.set(.23);
	//}
	
	public void FingersDown(){
		fingersDown.set(true);				//pushes the fingers down using pneumatics
	}
	public void fingerStop()
	{
		LeftTalon.stopMotor();
		RightTalon.stopMotor();
	}
	
	public boolean getLimit()
	{
		return (LeftLimit.get() && RightLimit.get());
	}
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

