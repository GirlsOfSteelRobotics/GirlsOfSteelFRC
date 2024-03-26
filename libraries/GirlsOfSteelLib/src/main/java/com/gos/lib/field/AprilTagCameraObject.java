package com.gos.lib.field;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject3d;

import java.util.ArrayList;
import java.util.List;

public class AprilTagCameraObject {
    private final Transform3d m_robotToCamera;

    private final FieldObject2d m_estimatedPose2d;
    private final FieldObject2d m_aprilTags2d;

    private final FieldObject3d m_estimatedPose3d;
    private final FieldObject3d m_aprilTags3d;

    public AprilTagCameraObject(BaseGosField field, String cameraName, Transform3d robotToCamera) {
        m_aprilTags2d = field.m_field2d.getObject(cameraName + " detected tags");
        m_estimatedPose2d = field.m_field2d.getObject(cameraName + " estimated pose");

        m_aprilTags3d = field.m_field3d.getObject(cameraName + " detected tags");
        m_estimatedPose3d = field.m_field3d.getObject(cameraName + " estimated pose");

        m_robotToCamera = robotToCamera;
    }

    public void setCameraResult(Pose3d estimatedPose, List<Pose3d> aprilTags) {
        m_estimatedPose2d.setPose(estimatedPose.toPose2d());
        m_estimatedPose3d.setPose(estimatedPose);

        m_aprilTags2d.setPoses(BaseGosField.pose3dTo2d(aprilTags));
        m_aprilTags3d.setPoses(aprilTags);
    }

    public void clearCameraResult() {
        m_estimatedPose2d.setPoses();
        m_estimatedPose3d.setPoses();

        m_aprilTags2d.setPoses();
        m_aprilTags3d.setPoses();
    }
}
