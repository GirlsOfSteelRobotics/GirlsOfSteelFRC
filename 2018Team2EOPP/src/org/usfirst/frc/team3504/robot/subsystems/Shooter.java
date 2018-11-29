package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	
	private WPI_TalonSRX shooter1master;
	private WPI_TalonSRX shooter1follower;
	
	private WPI_TalonSRX shooter2master;
	
public Shooter () {
	
	shooter1master= new WPI_TalonSRX(RobotMap.SHOOTER_1_MASTER);
	shooter1follower= new WPI_TalonSRX(RobotMap.SHOOTER_1_FOLLOWER);
	
	shooter2master=new WPI_TalonSRX(RobotMap.SHOOTER_2_MASTER);
	
	shooter1follower.follow(shooter1master, FollowerType.PercentOutput);
	
	shooter1master.setNeutralMode(NeutralMode.Brake);
	shooter1follower.setNeutralMode(NeutralMode.Brake);
	
	shooter2master.setNeutralMode(NeutralMode.Brake);
	
	
}


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

