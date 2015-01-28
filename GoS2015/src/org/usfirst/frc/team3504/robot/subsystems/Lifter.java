package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * @authors
 */
public class Lifter extends Subsystem {
	
	private Talon liftTalon;
	private Solenoid liftSD;
	private DigitalInput leftLimit;
	private DigitalInput rightLimit; //might not be used? i dunno
	
	
	public Lifter() {
	//	liftTalon = new Talon(RobotMap.FORKLIFT_CHANNEL);
		//liftSD = new Solenoid(0); //Assume 0 for PCM ID
		//leftLimit = new DigitalInput(RobotMap.LEFT_FORKLIFT_LIMIT);
		//rightLimit = new DigitalInput(RobotMap.RIGHT_FORKLIFT_LIMIT);
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	public void up(double speed) {
		liftTalon.set(speed);
	}
	
	public void down(double speed) {
		liftTalon.set(speed);
	}
	
	//Add Stop methods
	public void stop()
	{
		liftTalon.stopMotor();
	}
	
	public void upPneum() {
		liftSD.set(true); //?
	}
	
	public void downPneum() {
		liftSD.set(true); // FIXME: how does this work?? HALP. 
	}
	
	public boolean getLimit() {
		return(leftLimit.get() && rightLimit.get());
	}
}
