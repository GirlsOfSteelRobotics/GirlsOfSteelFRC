package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.Robot;
import com.gos.steam_works.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class CreateMotionProfile extends Command {

    private ArrayList<ArrayList<Double>> leftTrajectory; // filled with arraylists of points
    private ArrayList<ArrayList<Double>> rightTrajectory; // filled with arraylists of points
    public WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    public WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
    private ArrayList<Double> leftPoint; // position (rev), velocity (rpm), duration
    private ArrayList<Double> rightPoint; // position (rev), velocity (rpm), duration
    public String leftFile; // path of file on roborio
    public String rightFile; // path of file on roborio
    private double leftInitial; // initial encoder position
    private double rightInitial; // initial encoder position
    private static final double DURATION = 20.0;
    private static final double ERROR = 0; // TODO: change

    public CreateMotionProfile(String leftFileName, String rightFileName) {
        requires(Robot.chassis);
        // maybe get file names from smart dashboard input instead?
        leftFile = leftFileName;
        rightFile = rightFileName;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        leftInitial = leftTalon.getSelectedSensorPosition(0);
        rightInitial = rightTalon.getSelectedSensorPosition(0);

        leftTrajectory = new ArrayList<>();
        rightTrajectory = new ArrayList<>();

        leftPoint = new ArrayList<>();
        rightPoint = new ArrayList<>();

        System.out.println("CreateMotionProfile: Starting to Record MP");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        leftPoint = new ArrayList<>();
        rightPoint = new ArrayList<>();

        double leftPosition = leftTalon.getSelectedSensorPosition(0) - leftInitial; // in rotations
        double rightPosition = rightTalon.getSelectedSensorPosition(0) - rightInitial;

        double leftVelocity = leftTalon.getSelectedSensorPosition(0) / RobotMap.CODES_PER_WHEEL_REV;
        double rightVelocity = rightTalon.getSelectedSensorPosition(0) / RobotMap.CODES_PER_WHEEL_REV;

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
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
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
    @Override
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

        if (leftMP.size() > 1 && rightMP.size() > 1) {
            leftDiff = Math.abs(leftMP.get(1).get(0) - leftMP.get(0).get(0));
            rightDiff = Math.abs(rightMP.get(1).get(0) - rightMP.get(0).get(0));
        }

        while (leftMP.size() > 1 && rightMP.size() > 1 && leftDiff <= ERROR && rightDiff <= ERROR) {
            leftMP.remove(1);
            rightMP.remove(1);
        }

        // remove repeated final positions at the end
        if (leftMP.size() > 1 && rightMP.size() > 1) {
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
