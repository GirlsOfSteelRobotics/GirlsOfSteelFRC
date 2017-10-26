package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Climber extends Subsystem {
	private CANTalon climbMotorA;
	private CANTalon climbMotorB; 
    
	public Climber(){
		climbMotorA = new CANTalon(RobotMap.CLIMB_MOTOR_A);
		climbMotorB = new CANTalon(RobotMap.CLIMB_MOTOR_B);
		
		climbMotorA.enableBrakeMode(true);
		climbMotorB.enableBrakeMode(true);
		
		
	}
	
	public void climb(double speed) {
		climbMotorA.set(speed);
		climbMotorB.set(speed);
    }
	
	public void stopClimb() {
		climbMotorA.set(0.0);
		climbMotorB.set(0.0);
    }
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

