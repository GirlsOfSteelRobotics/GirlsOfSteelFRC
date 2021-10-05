/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveByJoystick extends Command {
    public DriveByJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("DriveByJoystick init");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // 4 is the axis number right x on the gamepad
        Robot.chassis.driveByJoystick(Robot.oi.getLeftUpAndDown(), Robot.oi.getRightSideToSide());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("DriveByJoystick end");
    }
}
