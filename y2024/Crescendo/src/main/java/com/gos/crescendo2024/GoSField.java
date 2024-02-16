package com.gos.crescendo2024;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoSField {
    private final Field2d m_field;
    private final FieldObject2d m_currentTrajectoryObject;
    private final FieldObject2d m_trajectorySetpoint;
    private final FieldObject2d m_odometryObject;
    private final FieldObject2d m_detectedNotePoses;

    private final FieldObject2d m_futurePosition;

    public static final class CameraObject {
        private final FieldObject2d m_estimatedPose;
        private final FieldObject2d m_aprilTags;
        private final Transform3d m_robotToCamera;

        public CameraObject(GoSField field, String cameraName, Transform3d robotToCamera) {
            m_aprilTags = field.m_field.getObject(cameraName + " detected tags");
            m_estimatedPose = field.m_field.getObject(cameraName + " estimated pose");
            m_robotToCamera = robotToCamera;
        }

        public void setCameraResult(Optional<EstimatedRobotPose> maybeResult) {
            List<Pose2d> aprilTags = new ArrayList<>();

            if (maybeResult.isPresent()) {
                EstimatedRobotPose estimatedRobotPose = maybeResult.get();
                m_estimatedPose.setPose(estimatedRobotPose.estimatedPose.toPose2d());
                for (PhotonTrackedTarget targetUsed : maybeResult.get().targetsUsed) {
                    Pose3d bestTransformPosition =
                        estimatedRobotPose.estimatedPose
                            .transformBy(m_robotToCamera.inverse())
                            .transformBy(targetUsed.getBestCameraToTarget());
                    aprilTags.add(bestTransformPosition.toPose2d());
                }
            } else {
                m_estimatedPose.setPoses();
            }

            m_aprilTags.setPoses(aprilTags);
        }
    }

    public GoSField() {
        m_field = new Field2d();

        m_currentTrajectoryObject = m_field.getObject("Trajectory");
        m_detectedNotePoses = m_field.getObject("Notes");
        m_trajectorySetpoint = m_field.getObject("TrajectoryTargetPose");
        m_odometryObject = m_field.getObject("OldOdometry");
        m_futurePosition = m_field.getObject("futurePosition");

        List<Pose2d> tagPoses = new ArrayList<>();
        for (AprilTag tag : FieldConstants.TAG_LAYOUT.getTags()) {
            tagPoses.add(tag.pose.toPose2d());
        }
        FieldObject2d aprilTagObjects = m_field.getObject("AprilTags"); // NOPMD(CloseResource)
        aprilTagObjects.setPoses(tagPoses);
    }

    public void setTrajectory(List<Pose2d> trajectory) {
        m_currentTrajectoryObject.setPoses(trajectory);
    }

    public void setTrajectorySetpoint(Pose2d pose) {
        m_trajectorySetpoint.setPose(pose);
    }

    public void setOdometry(Pose2d pose) {
        m_odometryObject.setPose(pose);
    }

    public void setFuturePose(Pose2d pose) {
        m_futurePosition.setPose(pose);
    }

    public void setPoseEstimate(Pose2d pose) {
        m_field.setRobotPose(pose);
    }

    public Sendable getSendable() {
        return m_field;
    }

    public void drawNotePoses(List<Pose2d> poses) {
        m_detectedNotePoses.setPoses(poses);
    }
}
