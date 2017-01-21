package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Climb extends Subsystem {
	private CANTalon climbMotor;
	private DoubleSolenoid pusher;
    
	public Climb(){
		climbMotor = new CANTalon(RobotMap.CLIMB_MOTOR);
	}
	
	public void collect(double speed) {
		climbMotor.set(speed);
    }
	
	public void stopCollect() {
		climbMotor.set(0.0);
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

