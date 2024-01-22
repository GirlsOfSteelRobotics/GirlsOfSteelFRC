package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class FieldConstants {

    public static final Translation2d AMP_CENTER =
        new Translation2d(Units.inchesToMeters(72.455), Units.inchesToMeters(322.996));

    public static final class Speaker {

        public static final double FIELD_LENGTH = Units.inchesToMeters(651.223);
        public static final double FIELD_WIDTH = Units.inchesToMeters(323.277);

        /** Center of the speaker opening (blue alliance) */
        public static final Pose2d CENTER_SPEAKER_OPENING =
            new Pose2d(0.0, FIELD_WIDTH - Units.inchesToMeters(104.0), new Rotation2d());
    }
}
