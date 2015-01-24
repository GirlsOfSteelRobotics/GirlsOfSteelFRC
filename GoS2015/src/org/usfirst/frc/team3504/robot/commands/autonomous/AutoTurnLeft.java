package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Authors: Alexa, Corinne, Kyra, Sarah
 */
public class AutoTurnLeft extends Command {

    public AutoTurnLeft() {
        requires(Robot.chassis); 
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.autoDriveForward(.5);  //driving from last container, over the platform, into the autozone
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if  (Robot.chassis.getFrontLeftEncoderDistance() == 108) //end once the robot is in the autozone
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
