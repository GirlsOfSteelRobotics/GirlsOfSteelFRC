package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSystem extends Subsystem {
	SpeedController driveLeft = RobotMap.driveSystemDriveLeft0;
	SpeedController driveRight = RobotMap.driveSystemDriveRight0;
	RobotDrive robotDrive2 = RobotMap.driveSystemRobotDrive2;
	// Encoder driveLeftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER_A,
	// RobotMap.DRIVE_LEFT_ENCODER_B, true, CounterBase.EncodingType.k2X);
	// Encoder driveRightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER_A,
	// RobotMap.DRIVE_RIGHT_ENCODER_B, true, CounterBase.EncodingType.k2X);
	double offsetValue = 0;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveByJoystick());
	}

	public void takeJoystickInputs(Joystick joystk) {
		robotDrive2.arcadeDrive(joystk);
	}

	public void forward() {
		robotDrive2.drive(1.0, 0);
	}

	public void stop() {
		robotDrive2.drive(/* speed */0, /* curve */0);
	}

	public double getEncoderRight() {
		return RobotMap.driveSystemDriveRight0.getEncPosition();
	}

	public double getEncoderLeft() {
		return RobotMap.driveSystemDriveLeft0.getEncPosition();
	}

	public double getEncoderDistance() {
		return (getEncoderLeft() - offsetValue) * RobotMap.DISTANCE_PER_PULSE;
	}

	public void resetDistance() {
		offsetValue = getEncoderLeft();
	}
}
