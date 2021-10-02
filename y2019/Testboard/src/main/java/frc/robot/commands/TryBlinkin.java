package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Blinkin;
import edu.wpi.first.wpilibj.command.Command;

public class TryBlinkin extends Command {

	public TryBlinkin() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.blinkin);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("init TryBlinking(HATCH_RELEASE) all green");
		Robot.blinkin.setLightPattern(Blinkin.LightPattern.HATCH_RELEASE);
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
}
