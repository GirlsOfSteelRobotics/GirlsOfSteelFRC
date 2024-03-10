package com.gos.swerve2023;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;

import java.util.List;

public class GoSField {
    private final Field2d m_field;
    private final FieldObject2d m_currentTrajectoryObject;
    private final FieldObject2d m_trajectorySetpoint;
    private final FieldObject2d m_odometryObject;
    private final FieldObject2d m_detectedNotePoses;

    private final FieldObject2d m_futurePosition;

    public GoSField() {
        m_field = new Field2d();

        m_currentTrajectoryObject = m_field.getObject("Trajectory");
        m_detectedNotePoses = m_field.getObject("Notes");
        m_trajectorySetpoint = m_field.getObject("TrajectoryTargetPose");
        m_odometryObject = m_field.getObject("OldOdometry");
        m_futurePosition = m_field.getObject("futurePosition");
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
