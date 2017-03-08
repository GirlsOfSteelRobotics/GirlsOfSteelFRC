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
        
		LiveWindow.addActuator("Chassis", "driveLeftA", driveLeftA);
		LiveWindow.addActuator("Chassis", "driveRightA", driveRightA);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand( new DriveByJoystick() );
	}
	
	public CANTalon getLeftTalon(){
		return driveLeftA; 
	}
	
	public CANTalon getRightTalon(){
		return driveRightA; 
	}
	
	public void stop(){
		driveLeftA.set(0);
		driveRightA.set(0);
	}
	
	public void setupEncoder(CANTalon talon){ //only call this on non-follower talons
		//Set Encoder Types
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);
		talon.reverseSensor(false);
	}
	
	public void setupFPID(CANTalon talon) { //values work with QuadEncoder for drive talons
		//PID Values
    	talon.setPosition(0);
		talon.setF(0);
    	talon.setP(0.64); //0.64 good
    	talon.setI(0.0); 
    	talon.setD(0.0);
	}
	
	
	
}
