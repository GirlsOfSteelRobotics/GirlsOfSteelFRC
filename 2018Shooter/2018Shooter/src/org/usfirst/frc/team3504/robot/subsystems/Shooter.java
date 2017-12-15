package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
import com.ctre.phoenix.MotorControl.CAN.BaseMotorController;
import com.ctre.phoenix.MotorControl.ControlMode;
import com.ctre.phoenix.MotorControl.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	// Speed mode constants TODO: test and change
	private static final int LOW_MAX_RPM = 3400;
	private static final int HIGH_MAX_RPM = 5000;
	private static final int SHOOTER_MIN_SPEED = 1000;
	private static final int SHOOTER_MAX_SPEED = HIGH_MAX_RPM;
	private static final int SHOOTER_SPEED_STEP = 100;
	private static final int SHOOTER_DEFAULT_SPEED = SHOOTER_MAX_SPEED;
	private static final int CONVEYOR_SPEED = 666; //TODO: this is a bogus number, test
	private static final int FEEDER_SPEED = 666; //TODO: also bogus
	private int shooterSpeed = SHOOTER_DEFAULT_SPEED;
		
	private TalonSRX conveyorA, conveyorB, feeder, shooterLowA, shooterLowB, shooterHighA, shooterHighB;

    public Shooter() {
    	conveyorA = new TalonSRX(RobotMap.CONVEYOR_A);
		conveyorB = new TalonSRX(RobotMap.CONVEYOR_B);
		feeder = new TalonSRX(RobotMap.FEEDER);
		shooterLowA = new TalonSRX(RobotMap.SHOOTER_LOW_A);
		//shooterLowB = new TalonSRX(RobotMap.SHOOTER_LOW_B);
		shooterHighA = new TalonSRX(RobotMap.SHOOTER_HIGH_A);
		//shooterHighB = new TalonSRX(RobotMap.SHOOTER_HIGH_B);
    		
		//setting followers
		conveyorB.set(ControlMode.Follower, conveyorA.getDeviceID());
		shooterLowB.set(ControlMode.Follower, shooterLowA.getDeviceID());
		shooterHighB.set(ControlMode.Follower, shooterHighA.getDeviceID());

		
//		conveyorA.enableBrakeMode(false);
//		feeder.enableBrakeMode(false);
//		shooterLowA.enableBrakeMode(false);
//		shooterHighA.enableBrakeMode(false);
		
		setupEncoder(conveyorA);
		setupEncoder(feeder);
		setupEncoder(shooterLowA);
		setupEncoder(shooterHighA);
		
//		LiveWindow.addActuator("Shooter", "low", lowShooterMotor);
//		LiveWindow.addActuator("Shooter", "high", highShooterMotor);

		// PID Values
		shooterLowA.config_kF(0, 0.04407, 0);
		shooterLowA.config_kP(0, 0.01, 0);
		shooterLowA.config_kI(0, 0.0, 0);
		shooterLowA.config_kD(0, 0.0, 0);

		// PID Values
		shooterHighA.config_kF(0, 0.02997, 0);
		shooterHighA.config_kP(0, 0.01, 0);
		shooterHighA.config_kI(0, 0.0, 0);
		shooterHighA.config_kD(0, 0.0, 0);

//		LiveWindow.addActuator("Shooter", "lowShooterMotor", lowShooterMotor);
//		LiveWindow.addActuator("Shooter", "highShooterMotor", highShooterMotor);
    }
    
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void runHighShooterMotor() {
		shooterHighA.set(ControlMode.Velocity, shooterSpeed);
	}

	public void runLowShooterMotor() {
		shooterLowA.set(ControlMode.Velocity, shooterSpeed * ((double) LOW_MAX_RPM / (double) HIGH_MAX_RPM));
	}
	
	public void runFeeder() {
		feeder.set(ControlMode.Velocity, FEEDER_SPEED);
	}
	
	public void runConveyor() {
		conveyorA.set(ControlMode.Velocity, CONVEYOR_SPEED);
	}

	public void stopShooterMotors() {
		conveyorA.set(ControlMode.Velocity, 0);
		feeder.set(ControlMode.Velocity, 0);
		shooterLowA.set(ControlMode.Velocity, 0);
		shooterHighA.set(ControlMode.Velocity, 0);
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

	public void setupEncoder(TalonSRX talon) {
		// Set Encoder Types
		//talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.setSensorPhase(false);
	}

	public int getHighShooterSpeed() {
		return shooterHighA.getSelectedSensorVelocity();
	}

	public int getLowShooterSpeed() {
		return shooterLowA.getSelectedSensorVelocity();
	}
}

