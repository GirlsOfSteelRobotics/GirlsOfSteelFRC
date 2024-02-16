package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;

import java.util.Optional;

public class AprilTagDetection {
    private static final double ROBOT_WIDTH = Units.inchesToMeters(28);
    private static final double ROBOT_LENGTH = Units.inchesToMeters(28);

    //TODO: Update values by putting values in it
    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(
        new Translation3d(
            -(ROBOT_WIDTH / 2 - 0.04), // 4cm from back
             -(ROBOT_LENGTH / 2 - 0.27), // 27cm from right side
            .235),
        new Rotation3d(0, Math.toRadians(-34), Math.toRadians(180)) // Negative because camera upside down?
//        new Rotation3d(0, Math.toRadians(34), Math.toRadians(180))
        );

    private static final String CAMERA_NAME = "AprilTag1";

    private final PhotonCamera m_photonCamera;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    private final VisionSystemSim m_visionSim;
    private final PhotonCameraSim m_cameraSim;

    private final GoSField.CameraObject m_field;

    public AprilTagDetection(GoSField field) {
        m_photonCamera = new PhotonCamera(CAMERA_NAME);
        m_field = new GoSField.CameraObject(field, CAMERA_NAME, ROBOT_TO_CAMERA);

        m_photonPoseEstimator = new PhotonPoseEstimator(FieldConstants.TAG_LAYOUT, PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, m_photonCamera, ROBOT_TO_CAMERA);
        m_photonPoseEstimator.setMultiTagFallbackStrategy(PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY);

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
