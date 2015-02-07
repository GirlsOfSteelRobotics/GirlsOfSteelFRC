package org.usfirst.frc.team3504.robot.commands.autonomous.plow;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.commands.ReleaseTote;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Author: Alexa, Corinne, Kyra, Sarah
 */
public class AutoDriveForward extends Command {

    public AutoDriveForward() {
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
    	Robot.chassis.autoDriveForward(.5);// what is the speed, .5 is guestimate, speed is between 0 and 1
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.chassis.getFrontLeftEncoderDistance() == 1) /**Tote/Container Distances:
																	Tote+Container(in): 26.9+18=44.9
																	Tote(in): 26.9
																	Distance Between w/o Container(in): 18+35.75=53.75 */
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
