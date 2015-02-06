package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Authors Alexa, Kyra, Sarah
 */
public class AutoDriveBackwards extends Command {
	double initialDistance; 
	
    public AutoDriveBackwards() {
    //set variable because no other way to reset encoders 
    	
    	requires(Robot.chassis);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialDistance = Robot.chassis.getFrontLeftEncoderDistance();
    	//come back to because encoder distance is not being printed on smart dashboard
    	//need to make this method
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.autoDriveSideways(-.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.chassis.getFrontLeftEncoderDistance() == (initialDistance + 36))
    		//check to make sure 36 is correct distance
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
    	end ();
    }
}
