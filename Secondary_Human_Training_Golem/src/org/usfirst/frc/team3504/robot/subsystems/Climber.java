package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Climber extends Subsystem {
	private TalonSRX climbMotorA;
	private TalonSRX climbMotorB; 
    
	public Climber(){
		climbMotorA = new TalonSRX(RobotMap.CLIMB_MOTOR_A);
		climbMotorB = new TalonSRX(RobotMap.CLIMB_MOTOR_B);
		
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

