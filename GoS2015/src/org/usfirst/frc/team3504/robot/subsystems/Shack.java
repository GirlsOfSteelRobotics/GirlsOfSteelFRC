package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Authors: Corinne, Sarah 
 */
public class Shack extends Subsystem {
	
	private DoubleSolenoid leftShack;
	//private DoubleSolenoid rightShack;
	
	public Shack() {
		leftShack = new DoubleSolenoid(RobotMap.LEFT_SHACK_MODULE, RobotMap.LEFT_SHACK_CHANNEL_A, RobotMap.LEFT_SHACK_CHANNEL_B);
		//rightShack = new DoubleSolenoid(RobotMap.RIGHT_SHACK_MODULE, RobotMap.RIGHT_SHACK_CHANNEL_A, RobotMap.RIGHT_SHACK_CHANNEL_B);
	}

	public void ShackIn() {
		leftShack.set(DoubleSolenoid.Value.kForward);
		//rightShack.set(DoubleSolenoid.Value.kForward);
	}
	
	public void ShackOut() {
		leftShack.set(DoubleSolenoid.Value.kReverse);
		//rightShack.set(DoubleSolenoid.Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub	
	}
	
}