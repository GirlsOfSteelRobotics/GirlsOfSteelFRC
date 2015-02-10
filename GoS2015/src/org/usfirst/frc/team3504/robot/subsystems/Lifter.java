package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * @author Arushi
 */
public class Lifter extends Subsystem {
	
	private Talon liftTalon;
	private DigitalInput leftLimit;
	private DigitalInput rightLimit; //might not be used? i dunno
	
	
	public Lifter() {
	//	liftTalon = new Talon(RobotMap.FORKLIFT_CHANNEL);
		//liftSD = new Solenoid(0); //Assume 0 for PCM ID
		//leftLimit = new DigitalInput(RobotMap.LEFT_FORKLIFT_LIMIT);
		//rightLimit = new DigitalInput(RobotMap.RIGHT_FORKLIFT_LIMIT);
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
	
	public boolean getLimit() {
		return(leftLimit.get() && rightLimit.get());
	}
	
	@Override
	protected void initDefaultCommand() {
	}
}
