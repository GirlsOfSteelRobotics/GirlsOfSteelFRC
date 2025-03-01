package com.gos.lib.field;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject3d;

import java.util.List;

/**
 * Helper object used to draw april tag information on a {@link edu.wpi.first.wpilibj.smartdashboard.Field2d}
 */
public class AprilTagCameraObject {
    private final FieldObject2d m_estimatedPose2d;
    private final FieldObject2d m_aprilTags2d;

    private final FieldObject3d m_estimatedPose3d;
    private final FieldObject3d m_aprilTags3d;

    /**
     * Constructor.
     * @param field The GOS field used to draw objects on
     * @param cameraName The name of the camera, used for prefixing the object names
     */
    public AprilTagCameraObject(BaseGosField field, String cameraName) {
        m_aprilTags2d = field.m_field2d.getObject(cameraName + " detected tags");
        m_estimatedPose2d = field.m_field2d.getObject(cameraName + " estimated pose");

        m_aprilTags3d = field.m_field3d.getObject(cameraName + " detected tags");
        m_estimatedPose3d = field.m_field3d.getObject(cameraName + " estimated pose");
    }

    /**
     * Provides the camera estimation to be drawn on the field.
     * @param estimatedPose The estimated pose of the robot
     * @param aprilTags The estimated poses of the april tags used to formulate this estimate
     */
    public void setCameraResult(Pose3d estimatedPose, List<Pose3d> aprilTags) {
        m_estimatedPose2d.setPose(estimatedPose.toPose2d());
        m_estimatedPose3d.setPose(estimatedPose);

        m_aprilTags2d.setPoses(BaseGosField.pose3dTo2d(aprilTags));
        m_aprilTags3d.setPoses(aprilTags);
    }

    /**
     * Clears the list of april tag and pose estimate objects.
     */
    public void clearCameraResult() {
        m_estimatedPose2d.setPoses();
        m_estimatedPose3d.setPoses();

        m_aprilTags2d.setPoses();
        m_aprilTags3d.setPoses();
    }
}
