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
    //TODO: Update values by putting values in it

    public static final Matrix<N3, N1> SINGLE_TAG_STDDEV = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> MULTI_TAG_STDDEV = VecBuilder.fill(0.5, 0.5, 1);
    private static final Transform3d ROBOT_TO_CAMERA = new Transform3d(new Translation3d(0, 0, 0), new Rotation3d(0, 0, 0));
    private final PhotonCamera m_photonCamera;
    private final PhotonPoseEstimator m_photonPoseEstimator;
    private final VisionSystemSim m_visionSim;
    private final PhotonCameraSim m_cameraSim;

    public AprilTagDetection() {
        m_photonCamera = new PhotonCamera("AprilTag1");

        m_photonPoseEstimator = new PhotonPoseEstimator(FieldConstants.TAG_LAYOUT, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_photonCamera, ROBOT_TO_CAMERA);

        if (RobotBase.isSimulation()) {
            m_cameraSim = new PhotonCameraSim(m_photonCamera);
            m_cameraSim.enableRawStream(true);
            m_cameraSim.enableProcessedStream(true);
            m_cameraSim.enableDrawWireframe(true);

            m_visionSim = new VisionSystemSim("AprilTag1");
            m_visionSim.addCamera(m_cameraSim, ROBOT_TO_CAMERA);
            m_visionSim.addAprilTags(FieldConstants.TAG_LAYOUT);
        } else {
            m_cameraSim = null;
            m_visionSim = null;
        }
    }

    public Optional<EstimatedRobotPose> getEstimateGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return m_photonPoseEstimator.update();
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
