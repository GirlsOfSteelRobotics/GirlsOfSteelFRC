package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final WPI_TalonSRX talonSRX7 = new WPI_TalonSRX(RobotMap.SHOOT_1);
	private final WPI_TalonSRX talonSRX8 = new WPI_TalonSRX(RobotMap.SHOOT_2);
	private final WPI_TalonSRX talonSRX9 = new WPI_TalonSRX(RobotMap.SHOOT_3);
		

	public Shooter() {
		talonSRX7.setNeutralMode(NeutralMode.Brake);
		talonSRX9.setNeutralMode(NeutralMode.Brake);
		talonSRX8.set(ControlMode.Follower, talonSRX7.getDeviceID());
			}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void shoot() {
    	talonSRX7.set(.5);
    	talonSRX9.set(.5);
    }
    public void stop() {
    	talonSRX7.set(0);
    	talonSRX9.set(0);
    }
}

