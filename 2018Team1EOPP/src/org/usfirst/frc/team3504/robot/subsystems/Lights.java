package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.RedAndWhite;

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
		setDefaultCommand(new RedAndWhite());
	}
	
	public void changeColor(int R, int G, int B) {
		lights.showRGB(R, G, B);
	}
	
	public void redAndWhite() {
		//					index time r g b
		lights.writeRegister(0, 10000.0, 255, 0, 0);
		lights.writeRegister(1, 10000.0, 255, 255, 255);
		
		lights.cycle(0, 1);
	}
}