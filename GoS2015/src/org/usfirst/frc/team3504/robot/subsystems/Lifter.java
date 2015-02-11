package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * @author Arushi
 */
public class Lifter extends Subsystem {
	
	private CANTalon liftTalon;
	private DigitalInput leftLimit;
	private DigitalInput rightLimit; //might not be used? i dunno
	private TalonEncoder liftEncoder;
	
	public Lifter() {
	//	liftTalon = new Talon(RobotMap.FORKLIFT_CHANNEL);
		//liftSD = new Solenoid(0); //Assume 0 for PCM ID
		//leftLimit = new DigitalInput(RobotMap.LEFT_FORKLIFT_LIMIT);
		//rightLimit = new DigitalInput(RobotMap.RIGHT_FORKLIFT_LIMIT);
		liftEncoder = new TalonEncoder(liftTalon);
	}
	
	public void up(double speed) {
		liftTalon.set(speed);
	}
	
	public void down(double speed) {
		liftTalon.set(speed);
	}
	
	
	public double getLiftEncoder() {
		return liftTalon.getEncPosition();
	}
	//Add Stop methods
	public void stop()
	{
		liftTalon.set(0.0);
	}
	
//	public boolean getLimit() {
//		return(leftLimit.get() && rightLimit.get());

	
	@Override
	protected void initDefaultCommand() {
	}
}
