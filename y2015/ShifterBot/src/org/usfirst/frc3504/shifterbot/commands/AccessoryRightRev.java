package org.usfirst.frc3504.shifterbot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3504.shifterbot.Robot;

/**
 *
 */
public class  AccessoryRightRev extends Command {

	public AccessoryRightRev() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		requires(Robot.accessoryMotors);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.accessoryMotors.driveAccessoryRight(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.accessoryMotors.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
