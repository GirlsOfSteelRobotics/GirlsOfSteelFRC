package com.gos.chargedup.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PhotonVisionSubsystem implements Subsystem, Vision {

    // TODO get transform for real robot
    private static final Transform3d ROBOT_TO_CAMERA =
        new Transform3d(
            new Translation3d(0.5, 0.0, Units.inchesToMeters(28.5)),
            new Rotation3d(0, Units.degreesToRadians(29), Units.degreesToRadians(15)));

    private static final String CAMERA_NAME = "OV5647";

    //get or tune this constant
    private static final GosDoubleProperty POSE_AMBIGUITY_THRESHOLD = new GosDoubleProperty(false, "Pose ambiguity threshold", 0.2);

    private static final GosDoubleProperty POSE_DISTANCE_THRESHOLD = new GosDoubleProperty(false, "Pose distance Threshold", 4.25);

    private static final GosDoubleProperty POSE_DISTANCE_ALLOWABLE_ERROR = new GosDoubleProperty(false, "Pose distance allowable error", 0.2);

    private final PhotonCamera m_camera;

    private final AprilTagFieldLayout m_aprilTagFieldLayout;

    private final PhotonPoseEstimator m_photonPoseEstimator;

    private final Field2d m_field;

    public PhotonVisionSubsystem() {
        m_camera = new PhotonCamera(CAMERA_NAME);
        m_field = new Field2d();
        Shuffleboard.getTab("PhotonVision " + CAMERA_NAME).add(m_field);

        try {
            m_aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
            m_photonPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_camera, ROBOT_TO_CAMERA);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Pose2d> tagPoses = new ArrayList<>();
        for (AprilTag tag : m_aprilTagFieldLayout.getTags()) {
            tagPoses.add(tag.pose.toPose2d());
        }
        m_field.getObject("tags").setPoses(tagPoses);
    }



    @Override
    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_field.setRobotPose(prevEstimatedRobotPose);

        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);

        PhotonPipelineResult cameraResult = m_camera.getLatestResult();

        ArrayList<PhotonTrackedTarget> goodTargets = new ArrayList<>();

        List<Pose2d> bestGuessPoses = new ArrayList<>();
        List<Pose2d> altGuessPoses = new ArrayList<>();

        if (!cameraResult.hasTargets()) {
            return Optional.empty();
        } else {
            for (PhotonTrackedTarget target: cameraResult.getTargets()) {
                //DISTANCE: use pythagorean theorem to get absolute distance (Math.sqrt(x^2 + y^2))
                double targetPositionCamera = Math.sqrt((target.getBestCameraToTarget().getX() * target.getBestCameraToTarget().getX()) + (target.getBestCameraToTarget().getY() * target.getBestCameraToTarget().getY()));

                Optional<Pose3d> targetPosition = m_aprilTagFieldLayout.getTagPose(target.getFiducialId());
                if (targetPosition.isEmpty()) {
                    System.out.println("Bad fiducial? " + target.getFiducialId());
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

         m_field.getObject("Best Guess").setPoses(bestGuessPoses);
         m_field.getObject("Alt Guess").setPoses(altGuessPoses);

        PhotonPipelineResult goodCameraResults = new PhotonPipelineResult(cameraResult.getLatencyMillis(), goodTargets);
        goodCameraResults.setTimestampSeconds(cameraResult.getTimestampSeconds());


        //DEBUGGING:
        SmartDashboard.putNumber("Number of found targets (pre-filter): ", cameraResult.getTargets().size());
        SmartDashboard.putNumber("Number of good targets (post-filter): ", goodTargets.size());

        return m_photonPoseEstimator.update(goodCameraResults);
    }
}

