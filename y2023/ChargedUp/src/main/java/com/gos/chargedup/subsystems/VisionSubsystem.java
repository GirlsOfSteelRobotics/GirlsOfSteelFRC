package com.gos.chargedup.subsystems;


import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.io.IOException;
import java.util.Optional;


public class VisionSubsystem implements Subsystem {

    // TODO get transform for real robot
    private static final Transform3d ROBOT_TO_CAMERA =
        new Transform3d(
            new Translation3d(0.5, 0.0, 0.5),
            new Rotation3d(0, 0, 0));

    private static final String CAMERA_NAME = "OV5647";

    private PhotonCamera m_camera;

    private PhotonPoseEstimator m_photonPoseEstimator;

    public VisionSubsystem() {
        m_camera = new PhotonCamera(CAMERA_NAME);

        try {
            AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
            m_photonPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_camera, ROBOT_TO_CAMERA);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);

        Optional<EstimatedRobotPose> estimate = m_photonPoseEstimator.update();
        if (estimate.isPresent()) {
            EstimatedRobotPose pose = estimate.get();
            System.out.println("Got something at " + pose.estimatedPose + ", " + pose.timestampSeconds);
        } else {
            // System.out.println("No target found");
        }

        return estimate;
    }




}

