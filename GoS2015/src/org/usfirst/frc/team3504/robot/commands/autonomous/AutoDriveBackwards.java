package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Authors Alexa, Kyra, Sarah
 */
public class AutoDriveBackwards extends Command {

	private double distance;

	public AutoDriveBackwards(double distance) {
		requires(Robot.chassis);
		this.distance = distance;
	}

	public AutoDriveBackwards() {

		requires(Robot.chassis);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Robot.chassis.r(Robot.chassis.getFrontLeftEncoderDistance());
		// come back to because encoder distance is not being printed on smart
		// dashboard
		// need to make this method
		Robot.chassis.resetDistance();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.chassis.autoDriveBackward(distance);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (Robot.chassis.getDistanceBackwards() > distance); // 50
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
