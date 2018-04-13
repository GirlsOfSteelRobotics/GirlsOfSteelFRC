package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnByMotionMagic extends Command {

	private double targetHeading; // in degrees
	private boolean resetPigeon;

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

	private final static double TURNING_FINISH_THRESHOLD = 7.0; // TODO tune (in degrees)

	public TurnByMotionMagic(double degrees) {
		targetHeading = degrees;
		resetPigeon = true;
		requires(Robot.chassis);
		// System.out.println("TurnByMotionMagic: constructed");
	}

	public TurnByMotionMagic(double degrees, boolean reset) {
		targetHeading = degrees;
		resetPigeon = reset;
		requires(Robot.chassis);
		// System.out.println("TurnByMotionMagic: constructed");
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.chassis.setInverted(false);

		Robot.chassis.configForTurnByMotionMagic();
		// System.out.println("TurnByMotionMagic: configured for motion magic");

		if (resetPigeon)
			Robot.chassis.zeroSensors();

		System.out.println("TurnByMotionMagic: heading: " + targetHeading);

		rightTalon.set(ControlMode.MotionMagic, -10 * targetHeading);
		leftTalon.follow(rightTalon);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
		double currentHeading = Robot.chassis.getYaw();
		double error = Math.abs(targetHeading - currentHeading);
		// System.out.println("DriveByMotionMagic: turning error = " + error);
		if (error < TURNING_FINISH_THRESHOLD) {
			System.out.println("DriveByMotionMagic: turning degrees reached");
			return true;
		} else
			return false; 

	}

	// Called once after isFinished returns true
	protected void end() {

		double currentHeading = Robot.chassis.getYaw();
		double degreesError = targetHeading - currentHeading;

		System.out.println("TurnByMotionMagic: ended. Error = " + degreesError + " degrees");
		Robot.chassis.stop();
		Robot.chassis.setInverted(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("TurnByMotionMagic: interrupted");
		end();
	}
}
