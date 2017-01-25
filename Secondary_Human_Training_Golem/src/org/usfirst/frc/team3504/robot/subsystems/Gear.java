package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.subsystems.Shifters.Speed;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
	
	private Solenoid cover; 
	
	private boolean extended;
	
	public Gear() {
		cover = new Solenoid(RobotMap.GEAR_COVER); //ASK WUTS UP
	}

    public void coverPosition(boolean extended) {
    	if (extended == true) { //HERE TOO not right
			//cover.set(Solenoid..kForward);// AND HERE
			System.out.println("Is covered");
			extended = true;
		} else {
			//cover.set(Solenoid..kReverse);
			System.out.println("Cover is Raised");
			extended = false;
		}
	}
    
	public boolean getCoverPosition() {
		return extended;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
