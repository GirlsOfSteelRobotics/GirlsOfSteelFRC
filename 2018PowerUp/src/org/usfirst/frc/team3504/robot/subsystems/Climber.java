package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	public WPI_TalonSRX climbMotor;

	public Climber() {
		climbMotor = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR);
		
		climbMotor.setNeutralMode(NeutralMode.Brake);
		climbMotor.configContinuousCurrentLimit(200, 10);
		

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	} 
	
	public void climb(double speed) {
		climbMotor.set(speed);
	}
	
	public void stopClimb() {
		climbMotor.set(0.0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

