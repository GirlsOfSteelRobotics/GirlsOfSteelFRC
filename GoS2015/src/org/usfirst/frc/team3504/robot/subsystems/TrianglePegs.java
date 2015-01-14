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
 *
  @annika
  @ziya
 */
public class TrianglePegs extends Subsystem {
	
	private Talon LeftTalon;
	private Talon RightTalon;
	private Encoder LeftEncoder;
	private Encoder RightEncoder;
	private DigitalInput LeftLimit;
	private DigitalInput RightLimit;
	
	public TrianglePegs()
	{
		LeftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B);
		RightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B);
		LeftTalon = new Talon(RobotMap.LEFT_TALON_PEG_A);
		RightTalon = new Talon(RobotMap.RIGHT_TALON_PEG_A);
		LeftLimit = new DigitalInput(RobotMap.LEFT_LIMIT_A);
		RightLimit = new DigitalInput(RobotMap.RIGHT_LIMIT_A);
	}
	
	public void PegDown()
	{
		LeftTalon.set(.23);
		RightTalon.set(.23);
	}
	
	public void PegStop()
	{
		LeftTalon.stopMotor();
		RightTalon.stopMotor();
	}
	
	public boolean GetLimit()
	{
		return (LeftLimit.get() && RightLimit.get());
	}
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public void TrianglePegs() {
		// TODO Auto-generated method stub
		
	}
}

