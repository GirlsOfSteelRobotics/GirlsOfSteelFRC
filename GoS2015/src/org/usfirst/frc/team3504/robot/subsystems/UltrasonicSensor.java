package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class UltrasonicSensor extends Subsystem{

	private Ultrasonic ultrasonicSensor;
	
	public UltrasonicSensor() {
		//ultrasonicSensor = new Ultrasonic(RobotMap.ULTRASONICSENSOR_CHANNEL);
	}

	public double getDistanceInches() {
		return 0.0; //ultrasonicSensor.getRangeInches();
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
