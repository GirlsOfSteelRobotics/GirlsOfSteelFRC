package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Capture a set of images from the default USB camera at a range of exposures,
 * saving the results to a set of files in the home directory of the RoboRIO
 */
public class Capture extends Command {
	// Path to a directory for the stored images. Must end with a slash.
	private static final String PATH = "/home/lvuser/";
	// File extension used for the stored images. OpenCV decides the format to
	// use based on the conventional meaning of the extension (jpg, png, etc.)
	private static final String EXTENSION = "jpg";

	public Capture() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.cameraSubsystem);
	}

	// Called just before this Command runs the first time
	// All the work is done in this routine. It takes a relatively long time to
	// run, so DON'T put this command into a normal robot program because the
	// timing will be off.
	@Override
	protected void initialize() {
		System.out.println("Camera command starting");
		// Zero the progress indicator on the dashboard
		Robot.oi.updateProgress(0);
		// Read the current location selections from the dashboard
		String side = Robot.oi.getSide();
		String lift = Robot.oi.getLift();
		String dist = Robot.oi.getDist();
		// Step through all possible "percent exposure" values for a Microsoft
		// Lifecam
		for (int exp = 0; exp <= 100; exp += 8) {
			String filename = String.format("%sexp%02d_%s_%s_%s.%s", 
					PATH, exp, side, lift, dist, EXTENSION);
			Robot.cameraSubsystem.setExposure(exp);
			Robot.cameraSubsystem.saveImage(filename);
			// This will range from 4 to 100, a better representation of status
			// than 0 to 96
			Robot.oi.updateProgress(exp + 4);
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
		Robot.oi.updateProgress(0);
		System.out.println("Camera command ended");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
