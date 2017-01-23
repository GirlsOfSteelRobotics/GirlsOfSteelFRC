package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private CANTalon shooterMotorA;
	private CANTalon shooterMotorB;
	
	  
	public Shooter(){
		shooterMotorA = new CANTalon(RobotMap.SHOOTER_MOTOR_A);
		shooterMotorB = new CANTalon(RobotMap.SHOOTER_MOTOR_B);
	}
		
	public void shootBall(double speed){
		shooterMotorA.set(speed);
		shooterMotorB.set(-speed);
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

}
