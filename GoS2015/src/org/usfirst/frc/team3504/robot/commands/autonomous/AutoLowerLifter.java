package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoLowerLifter extends Command {

    public AutoLowerLifter() {
    	requires(Robot.forklift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(.4);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.forklift.up(1);
    	//TODO speed
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.forklift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}