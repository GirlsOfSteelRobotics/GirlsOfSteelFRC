package org.usfirst.frc.team3504.robot.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CreateMotionProfile extends Command {

	private ArrayList<ArrayList<Double>> leftTrajectory; // filled with arraylists of points
	private ArrayList<ArrayList<Double>> rightTrajectory; // filled with arraylists of points
	public CANTalon leftTalon = Robot.chassis.getLeftTalon();
	public CANTalon rightTalon = Robot.chassis.getRightTalon();
	private ArrayList<Double> leftPoint; // position (rev), velocity (rpm), duration
	private ArrayList<Double> rightPoint; // position (rev), velocity (rpm), duration
	public String leftFile; // path of file on roborio
	public String rightFile; // path of file on roborio
	private double leftInitial; // initial encoder position
	private double rightInitial; // initial encoder position
	private final double DURATION = 20.0;
	private final double ERROR = 0; // TODO: change

	public CreateMotionProfile(String leftFileName, String rightFileName) {
		requires(Robot.chassis);
		// maybe get file names from smart dashboard input instead?
		leftFile = leftFileName;
		rightFile = rightFileName;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		leftInitial = (double) leftTalon.getPosition();
		rightInitial = (double) rightTalon.getPosition();
		
		leftTrajectory = new ArrayList<ArrayList<Double>>();
		rightTrajectory = new ArrayList<ArrayList<Double>>();
		
		leftPoint = new ArrayList<Double>();
		rightPoint = new ArrayList<Double>();
		
		System.out.println("CreateMotionProfile: Starting to Record MP");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		leftPoint = new ArrayList<Double>();
		rightPoint = new ArrayList<Double>();
		
		double leftPosition = (double) leftTalon.getPosition() - leftInitial; // in rotations
		double rightPosition = (double) rightTalon.getPosition() - rightInitial;
		
		double leftVelocity = (double) leftTalon.getEncVelocity() / RobotMap.CODES_PER_WHEEL_REV;
		double rightVelocity = (double) rightTalon.getEncVelocity() / RobotMap.CODES_PER_WHEEL_REV;
		
		/* Other way of getting velocity: divide change in position by time
		
		double minutes = DURATION/1000/60; //milliseconds * (1000 ms / sec) * (60 sec / min)
		
		double leftDeltaP = (leftPosition - leftTrajectory.get(leftTrajectory.size()-1).get(0));
		double rightDeltaP = (rightPosition - rightTrajectory.get(rightTrajectory.size()-1).get(0));
		
		double leftVelocity = leftDeltaP/minutes;
		double rightVelocity = rightDeltaP/minutes;
		*/

		// Get encoder position and velocity from left talon
		leftPoint.add(-leftPosition);
		leftPoint.add(-leftVelocity / RobotMap.CODES_PER_WHEEL_REV); // is this in RPM?
		leftPoint.add(DURATION); // should be the frequency of execute()


		// Get encoder position and velocity from right talon
		rightPoint.add(rightPosition); //rotations
		rightPoint.add(rightVelocity); //TODO: needs to be RPM
		rightPoint.add(DURATION); // should be the frequency of execute()

		// Add position and velocity to motion profile
		leftTrajectory.add(leftPoint);
		rightTrajectory.add(rightPoint);

		System.out.println("CreateMotionProfile: leftPoint: " + leftPoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("CreateMotionProfile: Done Recording MP");

		// remove same positions at beginning and end
		// need to do both together to make sure they have the same length
		cleanTrajectory(leftTrajectory, rightTrajectory);

		// Create left motion profile
		try {
			writeFile(leftFile, leftTrajectory);
		} catch (IOException e) {
			System.err.println("CreateMotionProfile: Left file not created");
		}

		// Create right motion profile
		try {
			writeFile(rightFile, rightTrajectory);
		} catch (IOException e) {
			System.err.println("CreateMotionProfile: Right file not created");
		}
		
		System.out.println("CreateMotionProfile: Finished");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	private void writeFile(String filePath, ArrayList<ArrayList<Double>> trajectory) throws IOException {

		FileWriter outFile = new FileWriter(filePath);
		BufferedWriter fout = new BufferedWriter(outFile);

		for (int x = 0; x < trajectory.size(); x++) { // outer loop to go
														// through the unknown #
														// of elements in
														// ArrayList<ArrayList<Double>>
			for (int y = 0; y < 3; y++) // inner loop to go through the three
										// elements of ArrayList<Double>
			{
				fout.write(trajectory.get(x).get(y) + " ");
			}
			fout.newLine();
		}
		fout.close();
	}

	private void cleanTrajectory(ArrayList<ArrayList<Double>> leftMP, ArrayList<ArrayList<Double>> rightMP) {
		// remove all extra zero positions at the beginning
		// TODO: does first position need to be exactly zero?
		double leftDiff = 0;
		double rightDiff = 0;
		
		if (leftMP.size() > 1 && rightMP.size() > 1){
			leftDiff = Math.abs(leftMP.get(1).get(0) - leftMP.get(0).get(0));
			rightDiff = Math.abs(rightMP.get(1).get(0) - rightMP.get(0).get(0));
		}
		
		while (leftMP.size() > 1 && rightMP.size() > 1 && leftDiff <= ERROR && rightDiff <= ERROR) {
			leftMP.remove(1);
			rightMP.remove(1);
		}
		
		// remove repeated final positions at the end
		if (leftMP.size() > 1 && rightMP.size() > 1){
			leftDiff = Math.abs(leftMP.get(leftMP.size() - 2).get(0) - leftMP.get(leftMP.size() - 1).get(0));
			rightDiff = Math.abs(rightMP.get(rightMP.size() - 2).get(0) - rightMP.get(rightMP.size() - 1).get(0));
		}
		
		while (leftMP.size() > 1 && rightMP.size() > 1 && leftDiff <= ERROR && rightDiff <= ERROR) {
			// while the second to last position is the same as the last
			// position for both motion profiles
			leftMP.remove(leftMP.size() - 2);
			rightMP.remove(rightMP.size() - 2);

			leftDiff = Math.abs(leftMP.get(leftMP.size() - 2).get(0) - leftMP.get(leftMP.size() - 1).get(0));
			rightDiff = Math.abs(rightMP.get(rightMP.size() - 2).get(0) - rightMP.get(rightMP.size() - 1).get(0));
		}
	}

}
