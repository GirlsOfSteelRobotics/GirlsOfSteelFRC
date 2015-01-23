package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class WedgeIn extends Command {

    public WedgeIn() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wedges);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute(){
    	Robot.wedges.moveIn(); 
    }  

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	Robot.wedges.getLimit();
    	return false; //Add limit switch implementation
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.wedges.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
