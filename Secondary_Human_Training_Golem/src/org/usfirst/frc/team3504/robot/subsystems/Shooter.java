package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;


public class Shooter extends Subsystem {
	private CANTalon shooterMotor;
	private CANTalon flap;
	
	  
		public Shooter(){
			shooterMotor = new CANTalon(RobotMap.SHOOTER_MOTOR);
			flap = new CANTalon(RobotMap.FLAP_MOTOR);
		}
		
	public void shootBall(double speed){
		shooterMotor.set(speed);
	}
	
	public void Flap(double speed){
		flap.set(speed);
	}
    
	
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

}

