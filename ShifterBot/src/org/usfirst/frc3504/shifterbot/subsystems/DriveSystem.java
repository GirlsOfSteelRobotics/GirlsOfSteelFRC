package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
import org.usfirst.frc3504.shifterbot.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSystem extends Subsystem {
	SpeedController driveLeft = RobotMap.driveSystemDriveLeft;
	SpeedController driveRight = RobotMap.driveSystemDriveRight;
	RobotDrive robotDrive2 = RobotMap.driveSystemRobotDrive2;
	Encoder driveLeftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER_A, RobotMap.DRIVE_LEFT_ENCODER_B, true, CounterBase.EncodingType.k2X);
	Encoder driveRightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER_A, RobotMap.DRIVE_RIGHT_ENCODER_B, true, CounterBase.EncodingType.k2X);
	
	private double pulsePerRevolution = 360;  //correct
    private double distancePerPulse = 1.0 / pulsePerRevolution;
    
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new DriveByJoystick());

		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void takeJoystickInputs(Joystick joystk) {
		robotDrive2.arcadeDrive(joystk);
	}

	public void forward(){
		robotDrive2.drive(1.0, 0);
	}
	
	public void stop() {
		robotDrive2.drive(/*speed*/0, /*curve*/0);
	}
	
	  public void initEncoders() {
	        driveLeftEncoder.setDistancePerPulse(distancePerPulse);
	    }

	    public double getEncoderDistance() {
	        return driveLeftEncoder.getDistance();
	    }

	    public double getRateEncoder() {
	        return driveLeftEncoder.getRate();
	    }

	    public double getEncoderLeft() {
	        return driveLeftEncoder.get();
	    }
	    
	    public double getEncoderRight() {
	        return driveRightEncoder.get();
	    }

	    public double getRaw() {
	        return driveLeftEncoder.getRaw();
	    }
	    
	    public void resetEncoders() {
	        driveLeftEncoder.reset();
	        driveRightEncoder.reset();
	    }

}
