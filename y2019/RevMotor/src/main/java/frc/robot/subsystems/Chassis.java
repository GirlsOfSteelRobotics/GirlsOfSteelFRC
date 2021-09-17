/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveByJoystick;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {
	private CANSparkMax masterLeft;
	private CANSparkMax followerLeftA;

	private CANSparkMax masterRight;
	private CANSparkMax followerRightA;

	private DifferentialDrive drive;

	public Chassis() {
		masterLeft = new CANSparkMax(RobotMap.DRIVE_LEFT_MASTER_TALON, MotorType.kBrushless);
		followerLeftA = new CANSparkMax(RobotMap.DRIVE_LEFT_FOLLOWER_A_TALON, MotorType.kBrushless);

		masterRight = new CANSparkMax(RobotMap.DRIVE_RIGHT_MASTER_TALON, MotorType.kBrushless);
		followerRightA = new CANSparkMax(RobotMap.DRIVE_RIGHT_FOLLOWER_A_TALON, MotorType.kBrushless);

		masterLeft.setIdleMode(IdleMode.kBrake);
		followerLeftA.setIdleMode(IdleMode.kBrake);

		masterRight.setIdleMode(IdleMode.kBrake);
		followerRightA.setIdleMode(IdleMode.kBrake);

		// inverted should be true for Laika
		masterLeft.setInverted(false);
		followerLeftA.setInverted(false);

		masterRight.setInverted(false);
		followerRightA.setInverted(false);

		followerLeftA.follow(masterLeft);
		followerRightA.follow(masterRight);

		drive = new DifferentialDrive(masterLeft, masterRight);
		drive.setSafetyEnabled(true);
		drive.setExpiration(0.1);
		drive.setMaxOutput(0.8);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveByJoystick());
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void driveByJoystick(double yDir, double xDir) {
		drive.arcadeDrive(yDir, xDir);
	}

	public void setSpeed(double speed) {
		drive.arcadeDrive(speed, 0);
	}

	public void stop() {
		drive.stopMotor();
	}
}
