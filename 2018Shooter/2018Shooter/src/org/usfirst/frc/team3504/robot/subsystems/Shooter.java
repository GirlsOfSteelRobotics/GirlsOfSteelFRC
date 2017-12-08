package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3335.util.CANTalon;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.MotorControl.SmartMotorController.TalonControlMode;

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
		
	private CANTalon conveyorA, conveyorB, feeder, shooterLowA, shooterLowB, shooterHighA, shooterHighB;

    public Shooter() {
    		conveyorA = new CANTalon(RobotMap.CONVEYOR_A);
		conveyorB = new CANTalon(RobotMap.CONVEYOR_B);
		feeder = new CANTalon(RobotMap.FEEDER);
		shooterLowA = new CANTalon(RobotMap.SHOOTER_LOW_A);
		shooterLowB = new CANTalon(RobotMap.SHOOTER_LOW_B);
		shooterHighA = new CANTalon(RobotMap.SHOOTER_HIGH_A);
		shooterHighB = new CANTalon(RobotMap.SHOOTER_HIGH_B);
    		
		//setting followers
		conveyorB.changeControlMode(CANTalon.TalonControlMode.Follower);
		shooterLowB.changeControlMode(CANTalon.TalonControlMode.Follower);
		shooterHighB.changeControlMode(CANTalon.TalonControlMode.Follower);
		conveyorB.set(conveyorA.getDeviceID());
		shooterLowB.set(shooterLowA.getDeviceID());
		shooterHighB.set(shooterHighA.getDeviceID());
		
		//changing control mode
		conveyorA.changeControlMode(TalonControlMode.Speed);
		feeder.changeControlMode(TalonControlMode.Speed);
		shooterLowA.changeControlMode(TalonControlMode.Speed);
		shooterHighA.changeControlMode(TalonControlMode.Speed);
		
		conveyorA.enableBrakeMode(false);
		feeder.enableBrakeMode(false);
		shooterLowA.enableBrakeMode(false);
		shooterHighA.enableBrakeMode(false);
		
		setupEncoder(conveyorA);
		setupEncoder(feeder);
		setupEncoder(shooterLowA);
		setupEncoder(shooterHighA);
		
//		LiveWindow.addActuator("Shooter", "low", lowShooterMotor);
//		LiveWindow.addActuator("Shooter", "high", highShooterMotor);

		// PID Values
		shooterLowA.setF(0.04407); // see p 17 of motion profile manual
										// 0.04407
		// lowShooterMotor.setF(0); //see p 17 of motion profile manual
		shooterLowA.setP(0.01);
		shooterLowA.setI(0.0);
		shooterLowA.setD(0.0);

		// PID Values
		shooterHighA.setF(0.02997); // see p 17 of motion profile manual
										// 0.02997
		// highShooterMotor.setF(0);
		shooterHighA.setP(0.01);
		shooterHighA.setI(0.0);
		shooterHighA.setD(0.0);

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
		shooterHighA.set(shooterSpeed);
	}

	public void runLowShooterMotor() {
		shooterLowA.set(shooterSpeed * ((double) LOW_MAX_RPM / (double) HIGH_MAX_RPM));
	}
	
	public void runFeeder() {
		feeder.set(FEEDER_SPEED);
	}
	
	public void runConveyor() {
		conveyorA.set(CONVEYOR_SPEED);
	}

	public void stopShooterMotors() {
		conveyorA.set(0);
		feeder.set(0);
		shooterLowA.set(0);
		shooterHighA.set(0);
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

	public void setupEncoder(CANTalon talon) {
		// Set Encoder Types
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		talon.reverseSensor(false);
	}

	public int getHighShooterSpeed() {
		return shooterHighA.getEncVelocity();
	}

	public int getLowShooterSpeed() {
		return shooterLowA.getEncVelocity();
	}
}

