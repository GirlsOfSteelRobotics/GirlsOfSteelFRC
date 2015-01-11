package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Sonia
 */
public class Chassis extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private RobotDrive gosDrive;
    private Gyro robotGyro;
    private Encoder frontLeftEncoder;
    private Encoder rearLeftEncoder;
    private Encoder frontRightEncoder;
    private Encoder rearRightEncoder;
	
	public Chassis()
	{
        gosDrive = new RobotDrive(RobotMap.FRONT_LEFT_CHANNEL, RobotMap.REAR_LEFT_CHANNEL,
        							RobotMap.FRONT_RIGHT_CHANNEL, RobotMap.REAR_RIGHT_CHANNEL);
        gosDrive.setInvertedMotor(MotorType.kFrontRight, true);	// invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kRearRight, true);		// may need to change or remove this to match robot
    	gosDrive.setExpiration(0.1);
    	gosDrive.setSafetyEnabled(true);
    	
    	frontLeftEncoder = new Encoder(RobotMap.FRONT_LEFT_ENCODER_A, RobotMap.FRONT_LEFT_ENCODER_B);
    	rearLeftEncoder = new Encoder(RobotMap.REAR_LEFT_ENCODER_A, RobotMap.REAR_LEFT_ENCODER_B);
    	frontRightEncoder = new Encoder(RobotMap.FRONT_RIGHT_ENCODER_A, RobotMap.FRONT_RIGHT_ENCODER_B);
    	rearRightEncoder = new Encoder(RobotMap.REAR_RIGHT_ENCODER_A, RobotMap.REAR_RIGHT_ENCODER_B);
    	
        robotGyro = new Gyro(RobotMap.GYRO_PORT);
	}
	
	public void moveByJoystick(Joystick stick)
	{
		if(stick.getMagnitude() > 0.1)
			gosDrive.mecanumDrive_Polar(0.5, stick.getDirectionDegrees(), stick.getZ());
		//gosDrive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), 0);//robotGyro.getAngle());
	}
	
	public void driveForward()
	{
		gosDrive.mecanumDrive_Cartesian(0, -.5, 0, 0);
	}

	public void driveBackward()
	{
		gosDrive.mecanumDrive_Cartesian(0, .5, 0, 0);
	}
	
	public void stop()
	{
		gosDrive.stopMotor();
	}
	
	public double getFrontLeftEncoderRate()
	{
		return frontLeftEncoder.getRate();
	}
	
	public double getRearLeftEncoderRate()
	{
		return rearLeftEncoder.getRate();
	}
	
	public double getFrontRightEncoderRate()
	{
		return frontRightEncoder.getRate();
	}
	
	public double getRearRightEncoderRate()
	{
		return rearRightEncoder.getRate();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveByJoystick());
    }
}
