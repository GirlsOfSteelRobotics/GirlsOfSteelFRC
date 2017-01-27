package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearCover extends Subsystem {
	
	private Solenoid cover; 
	
<<<<<<< HEAD:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/subsystems/GearCover.java
	public GearCover() {
=======
	public Gear() {
>>>>>>> 2fab681d8de0eeed81370d12b97c8a57ca31bdb2:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/subsystems/Gear.java
		cover = new Solenoid(RobotMap.GEAR_COVER);
	}

    public void coverPosition(boolean extended) {
<<<<<<< HEAD:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/subsystems/GearCover.java
		cover.set(extended);
		System.out.println("cover is extended: " + extended); 
=======
    	if (extended == true) {
			cover.set(true);
			System.out.println("Is covered");
		} else {
			cover.set(false);
			System.out.println("Cover is Raised");
		}
>>>>>>> 2fab681d8de0eeed81370d12b97c8a57ca31bdb2:Secondary_Human_Training_Golem/src/org/usfirst/frc/team3504/robot/subsystems/Gear.java
	}
    
	public boolean getCoverPosition() {
		return cover.get();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
