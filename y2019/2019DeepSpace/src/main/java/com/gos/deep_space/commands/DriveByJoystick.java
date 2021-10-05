package com.gos.deep_space.commands;

import com.gos.deep_space.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveByJoystick extends Command {

    public DriveByJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // 4 is the axis number right x on the gamepad
        Robot.m_chassis.driveByJoystick(Robot.m_oi.getLeftUpAndDown(), Robot.m_oi.getRightSideToSide());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
