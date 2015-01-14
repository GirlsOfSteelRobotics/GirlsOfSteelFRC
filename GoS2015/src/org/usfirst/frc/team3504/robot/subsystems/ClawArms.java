package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * author Alexa Corinne Sarah
 */
public class ClawArms extends Subsystem {
    
	private Talon leftTalon;
	private Talon rightTalon;
	
	public ClawArms () {
		leftTalon = new Talon(RobotMap.LEFT_CHANNEL);
		rightTalon = new Talon(RobotMap.RIGHT_CHANNEL); 
	}
	
	public void moveIn()
	{
		leftTalon.set(.5);
		rightTalon.set(-.5);
	}
	
	public void moveOut(){
		leftTalon.set(-.5);
		rightTalon.set(.5);
	}
	
	public void stop(){
		leftTalon.stopMotor();
		rightTalon.stopMotor();
		
	}
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

