package frc.robot.commands;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveByJoystick extends Command {

	public DriveByJoystick() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// 4 is the axis number right x on the gamepad
		Robot.chassis.driveByJoystick(Robot.robotContainer.getLeftUpAndDown(), Robot.robotContainter.getRightSideToSide());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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