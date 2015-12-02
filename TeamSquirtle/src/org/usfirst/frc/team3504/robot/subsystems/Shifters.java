package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shifters extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private DoubleSolenoid shifterLeft;
	private DoubleSolenoid shifterRight;
	
	public void shiftLeft(boolean highgear) {
		if(highgear==true) {
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

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

