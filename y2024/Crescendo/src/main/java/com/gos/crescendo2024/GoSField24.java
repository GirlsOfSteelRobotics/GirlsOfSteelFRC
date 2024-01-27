package com.gos.crescendo2024;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoSField24 {
    private final Field2d m_field;
    private final FieldObject2d m_currentTrajectoryObject;
    private final FieldObject2d m_trajectorySetpoint;
    private final FieldObject2d m_odometryObject;

    private final FieldObject2d m_drawnNotePoses;

    public GoSField24() {

        AprilTagFieldLayout aprilTagLayout;
        try {
            aprilTagLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        m_field = new Field2d();

        FieldObject2d aprilTagObjects = m_field.getObject("AprilTags"); // NOPMD(CloseResource)
        m_currentTrajectoryObject = m_field.getObject("Trajectory");
        m_drawnNotePoses = m_field.getObject("Notes");
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

    public void setTrajectory(List<Pose2d> trajectory) {
        m_currentTrajectoryObject.setPoses(trajectory);
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

    public void drawNotePoses(List<Pose2d> poses) {
        m_drawnNotePoses.setPoses(poses);
    }
}
