package com.gos.reefscape;

import com.gos.lib.field.BaseGosField;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

public class GosField extends BaseGosField {
    public GosField() {
        super(AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField));
    }
}
