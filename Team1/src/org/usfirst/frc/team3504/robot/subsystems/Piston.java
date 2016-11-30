package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
/**
 *
 */
public class Piston extends Subsystem {
    
	private DoubleSolenoid shooterPiston;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Piston()
	{
		shooterPiston = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_A, RobotMap.SHOOTER_PISTON_B);
	}
	public void pistonOut()
	{
		shooterPiston.set(DoubleSolenoid.Value.kForward);
	}
	
	public void pistonIn()
	{
		shooterPiston.set(DoubleSolenoid.Value.kReverse);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

