package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Authors: Alexa, Corinne, Kyra, Sarah
 */
public class AutoTurnLeft extends Command {
	double initialDistance;
    public AutoTurnLeft() {
        requires(Robot.chassis); 
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialDistance = Robot.chassis.getFrontLeftEncoderDistance(); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.autoDriveForward(.5);  //driving from last container, over the platform, into the autozone
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.chassis.getFrontLeftEncoderDistance() == (initialDistance + 108))

    	return true; 
    	else 
    		return false; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
