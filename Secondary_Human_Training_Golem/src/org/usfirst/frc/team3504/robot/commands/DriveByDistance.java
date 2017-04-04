package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByDistance extends Command {

	private double rotations;

	private CANTalon leftTalon = Robot.chassis.getLeftTalon();
	private CANTalon rightTalon = Robot.chassis.getRightTalon();

	private double leftInitial;
	private double rightInitial;

	public DriveByDistance(double inches) {
		rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);

		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.chassis.setPositionMode();

		// Robot.chassis.setupFPID(leftTalon);
		// Robot.chassis.setupFPID(rightTalon);
		leftTalon.setP(0.17);
		rightTalon.setP(0.17);

		leftTalon.setI(0.0);
		rightTalon.setI(0.0);

		leftTalon.setD(0.02);
		rightTalon.setD(0.02);

		leftTalon.setF(0.0);
		rightTalon.setF(0.0);

		// leftTalon.setPosition(0.0);
		// rightTalon.setPosition(0.0);

		System.out.println("Drive by Distance Started " + rotations);

		leftInitial = -leftTalon.getPosition();
		rightInitial = rightTalon.getPosition();

		leftTalon.set(-(rotations + leftInitial));
		rightTalon.set(rotations + rightInitial);

		System.out.println("LeftInitial: " + leftInitial + " RightInitial: " + rightInitial);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		leftTalon.set(-(rotations + leftInitial));
		rightTalon.set(rotations + rightInitial);

		SmartDashboard.putNumber("Drive Talon Left Goal", -rotations);
		SmartDashboard.putNumber("Drive Talon Left Position", leftTalon.getPosition());
		SmartDashboard.putNumber("Drive Talon Left Error", leftTalon.getError());

		System.out.println("Drive Talon Left Goal " + (-(rotations + leftInitial)));
		System.out.println("Drive Talon Left Position " + leftTalon.getPosition());
		System.out.println("Drive Talon Left Error " + leftTalon.getError());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (rotations > 0) {
			return ((rightTalon.getPosition() > rotations + rightInitial)
					&& (-leftTalon.getPosition() > rotations + leftInitial));
		} else if (rotations < 0) {
			return ((rightTalon.getPosition() < rotations + rightInitial)
					&& (-leftTalon.getPosition() < rotations + leftInitial));
		} else
			return true;
	}

	// Called once after isFinished returns true
	protected void end() {

		System.out.println("DriveByDistance Finished");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
