package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.chargedup.GosField;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PhotonVisionSubsystem extends SubsystemBase implements Vision {

    // TODO get transform for real robot


    private static final Transform3d ROBOT_TO_CAMERA;

    static {
        //toDo: make these values work as expected
        if (Constants.IS_ROBOT_BLOSSOM) {
            ROBOT_TO_CAMERA = new Transform3d(new Translation3d(Units.inchesToMeters(11), Units.inchesToMeters(0), Units.inchesToMeters(12)), new Rotation3d(0, 0, 0));
        } else {
            ROBOT_TO_CAMERA = new Transform3d(new Translation3d(Units.inchesToMeters(11), Units.inchesToMeters(0), Units.inchesToMeters(12.25)), new Rotation3d(0, 0, 0));
        }
    }

    private static final String CAMERA_NAME = "OV5647";

    //get or tune this constant
    private static final GosDoubleProperty POSE_AMBIGUITY_THRESHOLD = new GosDoubleProperty(false, "Pose ambiguity threshold", 0.35);
    private static final GosDoubleProperty POSE_DISTANCE_THRESHOLD = new GosDoubleProperty(false, "Pose distance Threshold", 4.25);
    private static final GosDoubleProperty POSE_DISTANCE_ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pose distance allowable error", 0.2);

    private final PhotonCamera m_camera;
    private final AprilTagFieldLayout m_aprilTagFieldLayout;
    private final PhotonPoseEstimator m_photonPoseEstimator;

    private final GosField.CameraObject m_field;

    public PhotonVisionSubsystem(GosField field) {
        m_camera = new PhotonCamera(CAMERA_NAME);
        this.m_field = new GosField.CameraObject(field, CAMERA_NAME);

        try {
            m_aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
            m_photonPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_camera, ROBOT_TO_CAMERA);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);

        PhotonPipelineResult cameraResult = m_camera.getLatestResult();

        ArrayList<PhotonTrackedTarget> goodTargets = new ArrayList<>();

        List<Pose2d> bestGuessPoses = new ArrayList<>();
        List<Pose2d> altGuessPoses = new ArrayList<>();

        if (!cameraResult.hasTargets()) {
            m_field.setBestGuesses(bestGuessPoses);
            m_field.setAltGuesses(altGuessPoses);
            m_field.setEstimate(Optional.empty());
            return Optional.empty();
        } else {
            for (PhotonTrackedTarget target: cameraResult.getTargets()) {
                //DISTANCE: use pythagorean theorem to get absolute distance (Math.sqrt(x^2 + y^2))
                double targetPositionCamera = Math.sqrt((target.getBestCameraToTarget().getX() * target.getBestCameraToTarget().getX()) + (target.getBestCameraToTarget().getY() * target.getBestCameraToTarget().getY()));

                Optional<Pose3d> targetPosition = m_aprilTagFieldLayout.getTagPose(target.getFiducialId());
                if (targetPosition.isEmpty()) {
                    continue;
                }
                Pose3d bestTransformPosition =
                    targetPosition
                        .get()
                        .transformBy(target.getBestCameraToTarget().inverse())
                        .transformBy(ROBOT_TO_CAMERA.inverse());
                bestGuessPoses.add(bestTransformPosition.toPose2d());
                Pose3d altTransformPosition =
                    targetPosition
                        .get()
                        .transformBy(target.getAlternateCameraToTarget().inverse())
                        .transformBy(ROBOT_TO_CAMERA.inverse());
                altGuessPoses.add(altTransformPosition.toPose2d());
                double distBetweenAltandPrev = Math.sqrt((altTransformPosition.getX() - prevEstimatedRobotPose.getX()) * (altTransformPosition.getX() - prevEstimatedRobotPose.getX()) + ((altTransformPosition.getY() - prevEstimatedRobotPose.getY())) * (altTransformPosition.getY() - prevEstimatedRobotPose.getY()));
                boolean isInSamePlace = distBetweenAltandPrev <= POSE_DISTANCE_ALLOWABLE_ERROR.getValue();

                //check ambiguity and distance
                if (target.getPoseAmbiguity() <= POSE_AMBIGUITY_THRESHOLD.getValue() && targetPositionCamera <= POSE_DISTANCE_THRESHOLD.getValue() && isInSamePlace) {
                    goodTargets.add(target);
                }
            }
        }

        m_field.setBestGuesses(bestGuessPoses);
        m_field.setAltGuesses(altGuessPoses);

        PhotonPipelineResult goodCameraResults = new PhotonPipelineResult(cameraResult.getLatencyMillis(), goodTargets);
        goodCameraResults.setTimestampSeconds(cameraResult.getTimestampSeconds());


        //DEBUGGING:
        SmartDashboard.putNumber("Number of found targets (pre-filter): ", cameraResult.getTargets().size());
        SmartDashboard.putNumber("Number of good targets (post-filter): ", goodTargets.size());

        Optional<EstimatedRobotPose> estimate = m_photonPoseEstimator.update(goodCameraResults);
        m_field.setEstimate(estimate);
        return estimate;
    }
}
