package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionMagicAbsolute extends Command {
	
	private double encoderTicks;
	private double targetHeading;
	
	private boolean turning;
	
	private int timeoutCtr;
	private double time;

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
	private final static double DISTANCE_FINISH_THRESHOLD = 4000; //TODO tune (in encoder ticks)
	private final static double TURNING_FINISH_THRESHOLD = 1.5; //TODO tune (in degrees)
	
	private final static double DISTANCE_TIMER_THRESHOLD = 10000; //TODO tune (in encoder ticks)
	private final static double TURNING_TIMER_THRESHOLD = 8.0; //TODO tune (in degrees)
	
	private final static double TIMER_THRESHOLD = 0.5; //in seconds

    public DriveByMotionMagicAbsolute(double inches, double absoluteDegrees, boolean isTurnMotion) {
		encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
		targetHeading = absoluteDegrees;
		turning = isTurnMotion;
		requires(Robot.chassis);
		//System.out.println("DriveByMotionMagicAbsolute: constructed");
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	time = 0;
    	Robot.chassis.setInverted(true);
    	//System.out.println("DriveByMotionMagicAbsolute: motors inverted");
    	
    	Robot.chassis.configForMotionMagic();
    	//System.out.println("DriveByMotionMagicAbsolute: configured for motion magic");
    	
    	Robot.chassis.zeroEncoder();
    	//System.out.println("DriveByMotionMagicAbsolute: sensors zeroed");
    	
    	double inches = (encoderTicks / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
    	System.out.println("DriveByMotionMagicAbsolute inches + heading: " + inches + targetHeading);
    	
		rightTalon.set(ControlMode.MotionMagic, 2 * encoderTicks, DemandType.AuxPID, 10 * targetHeading);
		leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
		
		System.out.println("DriveByMotionMagicAbsolute: running...");
		
		timeoutCtr = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	time+=0.02;
    	if (!turning) //if trying to drive straight
    	{
    		double currentTicks = rightTalon.getSensorCollection().getQuadraturePosition();
    		double error = Math.abs(encoderTicks - currentTicks);
    		if (error < DISTANCE_TIMER_THRESHOLD) timeoutCtr++;
    	}
    	else //if trying to turn to an angle
    	{
    		double currentHeading = Robot.chassis.getYaw();
    		double error = Math.abs(targetHeading - currentHeading);
    		//System.out.println("DriveByMotionMagicAbsolute: turning error = " + error);
    		if (error < TURNING_TIMER_THRESHOLD) timeoutCtr++;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	if (timeoutCtr > (TIMER_THRESHOLD * 50))
		{
    		System.out.println("DriveByMotionMagicAbsolute: timeout reached");
    		return true;
		}
    	else if (!turning) //if trying to drive straight
    	{
    		double currentTicks = rightTalon.getSensorCollection().getQuadraturePosition();
    		double error = Math.abs(encoderTicks - currentTicks);
    		//System.out.println("DriveByMotionMagicAbsolute: distance error = " + error);
    		if (error < DISTANCE_FINISH_THRESHOLD)
    		{
    			System.out.println("DriveByMotionMagicAbsolute: encoder ticks reached");
        		return true;
    		}
    		else return false;
    	}
    	else //if trying to turn to an angle
    	{
    		double currentHeading = Robot.chassis.getYaw();
    		double error = Math.abs(targetHeading - currentHeading);
    		//System.out.println("DriveByMotionMagicAbsolute: turning error = " + error);
    		if (error < TURNING_FINISH_THRESHOLD)
    		{
    			System.out.println("DriveByMotionMagicAbsolute: turning degrees reached");
        		return true;
    		}
    		else return false;
    	}
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	double currentTicks = rightTalon.getSensorCollection().getQuadraturePosition();
		double ticksError = Math.abs(encoderTicks - currentTicks);
		double inches = (ticksError / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
		double currentHeading = Robot.chassis.getYaw();
		double degreesError = Math.abs(targetHeading - currentHeading);
    	
    	System.out.println("DriveByMotionMagicAbsolute: ended. Error = " + inches/2 + " inches, " + degreesError + " degrees, " + time + " seconds");
    	Robot.chassis.stop();
    	Robot.chassis.setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("DriveByMotionMagicAbsolute: interrupted");
    	end();
    }
}
