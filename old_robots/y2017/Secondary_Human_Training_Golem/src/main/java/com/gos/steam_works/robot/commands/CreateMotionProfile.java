package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.robot.RobotMap;
import com.gos.steam_works.robot.subsystems.Chassis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CreateMotionProfile extends CommandBase {
    private static final double DURATION = 20.0;
    private static final double ERROR = 0; // TODO: change

    private final Chassis m_chassis;
    private final List<List<Double>> m_leftTrajectory; // filled with arraylists of points
    private final List<List<Double>> m_rightTrajectory; // filled with arraylists of points
    private final List<Double> m_leftPoint; // position (rev), velocity (rpm), duration
    private final List<Double> m_rightPoint; // position (rev), velocity (rpm), duration
    private final String m_leftFile; // path of file on roborio
    private final String m_rightFile; // path of file on roborio
    private double m_leftInitial; // initial encoder position
    private double m_rightInitial; // initial encoder position

    public CreateMotionProfile(Chassis chassis, String leftFileName, String rightFileName) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        // maybe get file names from smart dashboard input instead?
        m_leftFile = leftFileName;
        m_rightFile = rightFileName;

        m_leftTrajectory = new ArrayList<>();
        m_rightTrajectory = new ArrayList<>();

        m_leftPoint = new ArrayList<>();
        m_rightPoint = new ArrayList<>();
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_leftInitial = m_chassis.getLeftPosition();
        m_rightInitial = m_chassis.getRightPosition();

        m_leftTrajectory.clear();
        m_rightTrajectory.clear();

        m_leftPoint.clear();
        m_rightPoint.clear();

        System.out.println("CreateMotionProfile: Starting to Record MP");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_leftPoint.clear();
        m_rightPoint.clear();

        double leftPosition = m_chassis.getLeftPosition() - m_leftInitial; // in rotations
        double rightPosition = m_chassis.getRightPosition() - m_rightInitial;

        double leftVelocity = m_chassis.getLeftVelocity() / RobotMap.CODES_PER_WHEEL_REV;
        double rightVelocity = m_chassis.getRightVelocity() / RobotMap.CODES_PER_WHEEL_REV;

        /* Other way of getting velocity: divide change in position by time

        double minutes = DURATION/1000/60; //milliseconds * (1000 ms / sec) * (60 sec / min)

        double leftDeltaP = (leftPosition - leftTrajectory.get(leftTrajectory.size()-1).get(0));
        double rightDeltaP = (rightPosition - rightTrajectory.get(rightTrajectory.size()-1).get(0));

        double leftVelocity = leftDeltaP/minutes;
        double rightVelocity = rightDeltaP/minutes;
        */

        // Get encoder position and velocity from left talon
        m_leftPoint.add(-leftPosition);
        m_leftPoint.add(-leftVelocity / RobotMap.CODES_PER_WHEEL_REV); // is this in RPM?
        m_leftPoint.add(DURATION); // should be the frequency of execute()


        // Get encoder position and velocity from right talon
        m_rightPoint.add(rightPosition); //rotations
        m_rightPoint.add(rightVelocity); //TODO: needs to be RPM
        m_rightPoint.add(DURATION); // should be the frequency of execute()

        // Add position and velocity to motion profile
        m_leftTrajectory.add(m_leftPoint);
        m_rightTrajectory.add(m_rightPoint);

        System.out.println("CreateMotionProfile: leftPoint: " + m_leftPoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        System.out.println("CreateMotionProfile: Done Recording MP");

        // remove same positions at beginning and end
        // need to do both together to make sure they have the same length
        cleanTrajectory(m_leftTrajectory, m_rightTrajectory);

        // Create left motion profile
        try {
            writeFile(m_leftFile, m_leftTrajectory);
        } catch (IOException e) {
            System.err.println("CreateMotionProfile: Left file not created");
        }

        // Create right motion profile
        try {
            writeFile(m_rightFile, m_rightTrajectory);
        } catch (IOException e) {
            System.err.println("CreateMotionProfile: Right file not created");
        }

        System.out.println("CreateMotionProfile: Finished");
    }



    private void writeFile(String filePath, List<List<Double>> trajectory) throws IOException {

        try (BufferedWriter fout = Files.newBufferedWriter(Paths.get(filePath))) {

            for (List<Double> trajectoryPoints : trajectory) { // outer loop to go
                // through the unknown #
                // of elements in
                // ArrayList<ArrayList<Double>>

                // inner loop to go through the three
                // elements of ArrayList<Double>
                for (int y = 0; y < 3; y++) {
                    fout.write(trajectoryPoints.get(y) + " ");
                }
                fout.newLine();
            }
        }
    }

    @SuppressWarnings({"PMD.NPathComplexity", "PMD.CyclomaticComplexity"})
    private void cleanTrajectory(List<List<Double>> leftMP, List<List<Double>> rightMP) {
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
