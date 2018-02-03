package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Wrist extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private WPI_TalonSRX wrist;
	
	public Wrist() {
		wrist = new WPI_TalonSRX(RobotMap.PIVOT);
		setupWristFPID();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    public void setupWristFPID() {
		//talon.setPosition (0); TODO figure out new syntax
		wrist.config_kF(0, 0, 10);
		wrist.config_kP(0, 0.5, 10);
		wrist.config_kI(0, 0, 10);
		wrist.config_kD(0, 0, 10);	
	}
    
   
//    public WPI_TalonSRX getLiftTalon() {
//    	return lift;
//    }
    

    public void setWristSpeed(double speed) {
    		wrist.set(speed); //value between -1.0 and 1.0;
    }

	public void wristStop(){
		wrist.stopMotor();
	}
}

