package com.gos.outreach2021.sim;

import com.gos.outreach2021.subsystems.LimelightSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import org.snobotv2.camera.LimelightSimulator;
import org.snobotv2.camera.TargetLocation;
import org.snobotv2.camera.games.InfiniteRechargeTargets;
import org.snobotv2.coordinate_gui.CameraRayPublisher;

public class CameraSimulator {

    private final LimelightSimulator m_simulator;
    private final CameraRayPublisher m_rayPublisher;

    public CameraSimulator() {
        m_simulator = new LimelightSimulator(InfiniteRechargeTargets.getTargets(), LimelightSubsystem.OBJECT_HEIGHT - LimelightSubsystem.ROBOT_HEIGHT);
        m_rayPublisher = new CameraRayPublisher();
    }

    @SuppressWarnings("PMD")
    public void update(Pose2d robotPose) {
        TargetLocation bestTarget = m_simulator.update(robotPose);
        if (bestTarget == null) {
            m_rayPublisher.clear();
        }
        else {
            m_rayPublisher.publish(robotPose, bestTarget.getPosition());
        }
    }
}
