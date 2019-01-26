package frc.robot.commands;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReadLightSensor extends Command {

	public ReadLightSensor() {
		requires(Robot.motor);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//System.out.println("Printing value of Light Sensor " + Robot.motor.darkLightSensor());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.motor.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
