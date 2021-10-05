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

    private final double DRIVE_SPEED = 0.4, BABYDRIVE_SPEED = 0.3;
    private double goalLidar;
    private boolean chassis;

    // if boolean chassis is true, then normal drive
    // if chassis is false, then babyDrive
    public LidarDriveForward(double goalLidar, boolean chassis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.chassis = chassis;
        this.goalLidar = goalLidar;

        if (chassis) {
            requires(Robot.chassis);
        } else {
            requires(Robot.babyDrive);
        }
        requires(Robot.lidar);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("LidarDriveForward init, goal lidar: " + goalLidar + ", chassis bool: " + chassis);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (chassis) {
            Robot.chassis.setSpeed(DRIVE_SPEED);
        } else {
            Robot.babyDrive.babyDriveSetSpeed(BABYDRIVE_SPEED);
        }
        System.out.println("LidarDriveForward lidar distance: " + Robot.lidar.getDistance());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        double error = Robot.lidar.getDistance() - goalLidar;
        System.out.println("LidarDriveForward error: " + error);
        return error <= Robot.lidar.LIDAR_TOLERANCE;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        if (chassis) {
            Robot.chassis.stop();
        } else {
            Robot.babyDrive.babyDriveStop();
        }
        System.out.println("LidarDriveForward end");
    }
}
