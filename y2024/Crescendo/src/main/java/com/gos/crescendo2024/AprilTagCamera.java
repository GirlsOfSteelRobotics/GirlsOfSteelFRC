package com.gos.crescendo2024;

import com.gos.lib.field.AprilTagCameraObject;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.RobotBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AprilTagCamera {
    private final Transform3d m_robotToCamera;
    private final String m_cameraName;
    private static final Matrix<N3, N1> SINGLE_TAG_STDDEV = VecBuilder.fill(1.5, 1.5, 8); // (4, 4, 8)
    private static final Matrix<N3, N1> MULTI_TAG_STDDEV = VecBuilder.fill(0.25, 0.25, 500); // (0.5, 0.5, 1)

    private final PhotonCamera m_photonCamera;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    private final PhotonCameraSim m_cameraSim;

    private final AprilTagCameraObject m_field;

    // Cached values
    private Optional<EstimatedRobotPose> m_maybeResult;
    private PhotonPipelineResult m_lastPipelineResult;

    public AprilTagCamera(GoSField field, String name, Transform3d transform3d) {
        m_cameraName = name;
        m_robotToCamera = transform3d;
        m_photonCamera = new PhotonCamera(m_cameraName);
        m_field = new AprilTagCameraObject(field, m_cameraName, m_robotToCamera);

        m_photonPoseEstimator = new PhotonPoseEstimator(FieldConstants.TAG_LAYOUT, PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, m_photonCamera, m_robotToCamera);
        m_photonPoseEstimator.setMultiTagFallbackStrategy(PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY);

        if (RobotBase.isSimulation()) {
            m_cameraSim = new PhotonCameraSim(m_photonCamera);

            boolean enableFancySim = true;
            m_cameraSim.enableRawStream(enableFancySim);
            m_cameraSim.enableProcessedStream(enableFancySim);
            m_cameraSim.enableDrawWireframe(enableFancySim);
        } else {
            m_cameraSim = null;
        }

        m_maybeResult = Optional.empty();
    }

    public void update(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        m_maybeResult = m_photonPoseEstimator.update();

        if (m_maybeResult.isEmpty()) {
            m_field.clearCameraResult();
        } else {
            List<Pose3d> aprilTags = new ArrayList<>();
            EstimatedRobotPose estimatedRobotPose = m_maybeResult.get();
            for (PhotonTrackedTarget targetUsed : m_maybeResult.get().targetsUsed) {
                Pose3d bestTransformPosition =
                    estimatedRobotPose.estimatedPose
                        .transformBy(m_robotToCamera)
                        .transformBy(targetUsed.getBestCameraToTarget());
                aprilTags.add(bestTransformPosition);
            }
            m_field.setCameraResult(m_maybeResult.get().estimatedPose, aprilTags);
        }

        m_lastPipelineResult = m_photonCamera.getLatestResult();
    }

    public Optional<EstimatedRobotPose> getEstimateGlobalPose() {
        return m_maybeResult;
    }

    public Matrix<N3, N1> getEstimationStdDevs(Pose2d estimatedPose) {
        Matrix<N3, N1> estStdDevs = SINGLE_TAG_STDDEV;
        List<PhotonTrackedTarget> targets = m_lastPipelineResult.getTargets();
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
        if (numTags == 1 && avgDist > 2.5) {
            estStdDevs = VecBuilder.fill(1000, 1000, 1000);
        }
        else {
            estStdDevs = estStdDevs.times(1 + (avgDist * avgDist / 30));
        }

        return estStdDevs;
    }

    public void takeScreenshot() {
        m_photonCamera.takeInputSnapshot();
        m_photonCamera.takeOutputSnapshot();
    }

    public PhotonPipelineResult getLatestResult() {
        return m_lastPipelineResult;
    }

    public PhotonCameraSim getSimulator() {
        return m_cameraSim;
    }

    public Transform3d getRobotToCamera() {
        return m_robotToCamera;
    }
}
