package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Ramp extends Subsystem {
    public static DoubleSolenoid rightramp = RobotMap.DSRightRamp;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void Up(){
		rightramp.set(DoubleSolenoid.Value.kForward);
	}

	public void Down(){
		rightramp.set(DoubleSolenoid.Value.kReverse);
	}
	
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

