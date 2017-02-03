package org.usfirst.frc.team3504.robot.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team3504.robot.Robot;
import com.ctre.CANTalon.TalonControlMode;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionProfile extends Command {

	public ArrayList<ArrayList<Double>> leftPoints;
	public ArrayList<ArrayList<Double>> rightPoints;
	public CANTalon leftTalon = Robot.chassis.driveLeftA;
	public CANTalon rightTalon = Robot.chassis.driveRightA;
	private CANTalon.MotionProfileStatus leftStatus;
	private CANTalon.MotionProfileStatus rightStatus;
	private static final int kMinPointsInTalon = 5;
	
	Notifier notifier = new Notifier(new PeriodicRunnable());
	
    public DriveByMotionProfile(String leftFile, String rightFile) {
    	requires(Robot.chassis);
    	
    	//Load trajectory from file into array
    	try {
    	leftPoints = loadMotionProfile(leftFile);
    	rightPoints = loadMotionProfile(rightFile);
    	System.out.println("DriveByMotion: Loaded File");
    	} 
    	catch (FileNotFoundException ex){
    		System.err.println("File Not Found: Motion Profile Trajectories");
    	}
    	
    	//Initialize status variables
    	leftStatus = new CANTalon.MotionProfileStatus();
    	rightStatus = new CANTalon.MotionProfileStatus();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//PID Values
		leftTalon.setF(3);
		leftTalon.setP(0);
		leftTalon.setI(0);
		leftTalon.setD(0);
		rightTalon.setF(3);
		rightTalon.setP(0);
		rightTalon.setI(0);
		rightTalon.setD(0);
		
    	//Set Talon to MP mode
    	leftTalon.changeControlMode(TalonControlMode.MotionProfile);
    	rightTalon.changeControlMode(TalonControlMode.MotionProfile);
    	System.out.println("DriveByMotion: Change Talon to MP Mode");
    	
    	//Disable MP
    	leftTalon.set(CANTalon.SetValueMotionProfile.Disable.value);
    	rightTalon.set(CANTalon.SetValueMotionProfile.Disable.value);
    	System.out.println("DriveByMotion: Disable MP Mode");
    	
    	//Push Trajectory
    	pushTrajectory(leftTalon, leftPoints);
    	pushTrajectory(rightTalon, rightPoints);
    	System.out.println("DriveByMotion: Push Trajectory");
    	
    	//Start Periodic Notifier
    	leftTalon.changeMotionControlFramePeriod(5);
    	rightTalon.changeMotionControlFramePeriod(5);
		notifier.startPeriodic(0.005);
		System.out.println("DriveByMotion: Start Periodic");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("DriveByMotion: Run Execute");
    	//get MP status from each talon
    	leftTalon.getMotionProfileStatus(leftStatus);
    	rightTalon.getMotionProfileStatus(rightStatus);
    	
    	//Enable MP if not already enabled
    	if(leftStatus.outputEnable != CANTalon.SetValueMotionProfile.Enable){
    		if (leftStatus.btmBufferCnt > kMinPointsInTalon){
    			leftTalon.set(CANTalon.SetValueMotionProfile.Enable.value);
    			System.out.println("DriveByMotion: Enabled left talon");
    		}
    			
    		
    	}
    	
    	if(rightStatus.outputEnable != CANTalon.SetValueMotionProfile.Enable){
    		if (rightStatus.btmBufferCnt > kMinPointsInTalon){
    			rightTalon.set(CANTalon.SetValueMotionProfile.Enable.value);
    			System.out.println("DriveByMotion: Enabled right talon");
    		}
    			
    	}
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean left = (leftStatus.activePointValid && leftStatus.activePoint.isLastPoint);
    	boolean right = (rightStatus.activePointValid && rightStatus.activePoint.isLastPoint);
    	
    	if(left && right){
    		leftTalon.set(CANTalon.SetValueMotionProfile.Disable.value);
        	rightTalon.set(CANTalon.SetValueMotionProfile.Disable.value);
        	System.out.println("DriveByMotion: Finished");
    	}
    	
        return (left && right);
    }

    // Called once after isFinished returns true
    protected void end() {
    	notifier.stop();
    	
    	leftTalon.clearMotionProfileTrajectories();
    	rightTalon.clearMotionProfileTrajectories();
		
    	leftTalon.set(CANTalon.SetValueMotionProfile.Disable.value);
    	rightTalon.set(CANTalon.SetValueMotionProfile.Disable.value);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private ArrayList<ArrayList<Double>> loadMotionProfile(String filename) throws FileNotFoundException {
    	ArrayList<ArrayList<Double>> points = new ArrayList<ArrayList<Double>>();
		InputStream is = new FileInputStream(filename);
		Scanner s = new Scanner(is);
		while(s.hasNext())
		{
			ArrayList<Double> arr = new ArrayList<Double>();
			arr.add(s.nextDouble()); //p
			arr.add(s.nextDouble()); //v
			arr.add(s.nextDouble()); //d
			
			points.add(arr);
		}
		s.close();
		return points;
	}
    
    private void pushTrajectory(CANTalon _talon, ArrayList<ArrayList<Double>> points) {
    	//**************handle Underrun
    	
		/* create an empty point */
		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		_talon.clearMotionProfileTrajectories();

		/* This is fast since it's just into our TOP buffer */
		int i = 0;
		for (ArrayList<Double> arr:points) {
			/* for each point, fill our structure and pass it to API */
			//Double[] a = (Double[]) arr.toArray();
			point.position = arr.get(0);
			point.velocity = arr.get(1);
			point.timeDurMs = arr.get(2).intValue();
			point.profileSlotSelect = 0; /* which set of gains would you like to use? */
			point.velocityOnly = false; /* set true to not do any position
										 * servo, just velocity feedforward
										 */
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == points.size())
				point.isLastPoint = true; /* set this to true on the last point  */

			_talon.pushMotionProfileTrajectory(point);
			i++;
		}
    }
    
    class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {  
	    	leftTalon.processMotionProfileBuffer();
	    	rightTalon.processMotionProfileBuffer();
	    }
	}
}
