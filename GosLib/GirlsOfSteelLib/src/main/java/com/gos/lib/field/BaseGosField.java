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

/**
 * An extension of the {@link Field2d} class that holds extra commonly used GOS debugging data
 */
public class BaseGosField {
    protected final Field2d m_field2d;
    protected final Field3d m_field3d;

    protected final FieldObject2d m_currentTrajectoryObject;
    protected final FieldObject2d m_trajectorySetpoint;
    protected final FieldObject2d m_odometryObject;

    /**
     * Constructor.
     * @param aprilTagLayout The april tag layout, used to draw where april tags are expected to be.
     */
    public BaseGosField(AprilTagFieldLayout aprilTagLayout) {
        m_field2d = new Field2d();
        m_currentTrajectoryObject = m_field2d.getObject("Trajectory");
        m_trajectorySetpoint = m_field2d.getObject("TrajectoryTargetPose");
        m_odometryObject = m_field2d.getObject("OldOdometry");

        m_field3d = new Field3d();

        setAprilTagLayout(aprilTagLayout);
    }

    /**
     * Sets a new april tag layout.
     * @param aprilTagLayout The layout
     */
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

    /**
     * Adds a trajectory to the field.
     * @param trajectory The trajectory
     */
    public final void setTrajectory(List<Pose2d> trajectory) {
        m_currentTrajectoryObject.setPoses(trajectory);
    }

    /**
     * Adds an object for the trajectory setpoint, aka the current goal position of the trajectory
     * @param pose The setpoint pose
     */
    public final void setTrajectorySetpoint(Pose2d pose) {
        m_trajectorySetpoint.setPose(pose);
    }

    /**
     * Sets a pose for the odometry-only measurement.
     * @param pose The odometry-only measurement
     */
    public final void setOdometry(Pose2d pose) {
        m_odometryObject.setPose(pose);
    }

    /**
     * Sets a pose for the pose estimator result, aka odometry + april tags
     * @param pose The robots estimated pose
     */
    public void setPoseEstimate(Pose2d pose) {
        m_field2d.setRobotPose(pose);
    }

    /**
     * Helper for converting a list of {@link Pose3d} to a list of {@link Pose2d}
     * @param poses The 3D poses
     * @return The converted 2D poses
     */
    public static List<Pose2d> pose3dTo2d(List<Pose3d> poses) {
        return
            poses.stream()
                .map(Pose3d::toPose2d)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Gets the underlying Field
     * @return the field
     */
    public Field2d getField2d() {
        return m_field2d;
    }

    /**
     * Gets the underlying Field
     * @return the field
     */
    public Field3d getField3d() {
        return m_field3d;
    }
}
