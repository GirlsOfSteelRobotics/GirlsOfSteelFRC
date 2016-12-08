package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSystem extends Subsystem implements PIDOutput{
	private CANTalon driveLeftA;
	private CANTalon driveLeftB;
	private CANTalon driveLeftC;

	private CANTalon driveRightA;
	private CANTalon driveRightB;
	private CANTalon driveRightC;

	private RobotDrive robotDrive;

	private double encOffsetValueRight = 0;
	private double encOffsetValueLeft = 0;

	//using the Nav board
	public PIDController turnController;

	static final double kP = 0.03; //TODO: adjust these
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;

	static final double kToleranceDegrees = 2.0f;

	boolean rotateToAngle = false;

	double rotateToAngleRate;

	public DriveSystem() {
		driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A_CAN_ID);
		driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B_CAN_ID);
		driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C_CAN_ID);
		driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A_CAN_ID);
		driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B_CAN_ID);
		driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C_CAN_ID);

		driveLeftA.enableBrakeMode(true);
		driveLeftB.enableBrakeMode(true);
		driveLeftC.enableBrakeMode(true);
		driveRightA.enableBrakeMode(true);
		driveRightB.enableBrakeMode(true);
		driveRightC.enableBrakeMode(true);

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
	
	public void takeJoystickInputs(Joystick joystk) {
		robotDrive.arcadeDrive(joystk);
	}

	public void driveByJoystick(double Y, double X) {
		SmartDashboard.putString("driveByJoystick?", Y + "," + X);
		robotDrive.arcadeDrive(Y,X);
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
	

	public void printEncoderValues() {
		getEncoderDistance();
	}

	public double getEncoderRight() {
		return -driveRightA.getEncPosition();
	}

	public double getEncoderLeft() {
		return driveLeftA.getEncPosition();
	}

	public double getEncoderDistance() {
		if (Robot.shifters.getGearSpeed()) {
			SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
			SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
			return (getEncoderRight() - encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
		}
		else {
			SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR);
			SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR);
			return (getEncoderRight() - encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR;
		}
	}

	public void resetEncoderDistance() {
		encOffsetValueRight = getEncoderRight();
		encOffsetValueLeft = getEncoderLeft();
		getEncoderDistance();
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}

}
