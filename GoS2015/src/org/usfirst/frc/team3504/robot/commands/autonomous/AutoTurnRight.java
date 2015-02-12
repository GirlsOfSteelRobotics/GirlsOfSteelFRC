package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Authors: Alexa, Corinne, Sarah
 */
public class AutoTurnRight extends Command {
	double initialDistance;
    public AutoTurnRight() {
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
    	Robot.chassis.autoDriveForward(-.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.chassis.getFrontLeftEncoderDistance() == 108)
    		//TODO speed and distance
    		return false;
    	else 
    		return true; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop(); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end (); 
    }
}
