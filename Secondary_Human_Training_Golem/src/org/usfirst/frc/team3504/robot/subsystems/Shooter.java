package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private CANTalon lowShooterMotor;
	private CANTalon highShooterMotor;
	private CANTalon loaderMotor; 
	

	public Shooter(){
		lowShooterMotor = new CANTalon(RobotMap.LOW_SHOOTER_MOTOR);
		highShooterMotor = new CANTalon(RobotMap.HIGH_SHOOTER_MOTOR);
		loaderMotor = new CANTalon(RobotMap.LOADER_MOTOR); 

	}

	public void shootBall(double lowSpeed, double highSpeed){
		lowShooterMotor.set(lowSpeed);
		highShooterMotor.set(highSpeed);
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

}
