package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Sonia
 */
public class Sucker extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Talon rightSucker;            // defining a Talon and naming it rightSucker
	private Talon leftSucker;             // defining a Talon motor and naming it leftSucker
	private Talon suckerAngleRight;       // defining a Talon motor and naming it suckerAngleRight
	private Talon suckerAngleLeft;        // defining a Talon motor and naming it suckerAngleLeft
	
	public Sucker()                //this is the constructor
	{
		rightSucker = new Talon(RobotMap.Right_Wheel_Talon_Sucker);
		leftSucker = new Talon(RobotMap.Left_Wheel_Talon_Sucker);
		suckerAngleRight = new Talon(RobotMap.Right_Angle_Talon_Sucker);
		suckerAngleLeft = new Talon(RobotMap.Left_Angle_Talon_Sucker);
		/*suckToteIn ();                  //calling method suckTotein
		suckToteOut ();                 //calling method suckToteOut
		suckerAngleOut ();
		suckerAngleIn ();*/
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
	
	
	//check to see if this is written correctly and in the right place...
	//
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

