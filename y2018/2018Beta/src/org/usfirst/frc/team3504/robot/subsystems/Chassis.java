package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Chassis extends Subsystem {
	private WPI_TalonSRX driveLeftA;
	private WPI_TalonSRX driveLeftB;
	private WPI_TalonSRX driveLeftC;

	private WPI_TalonSRX driveRightA;
	private WPI_TalonSRX driveRightB;
	private WPI_TalonSRX driveRightC;

	public DifferentialDrive drive; 

	public Chassis() {
		driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
		driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C);
		driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);
		driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C);

		driveLeftA.setNeutralMode(NeutralMode.Brake);
		driveLeftB.setNeutralMode(NeutralMode.Brake);
		driveLeftC.setNeutralMode(NeutralMode.Brake);
		driveRightA.setNeutralMode(NeutralMode.Brake);
		driveRightB.setNeutralMode(NeutralMode.Brake);
		driveRightC.setNeutralMode(NeutralMode.Brake);

		driveLeftB.follow(driveLeftA);
		driveLeftC.follow(driveLeftA);
		driveRightB.follow(driveRightA);
		driveRightC.follow(driveRightA);

		setupEncoder(driveLeftA);
		setupEncoder(driveRightA);

		//drive.setSafetyEnabled(false);
		//drive.setExpiration(0.2);
		//drive.setMaxOutput(1.0);

		driveLeftA.setInverted(false);
		driveRightA.setInverted(false);
		
		drive = new DifferentialDrive(driveLeftA, driveRightA);
		//drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
		//drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);

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

	public WPI_TalonSRX getLeftTalon() {
		return driveLeftA;
	}

	public WPI_TalonSRX getRightTalon() {
		return driveRightA;
	}

	public void setupEncoder(WPI_TalonSRX talon) { // only call this on non-follower
												// talons
		// Set Encoder Types
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);
		talon.setSensorPhase(true);
	}

	public void setupFPID(WPI_TalonSRX talon) { // values work with QuadEncoder for
											// drive talons
		// PID Values
		talon.setSelectedSensorPosition(0, 0, 0);
		talon.config_kF(0, 0, 0);
		talon.config_kP(0, 0.32, 0);
		talon.config_kI(0, 0, 0);
		talon.config_kD(0, 0, 0);
	}

	public void turn(double speed, double curve) {
		drive.curvatureDrive(speed, curve, false);
	}

	public void stop() {
		driveLeftA.set(ControlMode.PercentOutput, 0);
		driveRightA.set(ControlMode.PercentOutput, 0);
	}

}
