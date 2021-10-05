package com.gos.power_up.commands;

import com.gos.power_up.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorHold extends Command {

    public CollectorHold() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.collector.runCollector();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
