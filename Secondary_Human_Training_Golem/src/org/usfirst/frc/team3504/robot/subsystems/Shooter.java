package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	private CANTalon lowShooterMotor;
	private CANTalon highShooterMotor; 
	
	private double encOffsetValueHigh = 0;
	private double encOffsetValueLow = 0;
	
	/*
	private static final double shooterMinSpeed = -0.5;
	private static final double shooterMaxSpeed = -1.0;
	private static final double shooterDefaultSpeed = shooterMaxSpeed;
	private static final double shooterSpeedStep = -0.05; //percentage up/down per press
	private static final double shooterDefaultSpeedGear = -1.0 - (5.5 * -0.05);
	private static final double shooterDefaultSpeedKey = -1.0 - (8.0 * -0.05); 
	private double shooterSpeed = shooterDefaultSpeed; //starting speed
	*/
	//remember to add when released to reset current shooter increment 
	
	//Speed mode constants //TODO: test and change
	private static final int SHOOTER_MIN_SPEED = 100;
	private static final int SHOOTER_MAX_SPEED = 200;
	private static final int SHOOTER_SPEED_STEP = 25;
	public static final int SHOOTER_DEFAULT_SPEED = SHOOTER_MAX_SPEED;
	public static final int SHOOTER_SPEED_GEAR = 180;
	public static final int SHOOTER_SPEED_KEY = 120;
	private static final int MAX_SHOOTER_ERROR = 5;
	private int shooterSpeed = SHOOTER_DEFAULT_SPEED;
	private boolean lowMotorRunning = false;

	public Shooter(){
		lowShooterMotor = new CANTalon(RobotMap.LOW_SHOOTER_MOTOR);
		highShooterMotor = new CANTalon(RobotMap.HIGH_SHOOTER_MOTOR); 
		
		//lowShooterMotor.changeControlMode(TalonControlMode.Speed);
		//highShooterMotor.changeControlMode(TalonControlMode.Speed);
		lowShooterMotor.changeControlMode(TalonControlMode.PercentVbus);
		highShooterMotor.changeControlMode(TalonControlMode.PercentVbus);
		setupEncoder(lowShooterMotor);
		setupEncoder(highShooterMotor);
		
		LiveWindow.addActuator("Shooter", "low", lowShooterMotor);
		LiveWindow.addActuator("Shooter", "high", highShooterMotor);
	}

	public void runHighShooterMotor(){
		//highShooterMotor.set(shooterSpeed);
		highShooterMotor.set(1);
	}
	
	public void runLowShooterMotor(){
		/*if (lowMotorRunning){
			lowShooterMotor.set(shooterSpeed);
		}*/
		lowShooterMotor.set(1);
	}
	
	public void startLowShooterMotor(){
		lowMotorRunning = true;
	}
	
	public void stopLowShooterMotor(){
		lowMotorRunning = false;
	}

	public void shootBall(){
		highShooterMotor.set(shooterSpeed);
		lowShooterMotor.set(shooterSpeed);
	}
	
	public boolean isHighShooterAtSpeed(){
		return (highShooterMotor.getClosedLoopError() < MAX_SHOOTER_ERROR);
	}

	public void stopShooterMotors(){
		lowShooterMotor.set(0);
		highShooterMotor.set(0);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public void incrementHighShooterSpeed() {
		if ((shooterSpeed + SHOOTER_SPEED_STEP) <= SHOOTER_MAX_SPEED) 
			shooterSpeed = shooterSpeed + SHOOTER_SPEED_STEP;
		System.out.println("currentShooterSpeed: " + shooterSpeed);
	}
	
	public void decrementHighShooterSpeed() {
		if ((shooterSpeed - SHOOTER_SPEED_STEP) >= SHOOTER_MIN_SPEED)
			shooterSpeed = shooterSpeed - SHOOTER_SPEED_STEP;
		System.out.println("currentShooterSpeed: " + shooterSpeed);
	}
	
	public void setShooterSpeed(int speed) {
		shooterSpeed = speed;
		System.out.println("currentShooterSpeed has reset to: " + shooterSpeed);
	}
	
	
	public void setupEncoder(CANTalon talon){ //call on both talons
		//Set Encoder Types
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		talon.reverseSensor(false);
    	
    	//PID Values TODO:tune these
    	talon.setF(0);
    	talon.setP(0.5);
    	talon.setI(0.0); 
    	talon.setD(0.0);
	}


}
