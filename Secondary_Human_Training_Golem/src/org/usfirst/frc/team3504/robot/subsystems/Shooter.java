package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	private CANTalon lowShooterMotor;
	private CANTalon highShooterMotor; 
	
	private double encOffsetValueHigh = 0;
	private double encOffsetValueLow = 0;
	
	private static final double shooterMinSpeed = -0.5;
	private static final double shooterMaxSpeed = -1.0;
	private static final double shooterDefaultSpeed = shooterMaxSpeed;
	private static final double shooterSpeedStep = -0.05; //percentage up/down per press
	private static final double shooterDefaultSpeedGear = -1.0 - (5.5 * -0.05);
	private static final double shooterDefaultSpeedKey = -1.0 - (8.0 * -0.05); 
	private double shooterSpeed = shooterDefaultSpeed; //starting speed
	//remember to add when released to reset current shooter increment 

	public Shooter(){
		lowShooterMotor = new CANTalon(RobotMap.LOW_SHOOTER_MOTOR);
		highShooterMotor = new CANTalon(RobotMap.HIGH_SHOOTER_MOTOR); 
	}

	public void shootBall(){
		lowShooterMotor.set(-shooterSpeed);
		highShooterMotor.set(shooterSpeed);
	}

	public void stopShoot(){
		lowShooterMotor.set(0);
		highShooterMotor.set(0);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public void incrementHighShooterSpeed() {
		if (Math.abs(shooterSpeed + shooterSpeedStep) - Math.abs(shooterMaxSpeed) < 0.00001) 
			shooterSpeed = shooterSpeed + shooterSpeedStep;
		System.out.println("currentShooterSpeed: " + shooterSpeed);
	}
	
	public void decrementHighShooterSpeed() {
		if (Math.abs(shooterSpeed - shooterSpeedStep) - Math.abs(shooterMinSpeed) > -0.00001)
			shooterSpeed = shooterSpeed - shooterSpeedStep;
		System.out.println("currentShooterSpeed: " + shooterSpeed);
	}
	
	public void resetHighShooterSpeed() {
		shooterSpeed = shooterDefaultSpeed;
		System.out.println("currentShooterSpeed has reset to: " + shooterSpeed);
	}
	
	public void setSpeedFromGear(){
		shooterSpeed = shooterDefaultSpeedGear; 
	}
	
	public void setSpeedFromKey(){
		shooterSpeed = shooterDefaultSpeedKey; 
	}


}
