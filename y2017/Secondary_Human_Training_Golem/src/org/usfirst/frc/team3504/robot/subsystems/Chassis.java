package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.OI.DriveStyle;
import org.usfirst.frc.team3504.robot.commands.Drive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	public Chassis() {
		driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B);
		driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C);
		driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B);
		driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C);

		driveLeftA.enableBrakeMode(true);
		driveLeftB.enableBrakeMode(true);
		driveLeftC.enableBrakeMode(true);
		driveRightA.enableBrakeMode(true);
		driveRightB.enableBrakeMode(true);
		driveRightC.enableBrakeMode(true);

		driveLeftB.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveLeftC.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveRightB.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveRightC.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveLeftB.set(driveLeftA.getDeviceID());
		driveLeftC.set(driveLeftA.getDeviceID());
		driveRightB.set(driveRightA.getDeviceID());
		driveRightC.set(driveRightA.getDeviceID());

		setupEncoder(driveLeftA);
		setupEncoder(driveRightA);

		robotDrive = new RobotDrive(driveLeftA, driveRightA);
		// Set some safety controls for the drive system
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.2);
		robotDrive.setSensitivity(0.5);
		robotDrive.setMaxOutput(1.0);

		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);

		LiveWindow.addActuator("Chassis", "driveLeftA", driveLeftA);
		LiveWindow.addActuator("Chassis", "driveLeftB", driveLeftB);
		LiveWindow.addActuator("Chassis", "driveLeftC", driveLeftC);
		LiveWindow.addActuator("Chassis", "driveRightA", driveRightA);
		LiveWindow.addActuator("Chassis", "driveRightB", driveRightB);
		LiveWindow.addActuator("Chassis", "driveRightC", driveRightC);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Drive());
	}

	public CANTalon getLeftTalon() {
		return driveLeftA;
	}

	public CANTalon getRightTalon() {
		return driveRightA;
	}

	public void arcadeDrive() {
		robotDrive.arcadeDrive(Robot.oi.getDrivingJoystickY(), Robot.oi.getDrivingJoystickX());
	}
	
	public void tankDrive() {
		robotDrive.tankDrive(Robot.oi.getDrivingJoystickLeft(), Robot.oi.getDrivingJoystickRight());
	}

	public void setupEncoder(CANTalon talon) { // only call this on non-follower
												// talons
		// Set Encoder Types
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);
		talon.reverseSensor(false);
	}

	public void setupFPID(CANTalon talon) { // values work with QuadEncoder for
											// drive talons
		// PID Values
		talon.setPosition(0);
		talon.setF(0);
		talon.setP(0.32); // 0.64 good
		talon.setI(0.0);
		talon.setD(0.0);
	}

	public void turn(double speed, double curve) {
		robotDrive.drive(speed, curve);
	}

	public void stop() {
		driveLeftA.set(0);
		driveRightA.set(0);
	}

	public void setPositionMode() {
		driveLeftA.changeControlMode(TalonControlMode.Position);
		driveRightA.changeControlMode(TalonControlMode.Position);
	}

	public void setPercentVbusMode() {
		driveLeftA.changeControlMode(TalonControlMode.PercentVbus);
		driveRightA.changeControlMode(TalonControlMode.PercentVbus);
	}

	public void setSpeedMode() {
		driveLeftA.changeControlMode(TalonControlMode.Speed);
		driveRightA.changeControlMode(TalonControlMode.Speed);
	}

	public void setMotionProfileMode() {
		driveLeftA.changeControlMode(TalonControlMode.MotionProfile);
		driveRightA.changeControlMode(TalonControlMode.MotionProfile);
	}

}
