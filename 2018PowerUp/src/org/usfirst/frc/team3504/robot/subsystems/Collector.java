package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	
	private WPI_TalonSRX collectLeftA = new WPI_TalonSRX(RobotMap.COLLECT_LEFT_A);
	private WPI_TalonSRX collectLeftB = new WPI_TalonSRX(RobotMap.COLLECT_LEFT_B);
	
	private WPI_TalonSRX collectRightA = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT_A);
	private WPI_TalonSRX collectRightB = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT_B);

	public Collector() {
		setFollowerMode();
		collectLeftA.setSensorPhase(true);
		collectRightA.setSensorPhase(true);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setupFPID(WPI_TalonSRX talon) {
		//talon.setPosition (0); TODO figure out new syntax
		talon.config_kF(0, 0, 0);
		talon.config_kP(0, 0, 0);
		talon.config_kI(0, 0, 0);
		talon.config_kD(0, 0, 0);	
	}
    
    public WPI_TalonSRX getCollectorLeft() {
		return collectLeftA;
	}
    
    public WPI_TalonSRX getCollectorRight() {
		return collectRightA;
	}
    
    public void stop() {
    		collectLeftA.stopMotor();
    		collectRightA.stopMotor();
    }
    
    public void collect() {
    		collectLeftA.set(1.0); //TODO: tune this speed, and these values may be reversed
    		collectRightA.set(-1.0);
    }
    
    public void release() {
    		collectLeftA.set(-1.0); //TODO: tune this speed, and these values may be reversed
    		collectRightA.set(1.0);
    }
    
    public void setFollowerMode() {
    		collectLeftB.follow(collectLeftA);
    		collectRightB.follow(collectRightA);
    }
}

