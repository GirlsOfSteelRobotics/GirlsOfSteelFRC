/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.LidarLitePWM;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
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
	
	public LidarLitePWM lidar = new LidarLitePWM(new DigitalInput (RobotMap.LIDAR));
	
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
		/*
		driveLeftA.setInverted(true);
		driveLeftB.setInverted(true);
		driveLeftC.setInverted(true);
		driveRightA.setInverted(false);
		driveRightB.setInverted(false);
		driveRightC.setInverted(false); */
		
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
		
		System.out.println("Chassis: leftA " + driveLeftA.getInverted());
		System.out.println("Chassis: leftB " + driveLeftB.getInverted());
		System.out.println("Chassis: leftC " + driveLeftC.getInverted());
    	System.out.println("Chassis: rightA " + driveRightA.getInverted());
    	System.out.println("Chassis: rightB " + driveRightB.getInverted());
    	System.out.println("Chassis: rightC " + driveRightC.getInverted());
    	
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
	
	public void setupFPID(WPI_TalonSRX talon) {
		//talon.setPosition (0); TODO figure out new syntax
		talon.config_kF(0, 0, 0);
		talon.config_kP(0, 0, 0);
		talon.config_kI(0, 0, 0);
		talon.config_kD(0, 0, 0);	
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
