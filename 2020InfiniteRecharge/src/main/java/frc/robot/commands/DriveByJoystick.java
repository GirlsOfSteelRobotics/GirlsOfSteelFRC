package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

public class DriveByJoystick extends CommandBase {

  Chassis chassis;

	public DriveByJoystick(Chassis chassis) {
    chassis=chassis;
		// Use requires() here to declare subsystem dependencies
		super.addRequirements(chassis);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
		// 4 is the axis number right x on the gamepad
		chassis.driveByJoystick(Robot.robotContainter.getLeftUpAndDown(), Robot.robotContainter.getRightSideToSide());
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		chassis.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}