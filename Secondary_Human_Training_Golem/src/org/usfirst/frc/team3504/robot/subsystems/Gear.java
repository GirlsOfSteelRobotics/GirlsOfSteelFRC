package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
	
	private Solenoid cover; 
	
	public Gear() {
		cover = new Solenoid(RobotMap.GEAR_COVER);
	}

    public void coverPosition(boolean extended) {
    	if (extended == true) {
			cover.set(true);
			System.out.println("Is covered");
		} else {
			cover.set(false);
			System.out.println("Cover is Raised");
		}
	}
    
	public boolean getCoverPosition() {
		return cover.get();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
