package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
//import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


/**
 *
 */
public class Shifters extends Subsystem {
	DoubleSolenoid shifterLeft = RobotMap.shiftersShifterLeft;
	DoubleSolenoid shifterRight = RobotMap.shiftersShifterRight;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public void shiftLeft(boolean highgear) {
		if (highgear)
			shifterLeft.set(Value.kForward);
		else
			shifterLeft.set(Value.kReverse);
	}
	
	public void shiftRight(boolean highgear) {
		if (highgear)
			shifterRight.set(Value.kForward);
		else
			shifterRight.set(Value.kReverse);
	}
}
