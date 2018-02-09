/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {
	private WPI_TalonSRX driveLeftA;
	private WPI_TalonSRX driveLeftB;
	private WPI_TalonSRX driveLeftC;
	
	private WPI_TalonSRX driveRightA;
	private WPI_TalonSRX driveRightB;
	private WPI_TalonSRX driveRightC;
	
	private Shifters.Speed speed;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DifferentialDrive drive;
	
	public Chassis() {
		driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
		driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C);
		driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);
		driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C);
		
		setFollowerMode();
		
		driveRightA.setSensorPhase(true);
		driveLeftA.setSensorPhase(true);
		
		driveRightA.configForwardSoftLimitEnable(false, 0);
		driveLeftA.configForwardSoftLimitEnable(false, 0);
		
		driveRightA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		driveLeftA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	
		/*
		driveRightA.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 3, 10);
		driveLeftA.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 3, 10);
		driveRightA.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 3, 10);
		driveLeftA.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 3, 10);
		driveRightA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 3, 10);
		driveLeftA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 3, 10);
		*/
		
		driveLeftA.setSafetyEnabled(false);
		driveLeftB.setSafetyEnabled(false);
		driveLeftB.setSafetyEnabled(false);
		driveRightA.setSafetyEnabled(false);
		driveRightB.setSafetyEnabled(false);
		driveRightC.setSafetyEnabled(false);
    	
	    	driveLeftA.setName("Chassis", "driveLeftA");
	    	driveLeftB.setName("Chassis", "driveLeftB");
	    	driveLeftC.setName("Chassis", "driveLeftC");
	    	driveRightA.setName("Chassis", "driveRightA");
	    	driveRightB.setName("Chassis", "driveRightB");
	    	driveRightC.setName("Chassis", "driveRightC");
	    	LiveWindow.add(driveLeftA);
	    	LiveWindow.add(driveLeftB);
	    	LiveWindow.add(driveLeftC);
	    	LiveWindow.add(driveRightA);
	    	LiveWindow.add(driveRightB);
	    	LiveWindow.add(driveRightC);
		
		drive = new DifferentialDrive(driveLeftA, driveRightA);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveByJoystick());
		// Set the command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void setupFPID(WPI_TalonSRX talon) { //PID values from DriveByDistance
		if (speed == Shifters.Speed.kLow){
			talon.config_kF(0, 0, 0);
			talon.config_kP(0, 0.15, 0);
			talon.config_kI(0, 0, 0);
			talon.config_kD(0, 0, 0);
		}
		else if (speed == Shifters.Speed.kHigh){
			talon.config_kF(0, 0, 0);
			talon.config_kP(0, 0.02, 0);
			talon.config_kI(0, 0, 0);
			talon.config_kD(0, 0.04, 0);
		}	
	}
	
	public WPI_TalonSRX getLeftTalon() {
		return driveLeftA;
	}
	
	public WPI_TalonSRX getRightTalon() {
		return driveRightA;
	}
	
	public void stop() {
		drive.stopMotor();
	}
	
	public void setFollowerMode() {
		driveLeftB.follow(driveLeftA);
		driveLeftC.follow(driveLeftA);
		
		driveRightB.follow(driveRightA);
		driveRightC.follow(driveRightA);
	}
	
	public void setInverted(boolean inverted) {
		driveRightA.setInverted(inverted);
		driveRightB.setInverted(inverted);
		driveRightC.setInverted(inverted);
	}
		
}
