package com.gos.power_up.commands;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.Command;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
@SuppressWarnings("PMD.DataClass")
public class DriveByMotionProfile extends Command {

    private static final int MIN_POINTS_IN_TALON = 5;

    private List<List<Double>> m_leftPoints;
    private List<List<Double>> m_rightPoints;
    private final Chassis m_chassis;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;
    private final MotionProfileStatus m_leftStatus;
    private final MotionProfileStatus m_rightStatus;
    private SetValueMotionProfile m_state;

    private final Notifier m_notifier = new Notifier(new PeriodicRunnable());

    public DriveByMotionProfile(Chassis chassis, String leftFile, String rightFile) {
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        addRequirements(m_chassis);


        // Load trajectory from file into array
        try {
            m_leftPoints = loadMotionProfile(leftFile, true);
            m_rightPoints = loadMotionProfile(rightFile, false);
            System.out.println("DriveByMotion: Loaded File");
        } catch (IOException ex) {
            System.err.println("File Not Found: Motion Profile Trajectories");
        }

        // Initialize status variables
        m_leftStatus = new MotionProfileStatus();
        m_rightStatus = new MotionProfileStatus();
    }


    @Override
    public void initialize() {
        m_chassis.setVelocityPIDSlot();

        // Set Talon to MP mode
        System.out.println("DriveByMotion: Change Talon to MP Mode");

        // Disable MP
        m_state = SetValueMotionProfile.Disable;
        m_leftTalon.set(ControlMode.MotionProfile, m_state.value);
        m_rightTalon.set(ControlMode.MotionProfile, m_state.value);
        System.out.println("DriveByMotion: Disable MP Mode");

        // Push Trajectory
        pushTrajectory(m_leftTalon, m_leftPoints);
        pushTrajectory(m_rightTalon, m_rightPoints);
        System.out.println("DriveByMotion: Push Trajectory");

        // Start Periodic Notifier
        m_leftTalon.changeMotionControlFramePeriod(5);
        m_rightTalon.changeMotionControlFramePeriod(5);
        m_notifier.startPeriodic(0.005);
        System.out.println("DriveByMotion: Start Periodic");

    }


    @Override
    public void execute() {
        // get MP status from each talon
        m_leftTalon.getMotionProfileStatus(m_leftStatus);
        m_rightTalon.getMotionProfileStatus(m_rightStatus);

        // Enable MP if not already enabled
        if ((m_leftStatus.btmBufferCnt > MIN_POINTS_IN_TALON) && (m_rightStatus.btmBufferCnt > MIN_POINTS_IN_TALON)) {
            m_state = SetValueMotionProfile.Enable;
        }
        m_leftTalon.set(ControlMode.MotionProfile, m_state.value);
        m_rightTalon.set(ControlMode.MotionProfile, m_state.value);
        // System.out.println("DriveByMotion: Execute Setting State: " + state);

        // did we get an underrun condition since last time we checked?
        if (m_leftStatus.hasUnderrun || m_rightStatus.hasUnderrun) {
            // better log it so we know about it
            System.out.println("DriveByMotion: A Talon has underrun!!! Left Talon: " + m_leftStatus.hasUnderrun + " Right Talon: " + m_rightStatus.hasUnderrun);
            // clear the error. This flag does not auto clear, so this way we never miss logging it.
            m_leftTalon.clearMotionProfileHasUnderrun(0);
            m_rightTalon.clearMotionProfileHasUnderrun(0);
        }
    }


    @Override
    public boolean isFinished() {
        // get MP status from each talon
        m_leftTalon.getMotionProfileStatus(m_leftStatus);
        m_rightTalon.getMotionProfileStatus(m_rightStatus);

        boolean left = m_leftStatus.activePointValid && m_leftStatus.isLast;
        boolean right = m_rightStatus.activePointValid && m_rightStatus.isLast;


        if (left && right) {
            m_state = SetValueMotionProfile.Disable;
            m_leftTalon.set(ControlMode.MotionProfile, m_state.value);
            m_rightTalon.set(ControlMode.MotionProfile, m_state.value);
            System.out.println("DriveByMotion: Finished");
        }

        return left && right;
    }


    @Override
    public void end(boolean interrupted) {
        m_notifier.stop();

        m_leftTalon.clearMotionProfileTrajectories();
        m_rightTalon.clearMotionProfileTrajectories();

        m_leftTalon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
        m_rightTalon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);

    }



    private List<List<Double>> loadMotionProfile(String filename, boolean isLeft)
        throws IOException {
        List<List<Double>> points = new ArrayList<>();
        try (InputStream is = Files.newInputStream(Paths.get(filename));
            Scanner s = new Scanner(is)) {
            while (s.hasNext()) {
                List<Double> arr = new ArrayList<>();
                arr.add(s.nextDouble() * (isLeft ? 1.0 : -1.0)); // p
                arr.add(s.nextDouble() * (isLeft ? 1.0 : -1.0)); // v
                arr.add(s.nextDouble()); // d

                points.add(arr);
            }
        }
        return points;
    }

    private void pushTrajectory(WPI_TalonSRX talon, List<List<Double>> points) {
        // **************handle Underrun

        /* create an empty point */
        TrajectoryPoint point = new TrajectoryPoint();
        talon.clearMotionProfileTrajectories();

        /* This is fast since it's just into our TOP buffer */
        int i = 0;
        for (List<Double> arr : points) {
            /* for each point, fill our structure and pass it to API */
            // Double[] a = (Double[]) arr.toArray();
            point.position = arr.get(0) * RobotMap.CODES_PER_WHEEL_REV * 3.6;

            point.velocity = arr.get(1) * RobotMap.CODES_PER_WHEEL_REV * 4 / 600;
            point.timeDur = 20;
            //point.timeDur = (int)(arr.get(2) / multiplier);

            //System.out.println("DriveByMotionProfile: " + point.position + " " + point.velocity + " " + point.timeDur); // + " " + point.timeDur);
            point.profileSlotSelect0 = 0; /*
             * which set of gains would you like to
             * use?
             */
            //point.velocityOnly = false;
            /*
             * set true to not do any position
             * servo, just velocity feedforward
             */
            point.zeroPos = i == 0; /* set this to true on the first point */
            point.isLastPoint = (i + 1) == points.size(); /*
             * set this to true on the last point
             */

            talon.pushMotionProfileTrajectory(point);
            i++;
        }
    }

    private class PeriodicRunnable implements Runnable {
        @Override
        public void run() {
            m_leftTalon.processMotionProfileBuffer();
            m_rightTalon.processMotionProfileBuffer();
        }
    }
}
