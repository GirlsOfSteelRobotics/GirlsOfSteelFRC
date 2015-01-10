package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Sonia
 */
public class Chassis extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	RobotDrive gosDrive;
    Gyro robotGyro;
	
	public Chassis()
	{
        gosDrive = new RobotDrive(RobotMap.FRONT_LEFT_CHANNEL, RobotMap.REAR_LEFT_CHANNEL,
        							RobotMap.FRONT_RIGHT_CHANNEL, RobotMap.REAR_RIGHT_CHANNEL);
        gosDrive.setExpiration(0.1);
        gosDrive.setInvertedMotor(MotorType.kFrontRight, true);	// invert the left side motors
    	gosDrive.setInvertedMotor(MotorType.kRearRight, true);		// may need to change or remove this to match robot
    	gosDrive.setSafetyEnabled(true);
    	
        robotGyro = new Gyro(RobotMap.GYRO_PORT);
	}
	
	public void moveByJoystick(Joystick stick)
	{
		gosDrive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), 0);//robotGyro.getAngle());
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
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
