package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Forklift extends Subsystem {
	
	private Talon liftTalon;
	private Solenoid liftSD;
	
	
	public Forklift() {
		liftTalon = new Talon(RobotMap.FORKLIFT_CHANNEL);
		liftSD = new Solenoid(0); //Assume 0 for PCM ID
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void up(double speed) {
		liftTalon.set(speed);
	}
	
	public void down(double speed) {
		liftTalon.set(speed);
	}
	
	public void upPneum() {
		liftSD.set(true); //?
	}
	
	public void downPneum() {
		liftSD.set(true); // FIXME: how does this work?? HALP. 
	}
}
