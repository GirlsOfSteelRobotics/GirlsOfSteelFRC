package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	
	private WPI_TalonSRX collectLeft = new WPI_TalonSRX(RobotMap.COLLECT_LEFT_A);
	private WPI_TalonSRX collectRight = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT_A);

	public Collector() {
		collectLeft.setSensorPhase(true);
		collectRight.setSensorPhase(true);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setupFPID() {
		//talon.setPosition (0); TODO figure out new syntax
    	collectLeft.config_kF(0, 0, 0);
    	collectLeft.config_kP(0, 0, 0);
    	collectLeft.config_kI(0, 0, 0);
    	collectLeft.config_kD(0, 0, 0);	
		
    	collectRight.config_kF(0, 0, 0);
    	collectRight.config_kP(0, 0, 0);
    	collectRight.config_kI(0, 0, 0);
    	collectRight.config_kD(0, 0, 0);
	}
    
    public WPI_TalonSRX getCollectorLeft() {
		return collectLeft;
	}
    
    public WPI_TalonSRX getCollectorRight() {
		return collectRight;
	}
    
    public void stop() {
    		collectLeft.stopMotor();
    		collectRight.stopMotor();
    }
    
    public void collect() {
    		collectLeft.set(1.0); //TODO: tune this speed, and these values may be reversed
    		collectRight.set(-0.5);
    }
    
    public void release() {
    		collectLeft.set(-1.0); //TODO: tune this speed, and these values may be reversed
    		collectRight.set(1.0);
    }
    
}

