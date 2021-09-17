package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.CANTalon;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.MotorControl.SmartMotorController.FeedbackDevice;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Feeder extends Subsystem {
	// Speed mode constants TODO: test and change
	private static final int CONVEYOR_SPEED = 666;
	private static final int FEEDER_SPEED = 600; 
	
	private CANTalon conveyorA, feeder;
	//private TalonSRX shooterLowB, shooterHighB;

    public Feeder() {
    	conveyorA = new CANTalon(RobotMap.CONVEYOR_A);
    	feeder = new CANTalon(RobotMap.FEEDER);
		
		//setting followers
		//shooterLowB.set(ControlMode.Follower, shooterLowA.getDeviceID());
		//shooterHighB.set(ControlMode.Follower, shooterHighA.getDeviceID());

//		conveyorA.enableBrakeMode(false);
//		feeder.enableBrakeMode(false);
//		shooterLowA.enableBrakeMode(false);
//		shooterHighA.enableBrakeMode(false);
		
		setupEncoder(conveyorA);
		setupEncoder(feeder);
		
//		LiveWindow.addActuator("Feeder", "low", lowShooterMotor);
//		LiveWindow.addActuator("Feeder", "high", highShooterMotor);

//		LiveWindow.addActuator("Feeder", "lowShooterMotor", lowShooterMotor);
//		LiveWindow.addActuator("Feeder", "highShooterMotor", highShooterMotor);
    }
    
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void runFeeder() {
		feeder.set(-1.0);
	}
    
    public void runConveyor() {
    	conveyorA.set(1.0);
    }
    
    public void stopFeederMotors() {
    	feeder.set(0);
    	conveyorA.set(0); 
    }
    
	public void setupEncoder(TalonSRX talon) {
		// Set Encoder Types
		//talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//talon.setSensorPhase(false);
	}

	
}

