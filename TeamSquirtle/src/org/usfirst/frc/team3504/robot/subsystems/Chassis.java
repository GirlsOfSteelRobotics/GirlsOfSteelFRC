package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Chassis {

	CANTalon rt1 = RobotMap.rightTalon1;
	CANTalon rt2 = RobotMap.rightTalon2;
	CANTalon rt3 = RobotMap.rightTalon3;
	CANTalon lt1 = RobotMap.leftTalon1;
	CANTalon lt2 = RobotMap.leftTalon2;
	CANTalon lt3 = RobotMap.leftTalon3;
	RobotDrive driveSystem = RobotMap.driveSystem;
	Joystick stick;
	
	public Chassis() {
		
	}
	
	public void driveByJoystick(Joystick stick) {
		driveSystem.arcadeDrive(stick);
	}
	
	public void initDefaultCommand() {
		
	}
	
	
}
