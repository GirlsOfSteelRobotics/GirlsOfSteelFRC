package com.gos.lib.field;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.Field3d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject3d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseGosField {
    protected final Field2d m_field2d;
    protected final Field3d m_field3d;

    protected final FieldObject2d m_currentTrajectoryObject;
    protected final FieldObject2d m_trajectorySetpoint;
    protected final FieldObject2d m_odometryObject;

    public BaseGosField(AprilTagFieldLayout aprilTagLayout) {
        m_field2d = new Field2d();
        m_currentTrajectoryObject = m_field2d.getObject("Trajectory");
        m_trajectorySetpoint = m_field2d.getObject("TrajectoryTargetPose");
        m_odometryObject = m_field2d.getObject("OldOdometry");

        m_field3d = new Field3d();

        setAprilTagLayout(aprilTagLayout);
    }

    public final void setAprilTagLayout(AprilTagFieldLayout aprilTagLayout) {
        List<Pose3d> tagPoses = new ArrayList<>();
        for (AprilTag tag : aprilTagLayout.getTags()) {
            tagPoses.add(tag.pose);
        }

        FieldObject3d aprilTag3dObject = m_field3d.getObject("AprilTags"); // NOPMD(CloseResource)
        aprilTag3dObject.setPoses(tagPoses);

        FieldObject2d aprilTag2dObjects = m_field2d.getObject("AprilTags"); // NOPMD(CloseResource)
        aprilTag2dObjects.setPoses(pose3dTo2d(tagPoses));
    }

    public final void setTrajectory(List<Pose2d> trajectory) {
        m_currentTrajectoryObject.setPoses(trajectory);
    }

    public final void setTrajectorySetpoint(Pose2d pose) {
        m_trajectorySetpoint.setPose(pose);
    }

    public final void setOdometry(Pose2d pose) {
        m_odometryObject.setPose(pose);
    }

    public void setPoseEstimate(Pose2d pose) {
        m_field2d.setRobotPose(pose);
    }

    public static List<Pose2d> pose3dTo2d(List<Pose3d> poses) {
        return
            poses.stream()
                .map(Pose3d::toPose2d)
                .collect(Collectors.toUnmodifiableList());
    }

    public Field2d getField2d() {
        return m_field2d;
    }

    public Field3d getField3d() {
        return m_field3d;
    }
}
