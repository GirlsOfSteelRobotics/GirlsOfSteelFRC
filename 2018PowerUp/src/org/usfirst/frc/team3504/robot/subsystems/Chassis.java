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
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
		
		driveLeftA.setNeutralMode(NeutralMode.Brake);
		driveLeftB.setNeutralMode(NeutralMode.Brake);
		driveLeftC.setNeutralMode(NeutralMode.Brake);
		driveRightA.setNeutralMode(NeutralMode.Brake);
		driveRightB.setNeutralMode(NeutralMode.Brake);
		driveRightC.setNeutralMode(NeutralMode.Brake);
		
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
	    	
	    	setupFPID(driveLeftA);
	    	setupFPID(driveRightA);
	    	
	    	driveLeftA.configClosedloopRamp(0, 10);
	    	driveRightA.configClosedloopRamp(0, 10);
	    	// clyde values
	    	//driveLeftA.configOpenloopRamp(0.25, 10);
	    	//driveRightA.configOpenloopRamp(0.25, 10);
	    	
	    	//blinky values
	    	driveLeftA.configOpenloopRamp(0.5, 10);
	    	driveRightA.configOpenloopRamp(0.5, 10);
	    	
	    	driveLeftA.configMotionCruiseVelocity(2800, 10);
	    	driveRightA.configMotionCruiseVelocity(2800, 10);
	    	
	    	driveLeftA.configMotionAcceleration(6000, 10);
	    	driveRightA.configMotionAcceleration(6000, 10);
	    	
	    	driveLeftA.configPeakOutputForward(0.9, 10);
	    	driveLeftA.configPeakOutputReverse(-0.9, 10);
	    	
	    	driveRightA.configPeakOutputForward(0.9, 10);
	    	driveRightA.configPeakOutputReverse(-0.9, 10);
		
		drive = new DifferentialDrive(driveLeftA, driveRightA);
		drive.setSafetyEnabled(false);
		
		drive.setDeadband(0.02);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveByJoystick());
		// Set the command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void setupFPID(WPI_TalonSRX talon) { //PID values from DriveByDistance
		talon.config_kF(0, 0, 10);
		talon.config_kP(0, 0.025, 10); //increase until overshoot/oscillation
		talon.config_kI(0, 0, 10);
		talon.config_kD(0, 6.5, 10); //D is around 1/10 to 1/100 of P value

		
		talon.config_kF(1, 0.3008, 10);
		talon.config_kP(1, 0, 10); //increase until overshoot/oscillation
		talon.config_kI(1, 0, 10);
		talon.config_kD(1, 0, 10);
//		if (speed == Shifters.Speed.kLow){
//			talon.config_kF(0, 0, 10);
//			talon.config_kP(0, 0.3, 10);
//			talon.config_kI(0, 0, 10);
//			talon.config_kD(0, 0.001, 10);
//		}
//		else if (speed == Shifters.Speed.kHigh){
//			talon.config_kF(0, 0, 10);
//			talon.config_kP(0, 0.3, 10);
//			talon.config_kI(0, 0, 10);
//			talon.config_kD(0, 0.001, 10);
//		} else {
//			System.out.println("No bueno PID setting");
//		}
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
	
	public void setPositionPIDSlot()
	{
		driveLeftA.selectProfileSlot(0, 0);
		driveRightA.selectProfileSlot(0, 0);
	}
	
	public void setVelocityPIDSlot()
	{
		driveLeftA.selectProfileSlot(1, 0);
		driveRightA.selectProfileSlot(1, 0);
	}
	
		
}
