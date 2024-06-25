package com.gos.chargedup;

import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import org.photonvision.EstimatedRobotPose;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GosField {

    private final Field2d m_field;
    private final FieldObject2d m_currentTrajectoryObject;
    private final FieldObject2d m_trajectorySetpoint;
    private final FieldObject2d m_odometryObject;

    @SuppressWarnings("PMD.DataClass")
    public static class CameraObject {

        private final FieldObject2d m_bestGuesses;
        private final FieldObject2d m_altGuesses;
        private final FieldObject2d m_estimatedPosition;
        private final FieldObject2d m_detectedTags;
        private final FieldObject2d m_filteredTags;

        public CameraObject(GosField field, String cameraName) {
            m_bestGuesses = field.m_field.getObject(cameraName + ": BestGuesses");
            m_altGuesses = field.m_field.getObject(cameraName + ": AltGuesses");
            m_estimatedPosition = field.m_field.getObject(cameraName + ": EstimatedPosition");
            m_detectedTags = field.m_field.getObject(cameraName + ": DetectedTags");
            m_filteredTags = field.m_field.getObject(cameraName + ": FilteredTags");
        }

        public void setBestGuesses(List<Pose2d> poses) {
            m_bestGuesses.setPoses(poses);
        }

        public void setAltGuesses(List<Pose2d> poses) {
            m_altGuesses.setPoses(poses);
        }

        public void setEstimate(Optional<EstimatedRobotPose> maybeEstimate) {
            if (maybeEstimate.isPresent()) {
                setEstimate(maybeEstimate.get().estimatedPose);
            } else {
                m_estimatedPosition.setPoses();
            }
        }

        public void setEstimate(Pose3d estimate) {
            m_estimatedPosition.setPose(estimate.toPose2d());
        }

        public void setDetectedTags(List<Pose2d> detectedTags) {
            m_detectedTags.setPoses(detectedTags);
        }

        public void setFilteredTags(List<Pose2d> filteredTags) {
            m_filteredTags.setPoses(filteredTags);
        }
    }

    public static class RectangleObject {
        private final Pose2d[] m_corners = new Pose2d[4];
        private final FieldObject2d m_object;
        private boolean m_lastAllianceIsBlue;

        public RectangleObject(double leftTopX, double leftTopY, double rightBottomX, double rightBottomY, GosField field, String name) {
            m_corners[0] = new Pose2d(leftTopX, leftTopY, new Rotation2d(0.0));
            m_corners[1] = new Pose2d(rightBottomX, leftTopY, new Rotation2d(0.0));
            m_corners[2] = new Pose2d(rightBottomX, rightBottomY, new Rotation2d(0.0));
            m_corners[3] = new Pose2d(leftTopX, rightBottomY, new Rotation2d(0.0));

            m_object = field.m_field.getObject(name);

            ArrayList<Pose2d> poses = new ArrayList<>();
            for (Pose2d pose : m_corners) {
                poses.add(AllianceFlipper.maybeFlip(pose));
            }
            m_object.setPoses(poses);
            m_lastAllianceIsBlue = GetAllianceUtil.isBlueAlliance();
        }

        public void updateRectangle() {
            boolean isBlue = GetAllianceUtil.isBlueAlliance();
            if (isBlue != m_lastAllianceIsBlue) {
                ArrayList<Pose2d> poses = new ArrayList<>();
                for (Pose2d pose : m_corners) {
                    poses.add(AllianceFlipper.maybeFlip(pose));
                }
                m_object.setPoses(poses);
            }

            m_lastAllianceIsBlue = isBlue;
        }
    }

    public GosField() {

        AprilTagFieldLayout aprilTagLayout;
        try {
            aprilTagLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        m_field = new Field2d();

        FieldObject2d aprilTagObjects = m_field.getObject("AprilTags"); // NOPMD(CloseResource)
        m_currentTrajectoryObject = m_field.getObject("Trajectory");
        m_trajectorySetpoint = m_field.getObject("TrajectoryTargetPose");
        m_odometryObject = m_field.getObject("OldOdometry");

        List<Pose2d> tagPoses = new ArrayList<>();
        for (AprilTag tag : aprilTagLayout.getTags()) {
            tagPoses.add(tag.pose.toPose2d());
        }
        aprilTagObjects.setPoses(tagPoses);
    }

    public void clearTrajectory() {
        m_currentTrajectoryObject.setPoses();
        m_trajectorySetpoint.setPoses();
    }

    public void setTrajectory(Trajectory trajectory) {
        m_currentTrajectoryObject.setTrajectory(trajectory);
    }

    public void setTrajectorySetpoint(Pose2d pose) {
        m_trajectorySetpoint.setPose(pose);
    }

    public void setOdometry(Pose2d pose) {
        m_odometryObject.setPose(pose);
    }

    public void setPoseEstimate(Pose2d pose) {
        m_field.setRobotPose(pose);
    }

    public Sendable getSendable() {
        return m_field;
    }

}
