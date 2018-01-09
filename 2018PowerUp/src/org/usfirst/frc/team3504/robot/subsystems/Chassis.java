/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
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
	
	private SpeedControllerGroup leftGroup = new SpeedControllerGroup(driveLeftA, driveLeftB, driveLeftC);
	private SpeedControllerGroup rightGroup = new SpeedControllerGroup(driveRightA, driveRightB, driveRightC);
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Chassis() {
		DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
	}
	public void initDefaultCommand() {
		// Set the command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
