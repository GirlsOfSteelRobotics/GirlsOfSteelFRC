package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	public WPI_TalonSRX lift;
	private DigitalInput limitSwitch;
	
	public Lift() {
		lift = new WPI_TalonSRX(RobotMap.LIFT); 
		limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
		lift.setSensorPhase(true);
		setupLiftFPID();

	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setupLiftFPID() {
		//talon.setPosition(0); //TODO figure out new syntax
    	lift.config_kF(0, 0, 10);
		lift.config_kP(0, 1.5, 10);
		lift.config_kI(0, 0.001, 10);
		lift.config_kD(0, 0, 10);	
	}
    
   
//    public WPI_TalonSRX getLiftTalon() {
//    	return lift;
//    }
    
    public void setLiftSpeed(double speed) {
    		lift.set(speed); //value between -1.0 and 1.0;
    }

    public void stop() {
    		lift.stopMotor();
    }
	
	public boolean getLimitSwitch(){
		return limitSwitch.get();
	}
}

