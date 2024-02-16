package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.RobotBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;

import java.util.Optional;

public class AprilTagDetection {
    //TODO: Update values by putting values in it
    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(
        new Translation3d(0, 0, 0),
        new Rotation3d(0, 0, 0));

    private static final String CAMERA_NAME = "AprilTag1";

    private final PhotonCamera m_photonCamera;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    private final VisionSystemSim m_visionSim;
    private final PhotonCameraSim m_cameraSim;

    private final GoSField.CameraObject m_field;

    public AprilTagDetection(GoSField field) {
        m_photonCamera = new PhotonCamera(CAMERA_NAME);
        m_field = new GoSField.CameraObject(field, CAMERA_NAME, ROBOT_TO_CAMERA);

        m_photonPoseEstimator = new PhotonPoseEstimator(FieldConstants.TAG_LAYOUT, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_photonCamera, ROBOT_TO_CAMERA);

        if (RobotBase.isSimulation()) {
            m_cameraSim = new PhotonCameraSim(m_photonCamera);
            m_cameraSim.enableRawStream(true);
            m_cameraSim.enableProcessedStream(true);
            m_cameraSim.enableDrawWireframe(true);

            m_visionSim = new VisionSystemSim(CAMERA_NAME);
            m_visionSim.addCamera(m_cameraSim, ROBOT_TO_CAMERA);
            m_visionSim.addAprilTags(FieldConstants.TAG_LAYOUT);
        } else {
            m_cameraSim = null;
            m_visionSim = null;
        }
    }

    public Optional<EstimatedRobotPose> getEstimateGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        Optional<EstimatedRobotPose> result = m_photonPoseEstimator.update();
        m_field.setCameraResult(result);
        return result;
    }

    public void updateAprilTagSimulation(Pose2d chassisLocation) {
        m_visionSim.update(chassisLocation);
    }
}
