package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LoadBall extends Command {

	public LoadBall() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.loader);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("LoadBall Initialized");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.loader.loadBall(-1.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.loader.stopLoader();
		System.out.println("LoadBall Finished");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
