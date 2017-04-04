package org.usfirst.frc.team3504.robot.commands;


import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Agitate extends Command {
	private double agitateDelay = 0.1; //time in seconds of delay between moving the piston in and out 
	
	public Agitate() {
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.agitator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Agitate Initialzed");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.agitator.agitateForwards();
    	Timer.delay(agitateDelay);
    	Robot.agitator.agitateBackwards();
    	Timer.delay(agitateDelay);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Agitate Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
