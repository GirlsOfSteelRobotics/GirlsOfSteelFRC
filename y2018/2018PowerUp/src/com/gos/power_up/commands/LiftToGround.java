package com.gos.power_up.commands;

import com.gos.power_up.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToGround extends Command {

	
    public LiftToGround() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		Robot.lift.setLiftToGround();
    		System.out.println("LiftToGround");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.lift.setSelectedSensorPosition(0,0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}