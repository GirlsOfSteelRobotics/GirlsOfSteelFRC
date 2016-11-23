package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	private CANTalon collectRight;
	private CANTalon collectLeft;
	
	public Collector() {
		collectRight = new CANTalon(RobotMap.COLLECT_RIGHT);
		collectLeft = new CANTalon(RobotMap.COLLECT_LEFT);
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
    
    public void stop() {
    	collectRight.set(0);
    	collectLeft.set(0);
    }
}

