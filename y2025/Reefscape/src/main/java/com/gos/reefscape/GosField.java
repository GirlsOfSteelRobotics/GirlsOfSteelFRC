package com.gos.reefscape;

import com.gos.lib.field.BaseGosField;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import org.littletonrobotics.frc2025.FieldConstants;

public class GosField extends BaseGosField {
    protected final FieldObject2d m_oldPoseEstimator;

    public GosField() {
        super(FieldConstants.TAG_LAYOUT);

        m_oldPoseEstimator = m_field2d.getObject("OldPoseEstimator");
    }

    public void setOldPoseEstimator(Pose2d pose) {
        m_oldPoseEstimator.setPose(pose);
    }
}
