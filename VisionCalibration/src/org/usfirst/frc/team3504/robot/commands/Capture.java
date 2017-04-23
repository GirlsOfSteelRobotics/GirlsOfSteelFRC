package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3504.robot.Robot;

/**
 *
 */
public class Capture extends Command {
	public Capture() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.cameraSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Camera command starting");
		String side = Robot.oi.getSide();
		String lift = Robot.oi.getLift();
		String dist = Robot.oi.getDist();
		for (int exp = 0; exp <= 100; exp += 8) {
			String filename = "exp" + exp + "_" + 
					side + "_" + lift + "_" + dist + ".jpg";
			System.out.println("Saving: " + filename);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Camera command ended");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
