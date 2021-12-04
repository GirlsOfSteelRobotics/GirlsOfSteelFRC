package org.usfirst.frc.team3504.robot.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionProfile extends Command {

    public List<List<Double>> leftPoints;
    public List<List<Double>> rightPoints;
    public CANTalon leftTalon = Robot.chassis.getLeftTalon();
    public CANTalon rightTalon = Robot.chassis.getRightTalon();
    private final MotionProfileStatus leftStatus;
    private final MotionProfileStatus rightStatus;
    private static final int kMinPointsInTalon = 5;
    private SetValueMotionProfile state;
    private final double multiplier;

    Notifier notifier = new Notifier(new PeriodicRunnable());

    public DriveByMotionProfile(String leftFile, String rightFile, double multiplier) {
        requires(Robot.chassis);

        this.multiplier = multiplier;

        // Load trajectory from file into array
        try {
            leftPoints = loadMotionProfile(leftFile, true);
            rightPoints = loadMotionProfile(rightFile, false);
            System.out.println("DriveByMotion: Loaded File");
        } catch (IOException ex) {
            System.err.println("File Not Found: Motion Profile Trajectories");
        }

        // Initialize status variables
        leftStatus = new MotionProfileStatus();
        rightStatus = new MotionProfileStatus();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        Robot.chassis.setupFPID(leftTalon);
        Robot.chassis.setupFPID(rightTalon);

        // Set Talon to MP mode
        Robot.chassis.setMotionProfileMode();
        System.out.println("DriveByMotion: Change Talon to MP Mode");

        // Disable MP
        state = SetValueMotionProfile.Disable;
        leftTalon.set(state.value);
        rightTalon.set(state.value);
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
    @Override
    protected void execute() {
        // get MP status from each talon
        leftTalon.getMotionProfileStatus(leftStatus);
        rightTalon.getMotionProfileStatus(rightStatus);

        // Enable MP if not already enabled
        if ((leftStatus.btmBufferCnt > kMinPointsInTalon) && (rightStatus.btmBufferCnt > kMinPointsInTalon)) {
            state = SetValueMotionProfile.Enable;
        }
        leftTalon.set(state.value);
        rightTalon.set(state.value);
        // System.out.println("DriveByMotion: Execute Setting State: " + state);

        // did we get an underrun condition since last time we checked?
        if (leftStatus.hasUnderrun || rightStatus.hasUnderrun) {
            // better log it so we know about it
            System.out.println("DriveByMotion: A Talon has underrun!!! Left Talon: " + leftStatus.hasUnderrun + " Right Talon: " + rightStatus.hasUnderrun);
            // clear the error. This flag does not auto clear, so this way we never miss logging it.
            leftTalon.clearMotionProfileHasUnderrun();
            rightTalon.clearMotionProfileHasUnderrun();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // get MP status from each talon
        leftTalon.getMotionProfileStatus(leftStatus);
        rightTalon.getMotionProfileStatus(rightStatus);

        boolean left = (leftStatus.activePointValid && leftStatus.isLast);
        boolean right = (rightStatus.activePointValid && rightStatus.isLast);


        if (left && right) {
            state = SetValueMotionProfile.Disable;
            leftTalon.set(state.value);
            rightTalon.set(state.value);
            System.out.println("DriveByMotion: Finished");
        }

        return (left && right);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        notifier.stop();

        leftTalon.clearMotionProfileTrajectories();
        rightTalon.clearMotionProfileTrajectories();

        leftTalon.set(SetValueMotionProfile.Disable.value);
        rightTalon.set(SetValueMotionProfile.Disable.value);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }

    private List<List<Double>> loadMotionProfile(String filename, boolean isLeft)
            throws IOException {
        List<List<Double>> points = new ArrayList<>();
        InputStream is = Files.newInputStream(Paths.get(filename));
        Scanner s = new Scanner(is);
        while (s.hasNext()) {
            List<Double> arr = new ArrayList<>();
            arr.add(s.nextDouble() * (isLeft ? -1.0 : 1.0)); // p
            arr.add(s.nextDouble() * (isLeft ? -1.0 : 1.0)); // v
            arr.add(s.nextDouble()); // d

            points.add(arr);
        }
        s.close();
        return points;
    }

    private void pushTrajectory(CANTalon _talon, List<List<Double>> points) {
        // **************handle Underrun

        /* create an empty point */
        TrajectoryPoint point = new TrajectoryPoint();
        _talon.clearMotionProfileTrajectories();

        /* This is fast since it's just into our TOP buffer */
        int i = 0;
        for (List<Double> arr : points) {
            /* for each point, fill our structure and pass it to API */
            // Double[] a = (Double[]) arr.toArray();
            point.position = arr.get(0);

            point.velocity = arr.get(1) * multiplier;
            point.timeDur = (int)(arr.get(2) / multiplier);

            System.out.println("DriveByMotionProfile: " + point.position + " " + point.velocity + " " + point.timeDur);
            point.profileSlotSelect0 = 0; /*
                                             * which set of gains would you like to
                                             * use?
                                             */
            point.zeroPos = i == 0; /* set this to true on the first point */
            point.isLastPoint = (i + 1) == points.size(); /*
                                         * set this to true on the last point
                                         */

            _talon.pushMotionProfileTrajectory(point);
            i++;
        }
    }

    class PeriodicRunnable implements java.lang.Runnable {
        @Override
        public void run() {
            leftTalon.processMotionProfileBuffer();
            rightTalon.processMotionProfileBuffer();
        }
    }
}
