package com.gos.crescendo2024;

import com.gos.lib.field.BaseGosField;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;

import java.util.List;

public class GoSField extends BaseGosField {
    private final FieldObject2d m_detectedNotePoses;
    private final FieldObject2d m_futurePosition;
    private final FieldObject2d m_shootingPolygon;

    public GoSField() {
        super(FieldConstants.TAG_LAYOUT);
        m_detectedNotePoses = m_field2d.getObject("Notes");
        m_futurePosition = m_field2d.getObject("futurePosition");
        m_shootingPolygon = m_field2d.getObject("shootingPolygon");
    }

    public void setFuturePose(Pose2d pose) {
        m_futurePosition.setPose(pose);
    }

    public void drawNotePoses(List<Pose2d> poses) {
        m_detectedNotePoses.setPoses(poses);
    }

    public void setShootingPolygon(List<Pose2d> poses) {
        m_shootingPolygon.setPoses(poses);
    }
}
