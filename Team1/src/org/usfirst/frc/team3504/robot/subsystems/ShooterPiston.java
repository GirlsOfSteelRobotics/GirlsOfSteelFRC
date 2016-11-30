package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
/**
 *
 */
public class ShooterPiston extends Subsystem {
    
	private DoubleSolenoid shooterPistonLeft;
	private DoubleSolenoid shooterPistonRight;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public ShooterPiston()
	{
		shooterPistonLeft = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_LEFT_A, RobotMap.SHOOTER_PISTON_LEFT_B);
		shooterPistonRight = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_RIGHT_A, RobotMap.SHOOTER_PISTON_RIGHT_B);

	}
	public void pistonOut()
	{
		shooterPistonLeft.set(DoubleSolenoid.Value.kForward);
		shooterPistonRight.set(DoubleSolenoid.Value.kForward);
	}
	
	public void pistonIn()
	{
		shooterPistonLeft.set(DoubleSolenoid.Value.kReverse);
		shooterPistonRight.set(DoubleSolenoid.Value.kReverse);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}