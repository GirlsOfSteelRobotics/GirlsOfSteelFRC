package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDrive extends Command {

	public double distance; 
	
    public AutoDrive() {
        requires(Robot.chassis); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetDistance(); //need to create resetDistance method 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.driveForward(); //need to created driveForward method 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.chassis.driveForward() > distance);
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
