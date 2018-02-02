package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	private WPI_TalonSRX lift = new WPI_TalonSRX(RobotMap.LIFT);
	private WPI_TalonSRX pivot = new WPI_TalonSRX(RobotMap.PIVOT);
	private DigitalInput limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
	
	public Lift() {
		lift.setSensorPhase(true);
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setupFPID(WPI_TalonSRX talon) {
		//talon.setPosition(0); //TODO figure out new syntax
    	talon.config_kF(0, 0, 10);
		talon.config_kP(0, 0.5, 10);
		talon.config_kI(0, 0, 10);
		talon.config_kD(0, 0, 10);	
	}
    
    public void setupPivotFPID(WPI_TalonSRX talon) {
		//talon.setPosition (0); TODO figure out new syntax
		talon.config_kF(0, 0, 10);
		talon.config_kP(0, 0.5, 10);
		talon.config_kI(0, 0, 10);
		talon.config_kD(0, 0, 10);	
	}
    
    
    public WPI_TalonSRX getLiftTalon() {
    	return lift;
    }

    public WPI_TalonSRX getPivotTalon() {
    	return pivot;
    }
    
    public void setSpeed(double speed) {
    		lift.set(speed); //value between -1.0 and 1.0;
    }

    public void setPivotSpeed(double speed) {
    		pivot.set(speed); //value between -1.0 and 1.0;
    }

    public void stop() {
    		lift.stopMotor();
    }

	public void pivotStop(){
		pivot.stopMotor();
	}
	
	public boolean getLimitSwitch(){
		return limitSwitch.get();
	}
}

