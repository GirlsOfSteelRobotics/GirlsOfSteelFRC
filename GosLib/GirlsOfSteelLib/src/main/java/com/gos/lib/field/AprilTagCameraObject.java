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

    public static class DebugConfig {
        private final boolean m_enable2d;
        private final boolean m_enable3d;
        private final boolean m_enableDetectedTags;

        public DebugConfig() {
            this(true, true, true);
        }

        public DebugConfig(boolean enable2d, boolean enable3d, boolean enableDetectedTags) {
            m_enable2d = enable2d;
            m_enable3d = enable3d;
            m_enableDetectedTags = enableDetectedTags;
        }
    }

    /**
     * Constructor.
     * @param field The GOS field used to draw objects on
     * @param cameraName The name of the camera, used for prefixing the object names
     */
    public AprilTagCameraObject(BaseGosField field, String cameraName, DebugConfig debugConfig) {
        if (debugConfig.m_enable2d) {
            m_estimatedPose2d = field.m_field2d.getObject(cameraName + " estimated pose");
            if (debugConfig.m_enableDetectedTags) {
                m_aprilTags2d = field.m_field2d.getObject(cameraName + " detected tags");
            } else {
                m_aprilTags2d = null;
            }
        } else {
            m_aprilTags2d = null;
            m_estimatedPose2d = null;
        }

        if (debugConfig.m_enable3d) {
            m_estimatedPose3d = field.m_field3d.getObject(cameraName + " estimated pose");
            if (debugConfig.m_enableDetectedTags) {
                m_aprilTags3d = field.m_field3d.getObject(cameraName + " detected tags");
            } else {
                m_aprilTags3d = null;
            }
        } else {
            m_aprilTags3d = null;
            m_estimatedPose3d = null;
        }
    }

    /**
     * Provides the camera estimation to be drawn on the field.
     * @param estimatedPose The estimated pose of the robot
     * @param aprilTags The estimated poses of the april tags used to formulate this estimate
     */
    public void setCameraResult(Pose3d estimatedPose, List<Pose3d> aprilTags) {
        if (m_estimatedPose2d != null) {
            m_estimatedPose2d.setPose(estimatedPose.toPose2d());
        }
        if (m_estimatedPose3d != null) {
            m_estimatedPose3d.setPose(estimatedPose);
        }

        if (m_aprilTags2d != null) {
            m_aprilTags2d.setPoses(BaseGosField.pose3dTo2d(aprilTags));
        }
        if (m_aprilTags3d != null) {
            m_aprilTags3d.setPoses(aprilTags);
        }
    }

    /**
     * Clears the list of april tag and pose estimate objects.
     */
    public void clearCameraResult() {
        if (m_estimatedPose2d != null) {
            m_estimatedPose2d.setPoses();
        }
        if (m_estimatedPose3d != null) {
            m_estimatedPose3d.setPoses();
        }

        if (m_aprilTags2d != null) {
            m_aprilTags2d.setPoses();
        }
        if (m_aprilTags3d != null) {
            m_aprilTags3d.setPoses();
        }
    }
}
