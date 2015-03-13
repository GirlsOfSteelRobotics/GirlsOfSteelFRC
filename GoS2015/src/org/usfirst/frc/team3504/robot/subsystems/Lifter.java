package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author Arushi
 */
public class Lifter extends Subsystem {
	
	private CANTalon liftTalonA;
	//private CANTalon liftTalonB;
	
	private DigitalInput liftTopLimit;
	private DigitalInput liftBottomLimit;
	
	private double initEncoder; 
	
	public Lifter() {
		liftTalonA = new CANTalon(RobotMap.FORKLIFT_CHANNEL_A);
		//liftTalonB = new CANTalon(RobotMap.FORKLIFT_CHANNEL_B);
		liftTopLimit = new DigitalInput(RobotMap.FORKLIFT_TOP_LIMIT);
		liftBottomLimit = new DigitalInput(RobotMap.FORKLIFT_BOTTOM_LIMIT);
		
		SmartDashboard.putNumber("P value", 1);
		SmartDashboard.putNumber("I value", 0.01);
		SmartDashboard.putNumber("D value", 20);
		
		liftTalonA.changeControlMode(ControlMode.Position);
		liftTalonA.setPID(SmartDashboard.getNumber("P value"),
						  SmartDashboard.getNumber("I value"),
						  SmartDashboard.getNumber("D value"),
						  0, 0, 0, 0);
	}
	
	public void tunePID()
	{
		liftTalonA.setPID(SmartDashboard.getNumber("P value"),
				  SmartDashboard.getNumber("I value"),
				  SmartDashboard.getNumber("D value"),
				  0, 0, 0, 0);
		liftTalonA.set(5); //Number of encoder ticks
	}
	
	public void up(double speed) {
		liftTalonA.set(speed);
		//liftTalonB.set(speed);
	}
	
	public void down(double speed) {
		liftTalonA.set(-speed);
		//liftTalonB.set(speed);
	}
	
	public double getLiftTravel() {
		return Math.abs(liftTalonA.getEncPosition() - initEncoder); 
	}
	
	public void zeroLiftTravel(){
		initEncoder = liftTalonA.getEncPosition(); 
	}
	
	public void stop()
	{
		liftTalonA.set(0.0);
		//liftTalonB.set(0.0);
	}
	
	public boolean isAtTop()
	{
		return liftTopLimit.get();
	}
	
	public boolean isAtBottom()
	{
		return liftBottomLimit.get();
	}
	
	@Override
	protected void initDefaultCommand() {
	}
}
