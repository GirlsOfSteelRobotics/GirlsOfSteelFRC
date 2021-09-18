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
	private double SHIFTING_THRESHOLD = 0; 
	//TODO find correct shifting value
	
	public enum Speed {
		kHigh, kLow
	};

	private Speed speed;

	public Shifters() {
		shifterLeft = new DoubleSolenoid(RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
		shifterRight = new DoubleSolenoid(RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

		LiveWindow.addActuator("Shifters", "shifterLeft", shifterLeft);
		LiveWindow.addActuator("Shifters", "shifterRight", shifterRight);

	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void shiftGear(Speed speed) {
		this.speed = speed;
		if (speed == Speed.kLow) {
			shifterLeft.set(DoubleSolenoid.Value.kForward);
			shifterRight.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting left and right side into low gear (fwd)");
		} else {
			shifterLeft.set(DoubleSolenoid.Value.kReverse);
			shifterRight.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting left and right side into high gear (rev)");
		}
	}

	public Speed getGearSpeed() {
		return speed;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public double getShiftingThreshold() {
		return SHIFTING_THRESHOLD;
	}
}