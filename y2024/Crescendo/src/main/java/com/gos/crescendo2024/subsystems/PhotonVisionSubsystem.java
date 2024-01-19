package com.gos.crescendo2024.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.io.IOException;
import java.util.Optional;

public class PhotonVisionSubsystem {
    //TODO: Update values by putting values in it
    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d( new Translation3d(0,0,0), new Rotation3d(0,0,0));
    private final PhotonCamera m_photonCamera;
    private final AprilTagFieldLayout m_aprilTagFieldLayout;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    public PhotonVisionSubsystem () {
        m_photonCamera = new PhotonCamera("photonvision");
        try {
            m_aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
            m_photonPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_photonCamera, ROBOT_TO_CAMERA);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<EstimatedRobotPose> getEstimateGlobalPose(Pose2d prevEstimatedRobotPose){
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return m_photonPoseEstimator.update();
    }
}
