/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class BabyDriveBackwards extends Command {
    private static final double BABYDRIVE_SPEED = 0.4;

    public BabyDriveBackwards() {
        requires(Robot.babyDrive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("init BabyDriveBackwards");

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.babyDrive.babyDriveSetSpeed(BABYDRIVE_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.babyDrive.babyDriveStop();
        System.out.println("end BabyDriveBackwards");
    }

}
