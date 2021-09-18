package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.mindsensors.CANLight;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lights extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private CANLight lights;
	
	public Lights () {
		lights = new CANLight(RobotMap.LIGHTS);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void changeColor(int R, int G, int B) {
		lights.showRGB(R, G, B);
	}
}
