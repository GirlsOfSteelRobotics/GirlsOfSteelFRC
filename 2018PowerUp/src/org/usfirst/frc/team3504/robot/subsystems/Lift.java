package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	private WPI_TalonSRX liftA = new WPI_TalonSRX(RobotMap.LIFT_A);
	private WPI_TalonSRX liftB = new WPI_TalonSRX(RobotMap.LIFT_B);
	
	public Lift() {
		liftB.follow(liftA);
		liftA.setSensorPhase(true);
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
    
    public WPI_TalonSRX getLiftTalon() {
		return liftA;
	}
    
    public void setSpeed(double speed) {
    		liftA.set(speed); //value between -1.0 and 1.0;
    }
	
	public void stop() {
		liftA.stopMotor();
	}
}

