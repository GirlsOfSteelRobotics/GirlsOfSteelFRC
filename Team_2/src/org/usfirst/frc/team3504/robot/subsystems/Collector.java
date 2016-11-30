package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	private CANTalon collectRight;
	private CANTalon collectLeft;
	
	private DoubleSolenoid pusher; 
	
	public Collector() {
		collectRight = new CANTalon(RobotMap.COLLECT_RIGHT);
		collectLeft = new CANTalon(RobotMap.COLLECT_LEFT);
		
		pusher = new DoubleSolenoid(0, 1);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void collectIn(double speed){
    	collectRight.set(-speed);
    	collectLeft.set(speed);
    }
    
    public void pusherOut(){
        pusher.set(DoubleSolenoid.Value.kForward);
    }
    
    public void pusherIn(){
    	pusher.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void stop() {
    	collectRight.set(0);
    	collectLeft.set(0);
    }
}

