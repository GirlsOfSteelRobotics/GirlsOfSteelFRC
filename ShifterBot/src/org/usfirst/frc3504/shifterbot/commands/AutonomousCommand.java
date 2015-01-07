package org.usfirst.frc3504.shifterbot.commands;

import org.usfirst.frc3504.shifterbot.Robot;

import edu.wpi.first.wpilibj.command.Command;
//import org.usfirst.frc3504.shifterbot.Robot;

/**
 *
 */
public class  AutonomousCommand extends Command {

	public AutonomousCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		requires(Robot.accessoryMotors);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveSystem.resetEncoders();
		setTimeout(1.5);
		Robot.accessoryMotors.driveAccessoryLeft(true);
		Robot.accessoryMotors.driveAccessoryRight(false);	
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveSystem.forward();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.accessoryMotors.stop();
		Robot.driveSystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
