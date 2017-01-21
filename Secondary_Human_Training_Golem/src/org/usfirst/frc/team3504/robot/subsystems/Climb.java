package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climb extends Subsystem {
	private CANTalon climbMotorA;
	private CANTalon climbMotorB;
	private DoubleSolenoid pusher;
    
	public Climb(){
		climbMotorA = new CANTalon(RobotMap.CLIMB_MOTOR_A);
		climbMotorB = new CANTalon(RobotMap.CLIMB_MOTOR_B);
	}
	
	public void collect(double speed) {
		climbMotorA.set(speed);
		climbMotorB.set(-speed);
    }
	
	public void stopCollect() {
		climbMotorA.set(0.0);
		climbMotorB.set(0.0);
    }
	
	public void pistonIn(){
	    pusher.set(DoubleSolenoid.Value.kForward);
	}
	
	public void pistonOut(){
	    pusher.set(DoubleSolenoid.Value.kReverse);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

