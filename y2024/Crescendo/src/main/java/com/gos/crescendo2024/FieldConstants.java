package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class FieldConstants {

    public static final class Speaker {

        public static double fieldLength = Units.inchesToMeters(651.223);
        public static double fieldWidth = Units.inchesToMeters(323.277);

        /** Center of the speaker opening (blue alliance) */
        public static Pose2d centerSpeakerOpening =
            new Pose2d(0.0, fieldWidth - Units.inchesToMeters(104.0), new Rotation2d());
    }
}
