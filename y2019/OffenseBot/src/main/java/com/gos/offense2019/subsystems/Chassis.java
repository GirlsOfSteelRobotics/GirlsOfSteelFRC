/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.subsystems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.offense2019.RobotMap;
import com.gos.offense2019.commands.DriveByJoystick;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {
    private WPI_TalonSRX masterLeft;
    private WPI_TalonSRX followerLeftA;
    private WPI_TalonSRX followerLeftB;

    private WPI_TalonSRX masterRight;
    private WPI_TalonSRX followerRightA;
    private WPI_TalonSRX followerRightB;

    private DifferentialDrive drive;

    public Chassis() {
        masterLeft = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER_TALON);
        followerLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FOLLOWER_A_TALON);
        followerLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FOLLOWER_B_TALON);

        masterRight = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER_TALON);
        followerRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FOLLOWER_A_TALON);
        followerRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FOLLOWER_B_TALON);

        masterLeft.setNeutralMode(NeutralMode.Brake);
        followerLeftA.setNeutralMode(NeutralMode.Brake);
        followerLeftB.setNeutralMode(NeutralMode.Brake);

        masterRight.setNeutralMode(NeutralMode.Brake);
        followerRightA.setNeutralMode(NeutralMode.Brake);
        followerRightB.setNeutralMode(NeutralMode.Brake);

        // inverted should be true for Laika
        masterLeft.setInverted(false);
        followerLeftA.setInverted(false);
        followerLeftB.setInverted(false);

        masterRight.setInverted(false);
        followerRightA.setInverted(false);
        followerRightB.setInverted(false);

        followerLeftA.follow(masterLeft, FollowerType.PercentOutput);
        followerLeftB.follow(masterLeft, FollowerType.PercentOutput);
        followerRightA.follow(masterRight, FollowerType.PercentOutput);
        followerRightB.follow(masterRight, FollowerType.PercentOutput);

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
        SmartDashboard.putString("driveByJoystick?", yDir + "," + xDir);
        double forward = yDir * Math.abs(yDir);
        drive.arcadeDrive(forward, xDir);
    }

    public void setSpeed(double speed) {
        drive.arcadeDrive(speed, 0);
    }

    public void stop() {
        drive.stopMotor();
    }
}
