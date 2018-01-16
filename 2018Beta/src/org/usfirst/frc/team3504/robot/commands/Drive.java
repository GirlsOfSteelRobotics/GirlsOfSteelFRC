package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.OI.DriveStyle;
import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Command {

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

	public Drive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Change mode to Percent Vbus
		// V per sec; 12 = zero to full speed in 1 second
		leftTalon.configVoltageCompSaturation(24.0, 0);
		rightTalon.configVoltageCompSaturation(24.0, 0);
		//leftTalon.setVoltageRampRate(24.0);  IS THIS THE SAME AS ABOVE???
		//rightTalon.setVoltageRampRate(24.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (OI.driveStyle == DriveStyle.oneStickArcade 
				|| OI.driveStyle == DriveStyle.gamePadArcade) {
			Robot.chassis.arcadeDrive();
			SmartDashboard.putNumber("Drive by Joystick Y: ", Robot.oi.getDrivingJoystickY());
			SmartDashboard.putNumber("Drive by Joystick X: ", Robot.oi.getDrivingJoystickX());
		}
		else {
			Robot.chassis.tankDrive();
			SmartDashboard.putNumber("Drive by Joystick Left:", Robot.oi.getDrivingJoystickLeft());
			SmartDashboard.putNumber("drive by Joystick Right:", Robot.oi.getDrivingJoystickRight());
		} 
	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	public void stop() {
		Robot.chassis.stop();
	}

	// Called once after isFinished returns true
	protected void end() {
		stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
