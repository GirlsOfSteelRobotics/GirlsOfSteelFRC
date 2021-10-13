package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifters extends Subsystem{

	DoubleSolenoid shifterLeft = RobotMap.shiftersShifterLeft;
	DoubleSolenoid shifterRight = RobotMap.shiftersShifterRight;
	
	@Override
	public void initDefaultCommand() {
		
	}
	public void shiftLeft(boolean highgear) {
		if (highgear) {
			shifterLeft.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting left side into high gear (fwd)");
		}
		else {
			shifterLeft.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting left side into low gear (rev)");
		}
	}
	public void shiftRight(boolean highgear) {
		if (highgear) {
			shifterRight.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting right side into high gear (fwd)");
		}
		else {
			shifterRight.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting right side into low gear (rev)");
		}
	}
}
