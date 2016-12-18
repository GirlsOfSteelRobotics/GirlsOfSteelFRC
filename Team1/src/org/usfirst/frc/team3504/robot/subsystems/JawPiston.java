package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class JawPiston extends Subsystem {
    
	
	private DoubleSolenoid jawPiston = new DoubleSolenoid(RobotMap.PCM_ARM, RobotMap.JAW_PISTON_A, RobotMap.JAW_PISTON_B);
	
	public void pistonsOut(){
    	jawPiston.set(DoubleSolenoid.Value.kForward);
		System.out.println("Jaw Piston out");
	}
    
    public void pistonsIn(){
    	jawPiston.set(DoubleSolenoid.Value.kReverse);
		System.out.println("Jaw Piston in");
		
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

