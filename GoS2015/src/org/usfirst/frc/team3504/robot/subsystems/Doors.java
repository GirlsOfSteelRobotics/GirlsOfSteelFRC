package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Doors extends Subsystem{
	
	private DoubleSolenoid leftDoor;
	private DoubleSolenoid rightDoor;
	
	public Doors() {
		leftDoor = new DoubleSolenoid(RobotMap.LEFT_DOOR_MODULE, RobotMap.LEFT_DOOR_CHANNEL_A, RobotMap.LEFT_DOOR_CHANNEL_B);
		rightDoor = new DoubleSolenoid(RobotMap.RIGHT_DOOR_MODULE, RobotMap.RIGHT_DOOR_CHANNEL_A, RobotMap.RIGHT_DOOR_CHANNEL_B);
	}

	public void doorsIn() {
		leftDoor.set(DoubleSolenoid.Value.kForward);
		rightDoor.set(DoubleSolenoid.Value.kForward);
	}
	
	public void doorsOut() {
		leftDoor.set(DoubleSolenoid.Value.kReverse);
		rightDoor.set(DoubleSolenoid.Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub	
	}
	
}
