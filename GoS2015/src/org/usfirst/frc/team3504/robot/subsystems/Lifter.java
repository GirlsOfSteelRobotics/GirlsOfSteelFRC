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
	
	private CANTalon liftTalon;
	
	private DigitalInput liftTopLimit; 
	private DigitalInput liftBottomLimit;
	
	private double initEncoder; 
	
	public static final double DISTANCE_ZERO_TOTES=100;
	public static final double DISTANCE_ONE_TOTE=10453;
	public static final double DISTANCE_TWO_TOTES=20906;
	public static final double DISTANCE_THREE_TOTES=31359;
	public static final double DISTANCE_FOUR_TOTES=41812;
	
	public Lifter() {
		liftTalon = new CANTalon(RobotMap.FORKLIFT_CHANNEL_A);
		liftTopLimit = new DigitalInput(RobotMap.FORKLIFT_TOP_LIMIT);
		liftBottomLimit = new DigitalInput(RobotMap.FORKLIFT_BOTTOM_LIMIT);
		
		SmartDashboard.putNumber("P value", 1);
		SmartDashboard.putNumber("I value", 0.01);
		SmartDashboard.putNumber("D value", 20);
		
		liftTalon.changeControlMode(ControlMode.Position);
		liftTalon.setPID(SmartDashboard.getNumber("P value"),
						  SmartDashboard.getNumber("I value"),
						  SmartDashboard.getNumber("D value"),
						  0, 0, 0, 0);
	}
	
	public void tunePID()
	{
		liftTalon.setPID(SmartDashboard.getNumber("P value"),
				  SmartDashboard.getNumber("I value"),
				  SmartDashboard.getNumber("D value"),
				  0, 0, 0, 0);
		liftTalon.set(5); //Number of encoder ticks
	}
	
	public void setPosition(double distance) {
		liftTalon.set(distance);
	}
	public boolean isAtPosition()
	{
		return Math.abs(liftTalon.get()-liftTalon.getSetpoint()) <= .01*liftTalon.getSetpoint();
	}
	
	public double getLiftTravel() {
		return Math.abs(liftTalon.getEncPosition() - initEncoder); 
	}
	
	public void zeroLiftTravel(){
		initEncoder = liftTalon.getEncPosition(); 
	}
	
	public void stop()
	{
		liftTalon.set(liftTalon.get());
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
