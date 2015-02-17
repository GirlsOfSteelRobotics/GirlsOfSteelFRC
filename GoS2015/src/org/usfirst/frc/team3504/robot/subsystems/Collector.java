package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Kriti
 */
public class Collector extends Subsystem {

	//Talons
	private CANTalon rightCollector;
	private CANTalon leftCollector;
	
	//Pistons
	private DoubleSolenoid collectorLeftSolenoid;
	private DoubleSolenoid collectorRightSolenoid;
	
	public Collector()                //this is the constructor
	{
		rightCollector = new CANTalon(RobotMap.RIGHT_COLLECTOR_WHEEL);
		leftCollector = new CANTalon(RobotMap.LEFT_COLLECTOR_WHEEL);
		
		collectorLeftSolenoid = new DoubleSolenoid(RobotMap.LEFT_COLLECTOR_MODULE,
													RobotMap.LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
													RobotMap.LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL);
		collectorRightSolenoid = new DoubleSolenoid(RobotMap.RIGHT_COLLECTOR_MODULE,
													RobotMap.RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
													RobotMap.RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL);
	}
	
	//Method suckToteIn which suck a tote inside the robot
	public void collectorToteIn(){
		rightCollector.set(1);
		leftCollector.set(-1);
	}

	//Method suckToteOut which pushes a Tote out
	public void collectorToteOut(){
		rightCollector.set(-1);
		leftCollector.set(1);
	}
	
	//Method collectorToteRotate which rotates the tote inside the trifold
	public void collectorToteRotateRight(){
		rightCollector.set(-1);
		leftCollector.set(1);
	}
	
	public void collectorToteRotateLeft(){
		rightCollector.set(1);
		leftCollector.set(-1);
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
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

