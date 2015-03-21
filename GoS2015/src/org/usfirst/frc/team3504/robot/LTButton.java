package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class LTButton extends Trigger{
	/*
	 * This is the LT button that when pressed returns positive values
	 */
	public boolean get(){
		return Robot.oi.getOperatorJoystick().getZ() > 0.1 ;
		
	}
	
}
