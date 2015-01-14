package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @authors Alexa, Corinne, Sarah
 */
public class ClawArms extends Subsystem {
    
	private Talon leftTalon;
	private Talon rightTalon;
	
	public ClawArms () {
		leftTalon = new Talon(RobotMap.LEFT_CLAW_CHANNEL);
		rightTalon = new Talon(RobotMap.RIGHT_CLAW_CHANNEL); 
	}
	
	public void moveIn()
	{
		leftTalon.set(.5);
		rightTalon.set(-.5);
	}
	
	public void moveOut()
	{
		leftTalon.set(-.5);
		rightTalon.set(.5);
	}
	
	public void stop()
	{
		leftTalon.stopMotor();
		rightTalon.stopMotor();
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

