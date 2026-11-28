package com.gos.rebuilt;

import com.gos.lib.field.BaseGosField;
import org.wpilib.fields.Field;
import org.wpilib.fields.Fields;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.smartdashboard.FieldObject2d;

public class GosField extends BaseGosField {
    protected final FieldObject2d m_oldPoseEstimator;

    public GosField() {
        super(Field.loadField(Fields.FRC_2026_REBUILT_WELDED));

        m_oldPoseEstimator = m_field2d.getObject("OldPoseEstimator");
    }

    public void setOldPoseEstimator(Pose2d pose) {
        m_oldPoseEstimator.setPose(pose);
    }
}
