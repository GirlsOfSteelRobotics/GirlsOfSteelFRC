package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	
	private WPI_TalonSRX collectLeft;
	private WPI_TalonSRX collectRight;

	public Collector() {
		collectLeft = new WPI_TalonSRX(RobotMap.COLLECT_LEFT);
		collectRight = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT);
		collectLeft.setSensorPhase(true);
		collectRight.setSensorPhase(true);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
   
    
    public void stop() {
    		collectLeft.stopMotor();
    		collectRight.stopMotor();
    }
    
    public void collect() {
    		collectLeft.set(-1.0); //TODO: tune this speed, and these values may be reversed
    		collectRight.set(0.5);
    }
    
    public void release() {
    		if (Robot.lift.getLiftPosition() < -13000) {
    			collectLeft.set(1.0); //TODO: tune this speed, and these values may be reversed
        		collectRight.set(-1.0);
    		}
    		else
    		{
    			collectLeft.set(0.8); //TODO: tune this speed, and these values may be reversed
        		collectRight.set(-0.8);
    		}
    		
    }
    
}

