package org.usfirst.frc.team3504.robot.commands.tests;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author arushibandi
 */
public class TestPhotoSensor extends Command {

	public TestPhotoSensor() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.photosensor);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("photosensor light", Robot.photosensor.getLightValue());
		SmartDashboard.putNumber("photosensor dark", Robot.photosensor.getDarkValue());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
