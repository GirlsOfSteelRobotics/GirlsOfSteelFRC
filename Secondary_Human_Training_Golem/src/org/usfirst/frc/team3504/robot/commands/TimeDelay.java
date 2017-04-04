package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimeDelay extends Command {

	private double seconds;
	private Timer tim;

	public TimeDelay(double seconds) {
		// Use requires() here to declare subsystem dependencies
		tim = new Timer();
		this.seconds = seconds;

	}

	// Called just before this Command runs the first times
	protected void initialize() {
		tim.start();
		System.out.println("TimeDelay Initialzed with " + seconds + " seconds as parameter");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (tim.get() > seconds);
	}

	// Called once after isFinished returns true
	protected void end() {
		tim.stop();
		System.out.println("TimeDelay Finished");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
