package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSystem extends Subsystem {
	SpeedController driveLeft = RobotMap.driveSystemDriveLeft;
	SpeedController driveRight = RobotMap.driveSystemDriveRight;
	RobotDrive robotDrive2 = RobotMap.driveSystemRobotDrive2;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new DriveByJoystick());

		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void takeJoystickInputs(Joystick joystk) {
		robotDrive2.arcadeDrive(joystk);
	}

	public void stop() {
		robotDrive2.drive(/*speed*/0, /*curve*/0);
	}
}
