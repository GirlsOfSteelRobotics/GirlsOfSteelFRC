package com.gos.crescendo2024;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.List;
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
    );

    private static final String CAMERA_NAME = "AprilTag1";


    private static final Matrix<N3, N1> SINGLE_TAG_STDDEV = VecBuilder.fill(4, 4, 8);
    private static final Matrix<N3, N1> MULTI_TAG_STDDEV = VecBuilder.fill(0.5, 0.5, 1);
    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(new Translation3d(0, 0, 0), new Rotation3d(0, 0, 0));
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

    public PhotonPipelineResult getLatestResult() {
        return m_photonCamera.getLatestResult();
    }

    public void updateAprilTagSimulation(Pose2d chassisLocation) {
        m_visionSim.update(chassisLocation);
    }

    public Matrix<N3, N1> getEstimationStdDevs(Pose2d estimatedPose) {
        Matrix<N3, N1> estStdDevs = SINGLE_TAG_STDDEV;
        List<PhotonTrackedTarget> targets = getLatestResult().getTargets();
        int numTags = 0;
        double sumDist = 0;
        for (PhotonTrackedTarget tgt : targets) {
            Optional<Pose3d> tagPose = m_photonPoseEstimator.getFieldTags().getTagPose(tgt.getFiducialId());
            if (tagPose.isEmpty()) {
                continue;
            }
            numTags++;
            sumDist +=
                tagPose.get().toPose2d().getTranslation().getDistance(estimatedPose.getTranslation());
        }
        if (numTags == 0) {
            return estStdDevs;
        }
        double avgDist = sumDist / numTags;
        // Decrease std devs if multiple targets are visible
        if (numTags > 1) {
            estStdDevs = MULTI_TAG_STDDEV;
        }
        // Increase std devs based on (average) distance
        if (numTags == 1 && avgDist > 4) {
            estStdDevs = VecBuilder.fill(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        }
        else {
            estStdDevs = estStdDevs.times(1 + (avgDist * avgDist / 30));
        }

        return estStdDevs;
    }

}
