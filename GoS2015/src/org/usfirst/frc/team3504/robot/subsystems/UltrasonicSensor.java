package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.TestUltrasonic;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author arushibandi
 */

public class UltrasonicSensor extends Subsystem{

	private AnalogInput ultrasonicSensor;
	
	public UltrasonicSensor() {
		ultrasonicSensor = new AnalogInput(RobotMap.ULTRASONICSENSOR_CHANNEL);
	}

	public double getDistanceInches() {
		return ultrasonicSensor.getVoltage()*(512/5); //5 volts/512 per inch
	}
	
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TestUltrasonic());
		// TODO Auto-generated method stub
		
	}
}
