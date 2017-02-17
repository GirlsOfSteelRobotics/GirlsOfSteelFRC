package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	private CANTalon lowShooterMotor;
	private CANTalon highShooterMotor;
	private CANTalon loaderMotor; 
	
	private double encOffsetValueHigh = 0;
	private double encOffsetValueLow = 0;
	
	private final double shooterMinSpeed = -0.5;
	private final double shooterMaxSpeed = -1.0;
	private final double shooterSpeedStep = -0.05; //percentage up/down per press
	private final int maxIncrement = 10;
	private int currentShooterIncrement = 10; //starting speed 
	//remember to add whenreleased to reset current shooter incret 

	public Shooter(){
		lowShooterMotor = new CANTalon(RobotMap.LOW_SHOOTER_MOTOR);
		highShooterMotor = new CANTalon(RobotMap.HIGH_SHOOTER_MOTOR);
		loaderMotor = new CANTalon(RobotMap.LOADER_MOTOR); 
	}

	public void shootBall(){
		lowShooterMotor.set(0.5);
		highShooterMotor.set(shooterMinSpeed + shooterSpeedStep * currentShooterIncrement);
	}

	public void stopShoot(){
		lowShooterMotor.set(0);
		highShooterMotor.set(0);
	}

	public void loadBall(double speed){
		loaderMotor.set(speed);
	}
	
	public void stopLoader(){
		loaderMotor.set(0.0); 
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public void incrementHighShooter() {
		currentShooterIncrement = Math.min(currentShooterIncrement + 1, maxIncrement);
		System.out.println(currentShooterIncrement);
	}
	
	public void decrementHighShooter() {
		currentShooterIncrement = Math.max(currentShooterIncrement - 1, 0);
		System.out.println(currentShooterIncrement);
	}
	
	public double getEncoderHigh() {
		double encHigh = highShooterMotor.getEncPosition();
		
		//might not be DISTANCE_PER_PULSE_HIGH_GEAR
		SmartDashboard.putNumber("Shooter Encoder High", (encHigh - encOffsetValueHigh) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
		return (encHigh - encOffsetValueHigh) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
	}
	
	public double getEncoderLow() {
		double encLow = lowShooterMotor.getEncPosition();
		
		//might not be DISTANCE_PER_PULSE_HIGH_GEAR
		SmartDashboard.putNumber("Chassis Encoders Left", (encLow - encOffsetValueLow) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
		return (encLow - encOffsetValueLow) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
	}

	public void resetEncoderDistance() {
		encOffsetValueHigh = getEncoderHigh(); 
		encOffsetValueLow = getEncoderLow();
		getEncoderHigh();
		getEncoderLow();
	}

}
