package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Kriti
 */
public class Collector extends Subsystem {

	//Talons
	private Talon rightCollector;
	private Talon leftCollector;
	
	//Limit Switches
	//private DigitalInput leftLimit;
	//private DigitalInput rightLimit;
	
	//Pistons
	private DoubleSolenoid collectorLeftSolenoid;
	private DoubleSolenoid collectorRightSolenoid;
	
	public Collector()                //this is the constructor
	{
		rightCollector = new Talon(RobotMap.RIGHT_COLLECTOR_WHEEL);
		leftCollector = new Talon(RobotMap.LEFT_COLLECTOR_WHEEL);
		
	//	leftLimit = new DigitalInput(RobotMap.LEFT_COLLECTOR_LIMIT);
	//	rightLimit = new DigitalInput(RobotMap.RIGHT_COLLECTOR_LIMIT);
		
		collectorLeftSolenoid = new DoubleSolenoid(RobotMap.LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
													RobotMap.LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL);
		collectorRightSolenoid = new DoubleSolenoid(RobotMap.RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
													RobotMap.RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL);
	}
	
	//Method suckToteIn which suck a tote inside the robot
	public void collectorToteIn(){
		rightCollector.set(0.5);
		leftCollector.set(-0.5);
	}

	//Method suckToteOut which pushes a Tote out
	public void collectorToteOut(){
		rightCollector.set(-0.5);
		leftCollector.set(0.5);
	}
	
	//Method collectorToteRotate which rotates the tote inside the trifold
	public void collectorToteRotate(){
		rightCollector.set(0.5);
		leftCollector.set(0.5);
	}
	
	public void collectorIn(){
		collectorRightSolenoid.set(DoubleSolenoid.Value.kForward);
		collectorLeftSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void collectorOut(){
		collectorRightSolenoid.set(DoubleSolenoid.Value.kReverse);
		collectorLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stopCollecting(){
		rightCollector.set(0.0);
		leftCollector.set(0.0);
	}
	
//	public boolean getLimit()
	{
//		return(leftLimit.get() && rightLimit.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

