package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

/*
 *
 */

public class Pusher extends Subsystem {

	private Solenoid pusherSolenoid;
	 
	public Pusher() {
		
	//	pusherSolenoid = new Solenoid(22); //real port goes here
		
	}
	
	public void out() {
		pusherSolenoid.set(true);
	}
	
	public void in(){
	//	pusherSolenoid.set(true):
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
    
	
	
	
	
}

