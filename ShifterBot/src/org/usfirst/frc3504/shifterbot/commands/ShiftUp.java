package org.usfirst.frc3504.shifterbot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3504.shifterbot.Robot;

/**
 *
 */
public class  ShiftUp extends Command {

	public ShiftUp() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		requires(Robot.shifters);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shifters.shiftLeft(true);
		Robot.shifters.shiftRight(true);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// The solenoid setting commands should complete immediately
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
