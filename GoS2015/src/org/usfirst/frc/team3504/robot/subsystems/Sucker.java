package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Kriti
 */
public class Sucker extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Talon rightSucker;            // defining a Talon and naming it rightSucker
	private Talon leftSucker;             // defining a Talon motor and naming it leftSucker
	private Talon suckerAngleRight;       // defining a Talon motor and naming it suckerAngleRight
	private Talon suckerAngleLeft;        // defining a Talon motor and naming it suckerAngleLeft
	private DigitalInput leftLimit;
	private DigitalInput rightLimit;
	
	public Sucker()                //this is the constructor
	{
		/*
		rightSucker = new Talon(RobotMap.RIGHT_SUCKER_WHEEL);
		leftSucker = new Talon(RobotMap.LEFT_SUCKER_WHEEL);
		suckerAngleRight = new Talon(RobotMap.RIGHT_SUCKER_ANGLE_WHEEL);
		suckerAngleLeft = new Talon(RobotMap.LEFT_SUCKER_ANGLE_WHEEL);
		leftLimit = new DigitalInput(RobotMap.LEFT_SUCKER_LIMIT);
		rightLimit = new DigitalInput(RobotMap.RIGHT_SUCKER_LIMIT);
		
		**/
	}
	
	public void suckToteIn (){              //creating method suckToteIn which suck a tote inside the robot
		rightSucker.set(0.5);
		leftSucker.set(-0.5);
	}
	
	public void suckToteOut (){             //creating method suckToteOut which pushes a Tote out
		rightSucker.set(-0.5);
		leftSucker.set(0.5);
	}
	
	public void suckerAngleOut (){
		suckerAngleRight.set(0.5);
		suckerAngleLeft.set(-0.5);
	}
	
	public void suckerAngleIn (){
		suckerAngleRight.set(-0.5);
		suckerAngleLeft.set(0.5);
	}
	
	public void stopSucking(){
		rightSucker.set(0.0);
		leftSucker.set(0.0);
	}
	
	public void stopAngle (){
		suckerAngleRight.set(0.0);
		suckerAngleLeft.set(0.0);
	}
	
	public boolean getLimit()
	{
		return(leftLimit.get() && rightLimit.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

