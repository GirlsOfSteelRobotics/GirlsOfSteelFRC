package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.OI.DriveStyle;
import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByJoystick extends Command {

	public DriveByJoystick() {
		requires(Robot.chassis);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.oi.setDriveStyle();
		
		System.out.println("Squared Units: " + Robot.oi.isSquaredOrCurvature());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	
		double throttleFactor = Robot.oi.isThrottle()? .25 : 1;
		Robot.chassis.drive.curvatureDrive(Robot.oi.getAmazonLeftUpAndDown()*throttleFactor, 
				Robot.oi.getAmazonRightSideToSide()*throttleFactor, true);
	
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
