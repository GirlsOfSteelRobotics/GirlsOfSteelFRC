package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Chassis extends Subsystem {
	private CANTalon driveLeftA;
	private CANTalon driveLeftB;
	private CANTalon driveLeftC;
	
	private CANTalon driveRightA;
	private CANTalon driveRightB;
	private CANTalon driveRightC;
	
	private RobotDrive robotDrive;

	private double encOffsetValue = 0;
	
	public Chassis() {
		driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B);
		driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C);
		driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B);
		driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C);
		
		robotDrive = new RobotDrive(driveLeftA, driveRightA);
		
		// Set some safety controls for the drive system
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.1);
		robotDrive.setSensitivity(0.5);
		robotDrive.setMaxOutput(1.0);
		
		driveLeftB.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveLeftC.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveRightB.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveRightC.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveLeftB.set(driveLeftA.getDeviceID());
		driveLeftC.set(driveLeftA.getDeviceID());
		driveRightB.set(driveRightA.getDeviceID());
		driveRightC.set(driveRightA.getDeviceID());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand( new DriveByJoystick() );
    }
    
    public void driveByJoystick(Joystick stick) {
    	robotDrive.arcadeDrive(stick);
    }
    public void drive(double moveValue, double rotateValue){
    	robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void driveSpeed(double speed){
    	robotDrive.drive(-speed, 0);
    }
    
    public void stop() {
    	robotDrive.drive(0, 0);
    }
    
	public double getEncoderRight() {
		return driveRightA.getEncPosition();
	}

	public double getEncoderLeft() {
		return driveLeftA.getEncPosition();
	}

	public double getEncoderDistance() {
		if (Robot.shifters.getGearSpeed())
			return (getEncoderRight() - encOffsetValue) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
		else
			return (getEncoderRight() - encOffsetValue) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR;
	}

	public void resetDistance() {
		encOffsetValue = getEncoderRight();
	}
}


