package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ConveyorBelt extends Subsystem{
	
	CANTalon ct1 = RobotMap.conveyorTalon1;
	CANTalon ct2 = RobotMap.conveyorTalon2;
	
	public void initDefaultCommand() {
			
	}
	
	public void conveyorBeltUp(){
		ct1.set(0.5);
		ct2.set(-0.5);
	}
	
	public void conveyorBeltDown(){
		ct1.set(-0.5);
		ct2.set(0.5);
	}

}
