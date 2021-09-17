package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StayClimbed extends Command {

	private double encPosition;

	public StayClimbed() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.climber.climbMotorA.changeControlMode(TalonControlMode.Position);

		Robot.climber.climbMotorB.changeControlMode(TalonControlMode.Follower);
		Robot.climber.climbMotorB.set(Robot.climber.climbMotorA.getDeviceID());

		// Robot.climber.climbMotorA.setPosition(0);
		encPosition = Robot.climber.climbMotorA.getPosition();
		System.out.println("Climber Encoder Position: " + encPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.climber.climbMotorA.set(encPosition);
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
		end();
	}
}