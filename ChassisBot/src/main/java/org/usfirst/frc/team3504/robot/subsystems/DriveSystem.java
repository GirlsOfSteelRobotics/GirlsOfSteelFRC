package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FollowerType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



public class DriveSystem extends Subsystem {
	private WPI_TalonSRX driveLeftA;
	private WPI_TalonSRX driveLeftB;
	//private WPI_TalonSRX driveLeftC;
	
	private WPI_TalonSRX driveRightA;
	private WPI_TalonSRX driveRightB;
	//private WPI_TalonSRX driveRightC;
	
	private DifferentialDrive robotDrive;
	
	private double encOffsetValueRight = 0;
	private double encOffsetValueLeft = 0;

	
	public DriveSystem() {
		driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A_CAN_ID);
		//LiveWindow.addActuator("Drive System", "Drive Left A", driveLeftA);
		driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B_CAN_ID);
		//LiveWindow.addActuator("Drive System", "Drive Left B", driveLeftB);

		driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A_CAN_ID);
		//LiveWindow.addActuator("Drive System", "Drive Right A", driveRightA);
		driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B_CAN_ID);
		//LiveWindow.addActuator("Drive System", "Drive Right B", driveRightB);

		// On each side, all three drive motors MUST run at the same speed.
		// Use the CAN Talon Follower mode to set the speed of B and C,
		// making always run at the same speed as A.
		driveLeftB.follow(driveLeftA, FollowerType.PercentOutput);
		driveRightB.follow(driveRightA, FollowerType.PercentOutput);
	    
		// Define a robot drive object in terms of only the A motors.
		// The B and C motors will play along at the same speed (see above.)
		robotDrive = new DifferentialDrive(driveLeftA, driveRightA);

		// Set some safety controls for the drive system
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.1);
		robotDrive.setMaxOutput(1.0);
	}
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		setDefaultCommand(new DriveByJoystick());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void takeJoystickInputs(Joystick joystk) {
		robotDrive.arcadeDrive(-joystk.getY(),joystk.getX());
	}

	public void forward() {
		robotDrive.arcadeDrive(1.0, 0);
	}

	public void stop() {
		robotDrive.stopMotor();
	}

	public double getEncoderRight() {
		return driveRightA.getSelectedSensorPosition();
	}

	public double getEncoderLeft() {
		return -driveLeftA.getSelectedSensorPosition();
	}

	public double getEncoderDistance() {
		SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE);
		SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE);
		return (getEncoderRight() - encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE;
	}

	public void resetDistance() {
		encOffsetValueRight = getEncoderRight();
		encOffsetValueLeft = getEncoderLeft();
	}
	
	public void driveByJoystick(Joystick stick) {
		takeJoystickInputs(stick);
		
	}
}