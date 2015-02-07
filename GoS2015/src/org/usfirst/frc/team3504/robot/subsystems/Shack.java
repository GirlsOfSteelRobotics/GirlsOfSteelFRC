package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Authors: Corinne, Sarah 
 */
public class Shack extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private CANTalon shackCANTalon; 
	
	public void Shack(){ 
		// shackCANTalon = new CANTalon(); //insert port number
	}
	
	public void out(double speed){
		shackCANTalon.set(speed);
		
	}
	
	public void in(double speed){
		shackCANTalon.set(speed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

