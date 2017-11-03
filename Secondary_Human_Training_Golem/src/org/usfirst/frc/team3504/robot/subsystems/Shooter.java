package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	private TalonSRX lowShooterMotor;
	private TalonSRX highShooterMotor;
	private TalonSRX loaderMotor; 
	
	private double encOffsetValueHigh = 0;
	private double encOffsetValueLow = 0;
	

	public Shooter(){
		lowShooterMotor = new TalonSRX(RobotMap.LOW_SHOOTER_MOTOR);
		highShooterMotor = new TalonSRX(RobotMap.HIGH_SHOOTER_MOTOR);
		loaderMotor = new TalonSRX(RobotMap.LOADER_MOTOR); 
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
