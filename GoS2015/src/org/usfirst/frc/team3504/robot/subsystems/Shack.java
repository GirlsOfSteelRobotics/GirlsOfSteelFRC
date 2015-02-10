package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Authors: Corinne, Sarah 
 */
public class Shack extends Subsystem {
	
	private CANTalon shackCANTalon; 
	
	private DigitalInput leftLimit;
	private DigitalInput rightLimit; 
	
	public void Shack(){ 
		// shackCANTalon = new CANTalon(); //insert port number
	}
	
	public void out(double speed){
		shackCANTalon.set(speed);
		
	}
	
	public void in(double speed){
		shackCANTalon.set(speed);
	}
	
	public boolean getLimit() {
		if ((rightLimit.get() == true) &&  (leftLimit.get() == true))
			return true;
		else
			return false;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

