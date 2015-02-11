package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Doors extends Subsystem{
	
	private CANTalon leftTalon;
	private CANTalon rightTalon;
	
	public Doors() {
		//leftTalon = new CANTalon(RobotMap.LEFT_DOOR_CHANNEL);
		//rightTalon = new CANTalon(RobotMap.RIGHT_DOOR_CHANNEL);
	}

	public void doorsIn() {
		leftTalon.set(-0.5);
		rightTalon.set(0.5);
	}
	
	public void doorsOut() {
		leftTalon.set(0.5);
		rightTalon.set(- 0.5);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub	
	}
	
}
