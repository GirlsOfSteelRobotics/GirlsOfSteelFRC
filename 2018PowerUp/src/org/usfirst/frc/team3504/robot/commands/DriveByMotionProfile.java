package org.usfirst.frc.team3504.robot.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motion.*;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile.PeriodicRunnable;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionProfile extends Command {

	public ArrayList<ArrayList<Double>> leftPoints;
	public ArrayList<ArrayList<Double>> rightPoints;
	public WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	public WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	private MotionProfileStatus leftStatus;
	private MotionProfileStatus rightStatus;
	private static final int kMinPointsInTalon = 5;
	private SetValueMotionProfile state;
	private double multiplier = 1.0;

	Notifier notifier = new Notifier(new PeriodicRunnable());

	public DriveByMotionProfile(String leftFile, String rightFile, double multiplier) {
		requires(Robot.chassis);

		this.multiplier = multiplier;
		
		// Load trajectory from file into array
		try {
			leftPoints = loadMotionProfile(leftFile, true);
			rightPoints = loadMotionProfile(rightFile, false);
			System.out.println("DriveByMotion: Loaded File");
		} catch (FileNotFoundException ex) {
			System.err.println("File Not Found: Motion Profile Trajectories");
		}

		// Initialize status variables
		leftStatus = new MotionProfileStatus();
		rightStatus = new MotionProfileStatus();
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		Robot.chassis.setFollowerMode();
		Robot.chassis.setupFPID(leftTalon);
		Robot.chassis.setupFPID(rightTalon);

		// Set Talon to MP mode
		System.out.println("DriveByMotion: Change Talon to MP Mode");

		// Disable MP
		state = SetValueMotionProfile.Disable;
		leftTalon.set(ControlMode.MotionProfile, state.value);
		rightTalon.set(ControlMode.MotionProfile, state.value);
		System.out.println("DriveByMotion: Disable MP Mode");

		// Push Trajectory
		pushTrajectory(leftTalon, leftPoints);
		pushTrajectory(rightTalon, rightPoints);
		System.out.println("DriveByMotion: Push Trajectory");

		// Start Periodic Notifier
		leftTalon.changeMotionControlFramePeriod(5);
		rightTalon.changeMotionControlFramePeriod(5);
		notifier.startPeriodic(0.005);
		System.out.println("DriveByMotion: Start Periodic");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// get MP status from each talon
		leftTalon.getMotionProfileStatus(leftStatus);
		rightTalon.getMotionProfileStatus(rightStatus);

		// Enable MP if not already enabled
		if ((leftStatus.btmBufferCnt > kMinPointsInTalon) && (rightStatus.btmBufferCnt > kMinPointsInTalon)) {
			state = SetValueMotionProfile.Enable;
		}
		leftTalon.set(ControlMode.MotionProfile, state.value);
		rightTalon.set(ControlMode.MotionProfile, state.value);
		// System.out.println("DriveByMotion: Execute Setting State: " + state);
		
		// did we get an underrun condition since last time we checked?
		if (leftStatus.hasUnderrun || rightStatus.hasUnderrun) {
			// better log it so we know about it
			System.out.println("DriveByMotion: A Talon has underrun!!! Left Talon: " + leftStatus.hasUnderrun + " Right Talon: " + rightStatus.hasUnderrun);
			// clear the error. This flag does not auto clear, so this way we never miss logging it.
			leftTalon.clearMotionProfileHasUnderrun(0);
			rightTalon.clearMotionProfileHasUnderrun(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// get MP status from each talon
		leftTalon.getMotionProfileStatus(leftStatus);
		rightTalon.getMotionProfileStatus(rightStatus);

		boolean left = (leftStatus.activePointValid && leftStatus.isLast);
		boolean right = (rightStatus.activePointValid && rightStatus.isLast);
		

		if (left && right) {
			state = SetValueMotionProfile.Disable;
			leftTalon.set(ControlMode.MotionProfile, state.value);
			rightTalon.set(ControlMode.MotionProfile, state.value);
			System.out.println("DriveByMotion: Finished");
		}

		return (left && right);
	}

	// Called once after isFinished returns true
	protected void end() {
		notifier.stop();

		leftTalon.clearMotionProfileTrajectories();
		rightTalon.clearMotionProfileTrajectories();

		leftTalon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
		rightTalon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	private ArrayList<ArrayList<Double>> loadMotionProfile(String filename, boolean isLeft)
			throws FileNotFoundException {
		ArrayList<ArrayList<Double>> points = new ArrayList<ArrayList<Double>>();
		InputStream is = new FileInputStream(filename);
		Scanner s = new Scanner(is);
		while (s.hasNext()) {
			ArrayList<Double> arr = new ArrayList<Double>();
			arr.add(s.nextDouble() * (isLeft ? -1.0 : 1.0)); // p
			arr.add(s.nextDouble() * (isLeft ? -1.0 : 1.0)); // v
			arr.add(s.nextDouble()); // d

			points.add(arr);
		}
		s.close();
		return points;
	}

	private void pushTrajectory(WPI_TalonSRX _talon, ArrayList<ArrayList<Double>> points) {
		// **************handle Underrun

		/* create an empty point */
		TrajectoryPoint point = new TrajectoryPoint();
		_talon.clearMotionProfileTrajectories();

		/* This is fast since it's just into our TOP buffer */
		int i = 0;
		for (ArrayList<Double> arr : points) {
			/* for each point, fill our structure and pass it to API */
			// Double[] a = (Double[]) arr.toArray();
			point.position = arr.get(0);
			
			point.velocity = arr.get(1) * multiplier;
			point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_20ms;
			//point.timeDur = (int)(arr.get(2) / multiplier);
			
			System.out.println("DriveByMotionProfile: " + point.position + " " + point.velocity + " " + point.timeDur);// + " " + point.timeDurMs);
			point.profileSlotSelect0 = 0; /*
											 * which set of gains would you like to
											 * use?
											 */
			//point.velocityOnly = false; 
										/*
										 * set true to not do any position
										 * servo, just velocity feedforward
										 */
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == points.size())
				point.isLastPoint = true; /*
											 * set this to true on the last point
											 */

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
