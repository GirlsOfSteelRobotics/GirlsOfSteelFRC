package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSource;

public class TalonEncoder implements PIDSource {

	private static CANTalon talon;
	  
	public TalonEncoder(CANTalon talon) {
	     this.talon = talon;
	}
	  
	public double pidGet() {
	    return talon.getEncVelocity();
	}

}
