package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem 
{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private DoubleSolenoid gearPiston;
	
	public Gear()
	{
		gearPiston = new DoubleSolenoid(RobotMap.GEAR_PISTON_A, RobotMap.GEAR_PISTON_B);
	}

	public void pistonOut()
	{
    	gearPiston.set(DoubleSolenoid.Value.kForward);
		System.out.println("Piston out");
    }
    
    public void pistonIn(){
    	gearPiston.set(DoubleSolenoid.Value.kReverse);
		System.out.println("Piston in");
    }
	    
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

