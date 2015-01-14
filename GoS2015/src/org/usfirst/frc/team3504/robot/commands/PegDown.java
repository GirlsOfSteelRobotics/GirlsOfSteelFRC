package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@annika
 *@ziya
 */
public class PegDown extends Command {

    public PegDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	requires(Robot.pegs);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.pegs.pegDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	return Robot.pegs.getLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.pegs.pegStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
