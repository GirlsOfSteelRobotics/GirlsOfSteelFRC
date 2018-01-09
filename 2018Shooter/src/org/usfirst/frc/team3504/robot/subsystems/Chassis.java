package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.Drive;

import com.ctre.phoenix.MotorControl.ControlMode;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Chassis extends Subsystem {
	private TalonSRX driveLeftA;
	private TalonSRX driveLeftB;
	private TalonSRX driveLeftC;

	private TalonSRX driveRightA;
	private TalonSRX driveRightB;
	private TalonSRX driveRightC;

	private DifferentialDrive robotDrive;

	public Chassis() {
		driveLeftA = new TalonSRX(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new TalonSRX(RobotMap.DRIVE_LEFT_B);
		driveLeftC = new TalonSRX(RobotMap.DRIVE_LEFT_C);
		driveRightA = new TalonSRX(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new TalonSRX(RobotMap.DRIVE_RIGHT_B);
		driveRightC = new TalonSRX(RobotMap.DRIVE_RIGHT_C);

		/*driveLeftA.enableBrakeMode(true);
		driveLeftB.enableBrakeMode(true);
		driveLeftC.enableBrakeMode(true);
		driveRightA.enableBrakeMode(true);
		driveRightB.enableBrakeMode(true);
		driveRightC.enableBrakeMode(true);*/

		driveLeftB.set(ControlMode.Follower, driveLeftA.getDeviceID());
		driveLeftC.set(ControlMode.Follower, driveLeftA.getDeviceID());
		driveRightB.set(ControlMode.Follower, driveRightA.getDeviceID());
		driveRightC.set(ControlMode.Follower, driveRightA.getDeviceID());

		setupEncoder(driveLeftA);
		setupEncoder(driveRightA);
		
		setupFPID(driveLeftA);
		setupFPID(driveRightA);

		robotDrive = new DifferentialDrive(driveLeftA.getWPILIB_SpeedController(), driveRightA.getWPILIB_SpeedController());
		// Set some safety controls for the drive system
		robotDrive.setSafetyEnabled(true);
		robotDrive.setExpiration(0.2);
		//robotDrive.setSensitivity(0.5); //not available yet for DifferentialDrive
		robotDrive.setMaxOutput(1.0);

		//robotDrive.setInvertedMotor(DifferentialDrive.MotorType.kRearLeft, false); //not available yet for Differential Drive
		//robotDrive.setInvertedMotor(DifferentialDrive.MotorType.kRearRight, false);

//		LiveWindow.addActuator("Chassis", "driveLeftA", driveLeftA);
//		LiveWindow.addActuator("Chassis", "driveLeftB", driveLeftB);
//		LiveWindow.addActuator("Chassis", "driveLeftC", driveLeftC);
//		LiveWindow.addActuator("Chassis", "driveRightA", driveRightA);
//		LiveWindow.addActuator("Chassis", "driveRightB", driveRightB);
//		LiveWindow.addActuator("Chassis", "driveRightC", driveRightC);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Drive());
	}

	public TalonSRX getLeftTalon() {
		return driveLeftA;
	}

	public TalonSRX getRightTalon() {
		return driveRightA;
	}

	public void arcadeDrive() {
		robotDrive.arcadeDrive(Robot.oi.getDrivingJoystickY(), Robot.oi.getDrivingJoystickX());
	}

	public void setupEncoder(TalonSRX talon) { // only call this on non-follower
												// talons
		// Set Encoder Types
		//talon.setFeedbackDevice(TalonSRX.FeedbackDevice.QuadEncoder); //not available yet for Differential Drive
		//talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);//not available yet for Differential Drive
		talon.setSensorPhase(false);
	}

	public void setupFPID(TalonSRX talon) { // values work with QuadEncoder for
											// drive talons
		// PID Values
		talon.config_kF(0, 0.32, 0);
		talon.config_kP(0, 0.0, 0);
		talon.config_kI(0, 0.0, 0);
		talon.config_kD(0, 0.0, 0);
	}

	public void turn(double speed, double curve) {
		robotDrive.arcadeDrive(speed, curve);
	}

	public void stop() {
		
		driveLeftA.set(ControlMode.Velocity, 0.0);
		driveRightA.set(ControlMode.Velocity, 0.0);
	}

}