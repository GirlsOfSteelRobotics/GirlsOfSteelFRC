package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Shifters extends Subsystem {
	private DoubleSolenoid shifterLeft;
	private DoubleSolenoid shifterRight;

	public enum Speed {kHigh, kLow};
	
	public Shifters() {
		shifterLeft = new DoubleSolenoid(RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
		LiveWindow.addActuator("Shifters", "Shifter Left", shifterLeft);

		shifterRight = new DoubleSolenoid(RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);
		LiveWindow.addActuator("Shifters", "Shifter Right", shifterRight);
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//There's no default command for this subsystem
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void shiftLeft(Speed speed) {
		if (speed == Speed.kHigh) {
			shifterLeft.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting left side into high gear (fwd)");
		} else {
			shifterLeft.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting left side into low gear (rev)");
		}
	}
	
	public void shiftRight(Speed speed) {
		if (speed == Speed.kHigh) {
			shifterRight.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting right side into high gear (fwd)");
		} else {
			shifterRight.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting right side into low gear (rev)");
		}
	}
}
