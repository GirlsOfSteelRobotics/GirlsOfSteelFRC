package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Command {

	private TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private TalonSRX rightTalon = Robot.chassis.getRightTalon();

	public Drive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		// V per sec; 12 = zero to full speed in 1 second
		/*leftTalon.setVoltageRampRate(24.0);
		rightTalon.setVoltageRampRate(24.0);*/
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.chassis.arcadeDrive();
		SmartDashboard.putNumber("Drive by Joystick Y: ", Robot.oi.getDrivingJoystickY());
		SmartDashboard.putNumber("Drive by Joystick X: ", Robot.oi.getDrivingJoystickX());
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