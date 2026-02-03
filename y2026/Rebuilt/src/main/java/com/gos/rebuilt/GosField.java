package com.gos.rebuilt;

import com.gos.lib.field.BaseGosField;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;

public class GosField extends BaseGosField {
    protected final FieldObject2d m_oldPoseEstimator;

    public GosField() {
        super(AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded));

        m_oldPoseEstimator = m_field2d.getObject("OldPoseEstimator");
    }

    public void setOldPoseEstimator(Pose2d pose) {
        m_oldPoseEstimator.setPose(pose);
    }
}
