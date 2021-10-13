package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
//import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class AccessoryMotors extends Subsystem {
	SpeedController accessoryLeft = RobotMap.accessoryMotorsAccessoryLeft;
	SpeedController accessoryRight = RobotMap.accessoryMotorsAccessoryRight;


	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void driveAccessoryLeft(boolean fwd) {
		if (fwd)
			accessoryLeft.set(1.0);
		else
			accessoryLeft.set(-1.0);
	}

	public void driveAccessoryRight(boolean fwd) {
		if (fwd)
			accessoryRight.set(1.0);
		else
			accessoryRight.set(-1.0);
	}

	public void stop() {
		accessoryLeft.set(0.0);
		accessoryRight.set(0.0);
	}
}
