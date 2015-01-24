package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.RobotMap;

/**
 * @authors annika and ziya
 */
public class TrianglePegs extends Subsystem {
	
	private Talon LeftTalon;
	private Talon RightTalon;
	private Encoder LeftEncoder;
	private Encoder RightEncoder;
	private DigitalInput LeftLimit;
	private DigitalInput RightLimit;
	private Solenoid liftSD;
	
	public TrianglePegs()
	{
		LeftEncoder = new Encoder(RobotMap.LEFT_PEG_ENCODER_A, RobotMap.LEFT_PEG_ENCODER_B);
		RightEncoder = new Encoder(RobotMap.RIGHT_PEG_ENCODER_A, RobotMap.RIGHT_PEG_ENCODER_B);
		LeftTalon = new Talon(RobotMap.LEFT_PEG_TALON);
		RightTalon = new Talon(RobotMap.RIGHT_PEG_TALON);
		LeftLimit = new DigitalInput(RobotMap.LEFT_PEG_LIMIT);
		RightLimit = new DigitalInput(RobotMap.RIGHT_PEG_LIMIT);
		liftSD = new Solenoid(RobotMap.TRIANGLE_PEG_SOLENOID); 
	}
	
	public void pegDown()
	{
		LeftTalon.set(.23);
		RightTalon.set(.23);
	}

	public void upPneum()
	{
		liftSD.set(true);
	
	}
	
	public void downPneum()
	{
		liftSD.set(false);
	
	}
	
	public void pegStop()
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

