package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveByArcade extends Command {
	public DriveByArcade() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double y = Robot.oi.getArcadeDrive().getY(); 
    	double x = Robot.oi.getArcadeDrive().getX() * (-1);
    	if (y > 0) x = x * (-1); //This is for turning when going backwards
    	
    	System.out.println("y: " + y);
    	System.out.println("x: " + x);
    	
    	Robot.chassis.driveByArcade(y, x);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
