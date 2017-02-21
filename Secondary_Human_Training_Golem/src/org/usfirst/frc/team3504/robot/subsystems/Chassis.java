package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.CANTalon;

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

	private double encOffsetValueRight = 0;
	private double encOffsetValueLeft = 0;
	
	

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

		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false); 
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
		
		setupEncoder(driveLeftA);
		setupEncoder(driveRightA);
        
		LiveWindow.addActuator("Chassis", "driveLeftA", driveLeftA);
		LiveWindow.addActuator("Chassis", "driveRightA", driveRightA);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand( new DriveByJoystick() );
	}

	public void driveByJoystick(double Y, double X) {
		SmartDashboard.putString("driveByJoystick?", Y + "," + X);
		robotDrive.arcadeDrive(Y,X);
	}

	public void drive(double moveValue, double rotateValue){
		robotDrive.arcadeDrive(moveValue, rotateValue);
	}

	public void driveSpeed(double speed) {
		robotDrive.drive(speed, 0);
	}

	public void stop() {
		robotDrive.drive(0, 0);
	}
	
	public CANTalon getLeftTalon(){
		return driveLeftA; 
	}
	
	public CANTalon getRightTalon(){
		return driveRightA; 
	}
	
	public double getEncoderRight() {
		//because the motors are backwards relative to left 
		return driveRightA.getEncPosition();
	}

	public double getEncoderLeft() {
		return -driveLeftA.getEncPosition();
	}

	public double getEncoderDistance() {
		return -2.5; 
	}

	public void resetEncoderDistance() {
		encOffsetValueRight = getEncoderRight(); 
		encOffsetValueLeft = getEncoderLeft();
		getEncoderDistance();
	}
	
	public void setupEncoder(CANTalon talon){ //only call this on non-follower talons
		//Set Encoder Types
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.reverseSensor(false);
    	
    	//PID Values
    	talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);
    	talon.setPosition(0);
    	talon.setF(0);
    	talon.setP(0.64); //0.64 good
    	talon.setI(0.0); 
    	talon.setD(0.0);
	}
	
}
