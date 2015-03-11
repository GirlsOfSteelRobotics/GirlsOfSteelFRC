package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftOneTote extends Command {

    public LiftOneTote() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lifter.zeroLiftTravel();
 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.lifter.up(0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lifter.getLiftTravel() >= 10000;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lifter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
