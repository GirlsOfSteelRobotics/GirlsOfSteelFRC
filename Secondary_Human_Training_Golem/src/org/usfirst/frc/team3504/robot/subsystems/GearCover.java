package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearCover extends Subsystem {
	
	private Solenoid cover; 
	
	public GearCover() 
	{
		cover = new Solenoid(RobotMap.GEAR_COVER);
	}

    public void coverPosition(boolean extended) {
    	cover.set(extended);
		System.out.println("cover is extended: " + extended); 
		}
    
	public boolean getCoverPosition() {
		return cover.get();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
