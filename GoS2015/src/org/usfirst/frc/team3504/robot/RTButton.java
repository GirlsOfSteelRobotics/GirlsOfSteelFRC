package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class RTButton extends Trigger {
	/*
	 * This is the RT button that when pressed returns negative  values
	 */
	public boolean get(){
		return Robot.oi.getOperatorJoystick().getZ() < -0.1 ;
		
	}
	
}
