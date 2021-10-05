/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LidarDriveForward extends Command {

    private static final double DRIVE_SPEED = 0.4;
    private static final double BABYDRIVE_SPEED = 0.3;
    private final double m_goalLidar;
    private final boolean m_chassis;

    // if boolean chassis is true, then normal drive
    // if chassis is false, then babyDrive
    public LidarDriveForward(double goalLidar, boolean chassis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.m_chassis = chassis;
        this.m_goalLidar = goalLidar;

        if (chassis) {
            requires(Robot.m_chassis);
        } else {
            requires(Robot.m_babyDrive);
        }
        requires(Robot.m_lidar);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("LidarDriveForward init, goal lidar: " + m_goalLidar + ", chassis bool: " + m_chassis);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (m_chassis) {
            Robot.m_chassis.setSpeed(DRIVE_SPEED);
        } else {
            Robot.m_babyDrive.babyDriveSetSpeed(BABYDRIVE_SPEED);
        }
        System.out.println("LidarDriveForward lidar distance: " + Robot.m_lidar.getDistance());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        double error = Robot.m_lidar.getDistance() - m_goalLidar;
        System.out.println("LidarDriveForward error: " + error);
        return error <= Robot.m_lidar.LIDAR_TOLERANCE;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        if (m_chassis) {
            Robot.m_chassis.stop();
        } else {
            Robot.m_babyDrive.babyDriveStop();
        }
        System.out.println("LidarDriveForward end");
    }
}
