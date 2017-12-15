package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shoot extends Command {
	private int shooterSpeed;

	public Shoot(int speed) {
		requires(Robot.shooter);
		shooterSpeed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooter.setShooterSpeed(shooterSpeed);
		System.out.println("Shoot Initialzed with " + shooterSpeed + " as speed");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("High Shooter Speed", Robot.shooter.getHighShooterSpeed());
		SmartDashboard.putNumber("Low Shooter Speed", Robot.shooter.getLowShooterSpeed());
		Robot.shooter.runHighShooterMotor();
		Robot.shooter.runLowShooterMotor();
		Robot.shooter.runFeeder();
		Robot.shooter.runConveyor();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooter.stopShooterMotors();
		System.out.println("Shoot Finished");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}