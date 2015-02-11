package org.usfirst.frc.team3504.robot.commands.fingers;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@annika
 *@ziya
 */
public class FingerDown extends Command {

    public FingerDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3.5);
    	requires(Robot.fingers);
    	//probs change time
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.fingers.fingerDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.fingers.fingerStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
