package com.gos.crescendo2024;

import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import java.lang.reflect.Field;

public class AllianceFlipper {

    public static Translation2d flip(Translation2d translation) {
        return new Translation2d(
            FieldConstants.FIELD_LENGTH - translation.getX(),
            translation.getY()
        );
    }

    public static Rotation2d flip(Rotation2d rotation) {
        return rotation.times(-1);
    }

    public static Pose2d flip(Pose2d pose) {
        return new Pose2d(
            flip(pose.getTranslation()),
            flip(pose.getRotation())
        );
    }

    public static Translation2d maybeFlip(Translation2d translation) {
        if (GetAllianceUtil.isRedAlliance()) {
            return flip(translation);
        }
        return translation;
    }

    public static Pose2d maybeFlip(Pose2d pose) {
        if (GetAllianceUtil.isRedAlliance()) {
            return flip(pose);
        }
        return pose;
    }
}
