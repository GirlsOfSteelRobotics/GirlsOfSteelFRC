package org.usfirst.frc3504.shifterbot.subsystems;

import org.usfirst.frc3504.shifterbot.RobotMap;
import org.usfirst.frc3504.shifterbot.commands.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSystem extends Subsystem {
	SpeedController driveLeft = RobotMap.driveSystemDriveLeft0;
	SpeedController driveRight = RobotMap.driveSystemDriveRight0;
	RobotDrive robotDrive2 = RobotMap.driveSystemRobotDrive2;
//	Encoder driveLeftEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER_A, RobotMap.DRIVE_LEFT_ENCODER_B, true, CounterBase.EncodingType.k2X);
//	Encoder driveRightEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER_A, RobotMap.DRIVE_RIGHT_ENCODER_B, true, CounterBase.EncodingType.k2X);
	
	private double pulsePerRevolution = 360;  //correct
    private double distancePerPulse = 1.0 / pulsePerRevolution;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
    public DriveSystem(){
    
    /*	
    	rightFrontWheel = new CANTalon(RobotMap.DRIVE_RIGHT_ENCODER_A);
  		leftFrontWheel = new CANTalon(RobotMap.DRIVE_LEFT_ENCODER_A);
  		rightBackWheel = new CANTalon(RobotMap.DRIVE_RIGHT_ENCODER_B);
  		leftBackWheel = new CANTalon(RobotMap.DRIVE_LEFT_ENCODER_B);
  		
  		
  	    gosDrive = new RobotDrive(leftBackWheel, rightBackWheel, leftFrontWheel, rightFrontWheel);
  	    
          gosDrive.setInvertedMotor(MotorType.kFrontRight, true);	// invert the left side motors
      	gosDrive.setInvertedMotor(MotorType.kRearRight, true);		// may need to change or remove this to match robot
      	gosDrive.setExpiration(0.1);
      	gosDrive.setSafetyEnabled(true);
      	**/
/**
    	leftBackWheel.setPID(Kp, Ki, Kd);
    	rightBackWheel.setPID(Kp, Ki, Kd);
    	leftFrontWheel.setPID(Kp, Ki, Kd);
    	rightFrontWheel.setPID(Kp, Ki, Kd);
    	**/
    }
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
	

	    public double getEncoderDistance() {
	        return RobotMap.driveSystemDriveLeft0.getEncPosition();
	        
	    }

	    public double getRateEncoder() {
	        return RobotMap.driveSystemDriveLeft0.getEncVelocity();
	    }

	    public double getEncoderLeft() {
	        return RobotMap.driveSystemDriveLeft0.getEncPosition();
	    }
	    
	    public double getEncoderRight() {
	        return RobotMap.driveSystemDriveRight0.getEncPosition();
	    }

	  /*  public double getRaw() {
	        return RobotMap.driveSystemDriveLeft0.getRaw();
	    }
	    
	    public void resetEncoders() {
	        RobotMap.driveSystemDriveLeft0.setPosition(0);
	        RobotMap.driveSystemDriveRight0.setPosition(0);
	    }
*/
}
