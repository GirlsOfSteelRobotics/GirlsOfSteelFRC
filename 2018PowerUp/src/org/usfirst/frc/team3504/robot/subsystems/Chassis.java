/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {
	private WPI_TalonSRX driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
	private WPI_TalonSRX driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
	private WPI_TalonSRX driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C);
	
	private WPI_TalonSRX driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
	private WPI_TalonSRX driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);
	private WPI_TalonSRX driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C);
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DifferentialDrive drive;
	
	public Chassis() {
		setFollowerMode();
		driveLeftA.setInverted(true);
		//driveRightA.setInverted(true);
		driveRightA.setSensorPhase(true);
		driveLeftA.setSensorPhase(true);
		driveLeftA.setSafetyEnabled(false);
		driveRightA.setSafetyEnabled(false);
		
		drive = new DifferentialDrive(driveLeftA, driveRightA);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
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
	
}
