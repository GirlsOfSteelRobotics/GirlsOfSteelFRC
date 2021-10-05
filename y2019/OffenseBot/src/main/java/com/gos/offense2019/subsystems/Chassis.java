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
import com.gos.offense2019.RobotMap;
import com.gos.offense2019.commands.DriveByJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {
    private final WPI_TalonSRX m_masterLeft;
    private final WPI_TalonSRX m_followerLeftA;
    private final WPI_TalonSRX m_followerLeftB;

    private final WPI_TalonSRX m_masterRight;
    private final WPI_TalonSRX m_followerRightA;
    private final WPI_TalonSRX m_followerRightB;

    private final DifferentialDrive m_drive;

    public Chassis() {
        m_masterLeft = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER_TALON);
        m_followerLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FOLLOWER_A_TALON);
        m_followerLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FOLLOWER_B_TALON);

        m_masterRight = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER_TALON);
        m_followerRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FOLLOWER_A_TALON);
        m_followerRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FOLLOWER_B_TALON);

        m_masterLeft.setNeutralMode(NeutralMode.Brake);
        m_followerLeftA.setNeutralMode(NeutralMode.Brake);
        m_followerLeftB.setNeutralMode(NeutralMode.Brake);

        m_masterRight.setNeutralMode(NeutralMode.Brake);
        m_followerRightA.setNeutralMode(NeutralMode.Brake);
        m_followerRightB.setNeutralMode(NeutralMode.Brake);

        // inverted should be true for Laika
        m_masterLeft.setInverted(false);
        m_followerLeftA.setInverted(false);
        m_followerLeftB.setInverted(false);

        m_masterRight.setInverted(false);
        m_followerRightA.setInverted(false);
        m_followerRightB.setInverted(false);

        m_followerLeftA.follow(m_masterLeft, FollowerType.PercentOutput);
        m_followerLeftB.follow(m_masterLeft, FollowerType.PercentOutput);
        m_followerRightA.follow(m_masterRight, FollowerType.PercentOutput);
        m_followerRightB.follow(m_masterRight, FollowerType.PercentOutput);

        m_drive = new DifferentialDrive(m_masterLeft, m_masterRight);
        m_drive.setSafetyEnabled(true);
        m_drive.setExpiration(0.1);
        m_drive.setMaxOutput(0.8);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveByJoystick());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void driveByJoystick(double dirY, double dirX) {
        SmartDashboard.putString("driveByJoystick?", dirY + "," + dirX);
        double forward = dirY * Math.abs(dirY);
        m_drive.arcadeDrive(forward, dirX);
    }

    public void setSpeed(double speed) {
        m_drive.arcadeDrive(speed, 0);
    }

    public void stop() {
        m_drive.stopMotor();
    }
}
