package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
//import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;


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
		if (highgear) {
			shifterLeft.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting left side into high gear (fwd)");
		} else {
			shifterLeft.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting left side into low gear (rev)");
		}
	}
	
	public void shiftRight(boolean highgear) {
		if (highgear) {
			shifterRight.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting right side into high gear (fwd)");
		} else {
			shifterRight.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting right side into low gear (rev)");
		}
	}
}
