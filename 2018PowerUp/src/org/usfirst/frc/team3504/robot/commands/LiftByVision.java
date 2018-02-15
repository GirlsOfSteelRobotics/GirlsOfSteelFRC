package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftByVision extends Command {

    public LiftByVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.blobs.distanceBetweenBlobs() == -1)
    	{
    		System.out.print("LiftByVision initialize: line not in sight!!");
    		end();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.blobs.distanceBetweenBlobs() != -1)
    	{
    		Robot.lift.incrementLift();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.blobs.distanceBetweenBlobs() == -1);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
