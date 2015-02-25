package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Authors: Alexa, Corinne, Sarah
 * found distance between first container and tote aka diameter of container 
 * and make robot go that distance
 * 
 */
public class AutoFirstPickup extends Command {

	private double distance; 
	
    public AutoFirstPickup(double distance) {
       requires(Robot.chassis);
       this.distance = distance; 
    }
	
    public AutoFirstPickup() {
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.autoDriveRight(distance); //22.25
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Robot.chassis.getDistanceLeft() > distance);
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
