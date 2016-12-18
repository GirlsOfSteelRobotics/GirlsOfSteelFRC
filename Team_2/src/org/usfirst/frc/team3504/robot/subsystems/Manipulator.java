package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Manipulator extends Subsystem {
	private CANTalon collectRight;
	private CANTalon collectLeft;
	
	private DoubleSolenoid pusher; 
	
	private CANTalon pivotA; 
	private CANTalon pivotB; 
	
	public Manipulator() {
		collectRight = new CANTalon(RobotMap.COLLECT_RIGHT);
		collectLeft = new CANTalon(RobotMap.COLLECT_LEFT);
		
		pusher = new DoubleSolenoid(0, 1);
		
		pivotA = new CANTalon(RobotMap.PIVOT_RIGHT); 
		pivotB = new CANTalon(RobotMap.PIVOT_LEFT); 
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void collectIn(double speed){
    	collectRight.set(speed);
    	collectLeft.set(speed);
    }
    
    public void pusherOut(){
        pusher.set(DoubleSolenoid.Value.kForward);
    }
    
    public void pusherIn(){
    	pusher.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void pivotUp(double speed){
    	System.out.println("Pivot Up Speed" + speed);
    	pivotA.set(-speed); 
    	pivotB.set(-speed);
    }
    
    public void pivotDown(double speed){
    	System.out.println("Pivot Down Speed" + speed);
    	pivotA.set(speed);
    	pivotB.set(speed);
    }
    
    public void stopCollector() {
    	collectRight.set(0);
    	collectLeft.set(0);
    }
    
    public void stopPivot(){
    	pivotA.set(0);
    	pivotB.set(0);
    }
}
