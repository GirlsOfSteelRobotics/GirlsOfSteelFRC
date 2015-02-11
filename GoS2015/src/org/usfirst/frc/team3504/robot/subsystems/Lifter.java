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
	private TalonEncoder liftEncoder;
	
	public Lifter() {
		liftTalon = new CANTalon(RobotMap.FORKLIFT_CHANNEL);
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

	public void stop()
	{
		liftTalon.set(0.0);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
}
