package com.gos.crescendo2024;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;

import java.io.IOException;
import java.util.Optional;

public class AprilTagDetection {
    //TODO: Update values by putting values in it
    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(new Translation3d(0, 0, 0), new Rotation3d(0, 0, 0));
    private final PhotonCamera m_photonCamera;
    private final AprilTagFieldLayout m_aprilTagFieldLayout;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    private final VisionSystemSim m_visionSim;
    private final PhotonCameraSim m_cameraSim;

    public AprilTagDetection() {
        m_photonCamera = new PhotonCamera("aprilTags");
        m_visionSim = new VisionSystemSim("aprilTagsSim");
        m_cameraSim  = new PhotonCameraSim(m_photonCamera);
        m_visionSim.addCamera(m_cameraSim, ROBOT_TO_CAMERA);

        try {
            m_aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
            m_visionSim.addAprilTags(m_aprilTagFieldLayout);
            m_photonPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_photonCamera, ROBOT_TO_CAMERA);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        m_cameraSim.enableRawStream(true);
        m_cameraSim.enableProcessedStream(true);
        m_cameraSim.enableDrawWireframe(true);
    }

    public Optional<EstimatedRobotPose> getEstimateGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return m_photonPoseEstimator.update();
    }

    public void updateAprilTagSimulation(Pose2d chassisLocation) {
        m_visionSim.update(chassisLocation);
    }
}
