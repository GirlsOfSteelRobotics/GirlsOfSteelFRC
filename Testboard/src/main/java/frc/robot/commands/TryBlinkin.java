package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.subsystems.Blinkin;
import edu.wpi.first.wpilibj.command.Command;

public class TryBlinkin extends Command {

	public TryBlinkin() {
		requires(Robot.blinkin);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
       Robot.blinkin.setLightPattern(Blinkin.LightPattern.AUTO_DEFAULT);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.blinkin.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
