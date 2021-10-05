package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private WPI_TalonSRX lowShooterMotor;
	private WPI_TalonSRX highShooterMotor;

	/*
	 * private static final double shooterMinSpeed = -0.5; private static final
	 * double shooterMaxSpeed = -1.0; private static final double
	 * shooterDefaultSpeed = shooterMaxSpeed; private static final double
	 * shooterSpeedStep = -0.05; //percentage up/down per press private  static
	 * final double shooterDefaultSpeedGear = -1.0 - (5.5 * -0.05); private
	 * static final double shooterDefaultSpeedKey = -1.0 - (8.0 * -0.05);
	 * private double shooterSpeed = shooterDefaultSpeed; //starting speed
	 */
	// remember to add when released to reset current shooter increment

	// Speed mode constants //TODO: test and change
	private static final int LOW_MAX_RPM = 3400;
	private static final int HIGH_MAX_RPM = 5000;
	private static final int SHOOTER_MIN_SPEED = 1000;
	private static final int SHOOTER_MAX_SPEED = HIGH_MAX_RPM;
	private static final int SHOOTER_SPEED_STEP = 100;
	public static final int SHOOTER_DEFAULT_SPEED = SHOOTER_MAX_SPEED;
	public static final int SHOOTER_SPEED_GEAR = 3700;
	public static final int SHOOTER_SPEED_KEY = 3250;
	public static final int AUTO_SHOOTER_SPEED_KEY = 3333; 
	private static final double MAX_SHOOTER_ERROR = 0.05;

	private int shooterSpeed = SHOOTER_DEFAULT_SPEED;
	private boolean lowMotorRunning = false;

	public Shooter() {
		lowShooterMotor = new WPI_TalonSRX(RobotMap.LOW_SHOOTER_MOTOR);
		highShooterMotor = new WPI_TalonSRX(RobotMap.HIGH_SHOOTER_MOTOR);

		lowShooterMotor.setNeutralMode(NeutralMode.Brake);
		highShooterMotor.setNeutralMode(NeutralMode.Brake);

		setupEncoder(lowShooterMotor);
		setupEncoder(highShooterMotor);

//		LiveWindow.addActuator("Shooter", "low", lowShooterMotor);
//		LiveWindow.addActuator("Shooter", "high", highShooterMotor);

		// PID Values
		lowShooterMotor.config_kF(0, 0.04407, 0); //see p 17 of motion profile manual
		lowShooterMotor.config_kP(0, 0.01, 0);
		lowShooterMotor.config_kI(0, 0, 0);
		lowShooterMotor.config_kD(0, 0, 0);
		
		// PID Values
		highShooterMotor.config_kF(0, 0.02997, 0); //see p 17 of motion profile manual
		highShooterMotor.config_kP(0, 0.01, 0);
		highShooterMotor.config_kI(0, 0, 0);
		highShooterMotor.config_kD(0, 0, 0);


//		LiveWindow.addActuator("Shooter", "lowShooterMotor", lowShooterMotor);
//		LiveWindow.addActuator("Shooter", "highShooterMotor", highShooterMotor);
	}

	public void runHighShooterMotor() {
		highShooterMotor.set(ControlMode.Velocity, shooterSpeed);
	}

	public void runLowShooterMotor() {
		if (lowMotorRunning) {
			lowShooterMotor.set(ControlMode.Velocity, shooterSpeed * ((double) LOW_MAX_RPM / (double) HIGH_MAX_RPM));
		}
	}

	public void startLowShooterMotor() {
		lowMotorRunning = true;
	}

	public void stopLowShooterMotor() {
		lowMotorRunning = false;
	}

	public boolean isHighShooterAtSpeed() { // TODO: This is broken, always
											// returning true
		return ((double) highShooterMotor.getClosedLoopError(0) / (double) shooterSpeed) < MAX_SHOOTER_ERROR;
	}

	public void stopShooterMotors() {
		lowShooterMotor.set(ControlMode.Velocity, 0);
		highShooterMotor.set(ControlMode.Velocity, 0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void incrementHighShooterSpeed() {
		if ((shooterSpeed + SHOOTER_SPEED_STEP) <= SHOOTER_MAX_SPEED)
			shooterSpeed += SHOOTER_SPEED_STEP;
		System.out.println("currentShooterSpeed: " + shooterSpeed);
	}

	public void decrementHighShooterSpeed() {
		if ((shooterSpeed - SHOOTER_SPEED_STEP) >= SHOOTER_MIN_SPEED)
			shooterSpeed -= SHOOTER_SPEED_STEP;
		System.out.println("currentShooterSpeed: " + shooterSpeed);
	}

	public void setShooterSpeed(int speed) {
		shooterSpeed = speed;
		System.out.println("currentShooterSpeed has reset to: " + shooterSpeed);
	}

	public void setupEncoder(WPI_TalonSRX talon) { // call on both talons
		// Set Encoder Types
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		talon.setSensorPhase(false);
	}

	public double getHighShooterSpeed() {
		return highShooterMotor.getSelectedSensorVelocity(0);
	}

	public double getLowShooterSpeed() {
		return lowShooterMotor.getSelectedSensorVelocity(0);
	}

	public boolean isLowShooterMotorRunning() {
		return lowMotorRunning;
	}

}