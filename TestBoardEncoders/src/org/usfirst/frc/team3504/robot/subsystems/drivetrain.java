package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class drivetrain extends Subsystem {

	private static CANTalon Wheel1;
	
	public drivetrain()
	{
	Wheel1 = new CANTalon(RobotMap.Motor1);
	}
	
	public void initDefaultCommand() {
	}
	
	public void forward(){
		Wheel1.set(.1);

		System.out.println("Wheel 1 Encoder Position: " + Wheel1.getEncPosition()); //prints to Riolog
		System.out.println("Wheel 1 Encoder Velocity: " + Wheel1.getEncVelocity());
		
   	 
	   	SmartDashboard.putNumber("Encoder 1 Position", Wheel1.getEncPosition()); //prints to SmartDashboard
	   	SmartDashboard.putNumber("Encoder 1 Velocity", Wheel1.getEncVelocity());  
	
	   	SmartDashboard.putNumber("Encoder 1 Velocity Graph", Wheel1.getEncPosition());
	   	SmartDashboard.putNumber("Encoder 1 Position Graph", Wheel1.getEncVelocity());
	}

	public void backward(){
		Wheel1.set(-.1);
	
		System.out.println("Wheel 1 Encoder Position: " + Wheel1.getEncPosition()); //prints to Riolog
		System.out.println("Wheel 1 Encoder Velocity: " + Wheel1.getEncVelocity());
		
	   	SmartDashboard.putNumber("Encoder 1 Position", Wheel1.getEncPosition()); //prints to SmartDashboard
	   	SmartDashboard.putNumber("Encoder 1 Velocity", Wheel1.getEncVelocity());  
	
	   	SmartDashboard.putNumber("Encoder 1 Velocity Graph", Wheel1.getEncPosition());
	   	SmartDashboard.putNumber("Encoder 1 Position Graph", Wheel1.getEncVelocity());
	}

	public void stop()
	{
		Wheel1.set(0);
	}
}


