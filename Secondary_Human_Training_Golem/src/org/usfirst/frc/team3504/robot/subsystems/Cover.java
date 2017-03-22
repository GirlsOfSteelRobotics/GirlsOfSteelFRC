package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Cover extends Subsystem {
	
	private DoubleSolenoid cover; 
	private boolean coverPosition;
	
	public Cover() {
		cover = new DoubleSolenoid(RobotMap.GEAR_COVER_A, RobotMap.GEAR_COVER_B);
		coverPosition = false;
		
		LiveWindow.addActuator("Cover", "cover", cover);
	}

    public void coverPosition(boolean extended) {
    	if (extended == true) {
    		cover.set(DoubleSolenoid.Value.kForward);
    	} 
    	else{
    		cover.set(DoubleSolenoid.Value.kReverse);
    	}
		coverPosition = extended;
    	System.out.println("Cover is extended: " + extended); 
	}
    
	public boolean getCoverPosition() {
		return coverPosition;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
