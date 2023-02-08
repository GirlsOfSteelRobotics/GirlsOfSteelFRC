package com.gos.chargedup.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import org.photonvision.EstimatedRobotPose;

import java.util.Optional;

public interface Vision {
    Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose);
}
