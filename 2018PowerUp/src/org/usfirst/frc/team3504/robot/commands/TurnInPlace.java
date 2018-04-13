package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnInPlace extends Command {
	
	private double encoderTicks;
	private static final int ERROR_THRESHOLD = 5;
	
	private boolean leftGood;
	private boolean rightGood;
	private boolean targetReached = false;

	private double headingTarget;
	private double speed;
	private double currentPos;
	private double error;
	private double errorLast = 0;
	private double dError;
	private double iError = 0;
	private double tempError;
	
	private double kP = .005;
	private double kI = 0;
	private double kD = 0;
	
	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
    public TurnInPlace(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        headingTarget = degrees;
        speed = 0.2;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Trying to initialize");
    	Robot.chassis.setInverted(false);
    	Robot.chassis.zeroSensors();
    	System.out.println("Turn in place initialized Heading = " + headingTarget );
   
    }

   

	// Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	currentPos = Robot.chassis.getYaw();
    	error = (headingTarget - currentPos);
    	dError = ((error - errorLast)/.02);

    	
    	tempError = (iError + (error*.02));
    	if (Math.abs(tempError*kI) < .5){
    		iError = tempError;
    	}
    	System.out.println("current position " + currentPos);
    	
    	leftTalon.set(ControlMode.PercentOutput, ((kP*error)+(kD*dError)+(kI*iError)));
    	rightTalon.set(ControlMode.PercentOutput, ((kP*error)+(kD*dError)+(kI*iError)));

    	if (error <1 && dError <10){
    		targetReached = true;
    	}
    	
    	errorLast = error;
    	
    	

//    	if (headingTarget > 0) {
//    		leftTalon.set(ControlMode.Position, encoderTicks);
//        	rightTalon.set(ControlMode.Position, encoderTicks);
//    	} else {
//    		leftTalon.set(ControlMode.Position, -encoderTicks);
//        	rightTalon.set(ControlMode.Position, -encoderTicks);
//    	}
//    	
//    	System.out.println("Left Error: " + (leftTalon.getSelectedSensorPosition(1) - encoderTicks));
//    	System.out.println("Right Error: " + (rightTalon.getSelectedSensorPosition(1) + encoderTicks));
//
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return targetReached;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("TurnInPlace Finished");
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("TurnInPlace Interrupted");
    	end(); 
    }
}
