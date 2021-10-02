package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;



/**
 *
 */
public class Shooter extends Subsystem {
	
	private WPI_TalonSRX shooterTop ;	
	private WPI_TalonSRX shooterBottom;
		
public Shooter () {
	
	shooterTop = new WPI_TalonSRX(RobotMap.SHOOTER_TOP);
	shooterBottom = new WPI_TalonSRX(RobotMap.SHOOTER_BOTTOM);
	
	shooterTop.setNeutralMode(NeutralMode.Brake);
	shooterBottom.setNeutralMode(NeutralMode.Brake);
}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
    
    public void shoot() {
    	shooterTop.set(0.78);
    	shooterBottom.set(-0.7);
    }
    
    public void stop() {
    	shooterTop.set(0);
    	shooterBottom.set(0);
    }
}

