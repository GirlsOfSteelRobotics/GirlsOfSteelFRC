package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot; 
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



/**
 *
 */
public class Shooter extends Subsystem {
	
	private WPI_TalonSRX shooter1master;	
	private WPI_TalonSRX shooter2master;
		
public Shooter () {
	
	shooter1master= new WPI_TalonSRX(RobotMap.SHOOTER_1_MASTER);
	
	shooter2master=new WPI_TalonSRX(RobotMap.SHOOTER_2_MASTER);
	
	
	shooter1master.setNeutralMode(NeutralMode.Brake);
	
	shooter2master.setNeutralMode(NeutralMode.Brake);
	
}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
    
    public void shoot() {
    	shooter1master.set(0.5);
    	shooter2master.set(-0.5);
    }
    
    public void stop() {
    	shooter1master.set(0);
    	shooter2master.set(0);
    }
}

