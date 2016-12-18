package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.subsystems.Shifters.Speed;

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
    
    public boolean inHighGear;
    
    public Shifters() {
		shifterLeft = new DoubleSolenoid(RobotMap.PCM_SHIFTER, RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
		shifterRight = new DoubleSolenoid(RobotMap.PCM_SHIFTER, RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
    public void shiftLeft(Speed speed) {
		if (speed == Speed.kHigh) {
			shifterLeft.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting left side into high gear (fwd)");
			inHighGear = true;
		} else {
			shifterLeft.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting left side into low gear (rev)");
			inHighGear = false;
		}
	}
	
	public void shiftRight(Speed speed) {
		if (speed == Speed.kHigh) {
			shifterRight.set(DoubleSolenoid.Value.kReverse);
			System.out.println("Shifting right side into high gear (fwd)");
			inHighGear = true;
		} else {
			shifterRight.set(DoubleSolenoid.Value.kForward);
			System.out.println("Shifting right side into low gear (rev)");
			inHighGear = false;
		}
	
	}   
	
	public boolean getRightShifterValue() {
		return shifterRight.get() != DoubleSolenoid.Value.kForward;
	}
	
	public boolean getLeftShifterValue() {
		return shifterLeft.get() != DoubleSolenoid.Value.kForward;
	}
	
	public boolean getGearSpeed() {
		return inHighGear;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}