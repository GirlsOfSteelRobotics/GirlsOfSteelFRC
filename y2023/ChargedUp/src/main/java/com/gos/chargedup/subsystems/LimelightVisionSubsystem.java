package com.gos.chargedup.subsystems;


import com.gos.chargedup.GosField;
import com.gos.lib.sensors.LimelightSensor;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.EstimatedRobotPose;

import java.util.Optional;

public class LimelightVisionSubsystem extends SubsystemBase implements Vision {
    private final LimelightSensor m_limelight;

    private final GosField.CameraObject m_field;

    public LimelightVisionSubsystem(GosField field, String name) {
        m_limelight = new LimelightSensor(name);

        m_field = new GosField.CameraObject(field, name);
    }

    @Override
    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        if (!m_limelight.isVisible()) {
            m_field.setEstimate(Optional.empty());
            return Optional.empty();
        }
        EstimatedRobotPose estimatedRobotPose = new EstimatedRobotPose(m_limelight.getRobotPose(), Timer.getFPGATimestamp() - m_limelight.getLatency(), null, null);

        m_field.setEstimate(estimatedRobotPose.estimatedPose);
        return Optional.of(estimatedRobotPose);
    }
}
