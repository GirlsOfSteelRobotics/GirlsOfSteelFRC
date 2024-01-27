package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class FieldConstants {

    public static final Translation2d AMP_CENTER =
        new Translation2d(Units.inchesToMeters(72.455), Units.inchesToMeters(322.996));

    public static final double FIELD_LENGTH = Units.inchesToMeters(651.223);
    public static final double FIELD_WIDTH = Units.inchesToMeters(323.277);
    public static final class Speaker {


        /** Center of the speaker opening (blue alliance) */
        public static final Pose2d CENTER_SPEAKER_OPENING =
            new Pose2d(0.0, FIELD_WIDTH - Units.inchesToMeters(104.0), new Rotation2d());
    }

    public static final class StagingLocations {
        public static double centerlineX = FIELD_LENGTH / 2.0;

        // need to update
        public static double centerlineFirstY = Units.inchesToMeters(29.638);
        public static double centerlineSeparationY = Units.inchesToMeters(66);
        public static double spikeX = Units.inchesToMeters(114);
        // need
        public static double spikeFirstY = Units.inchesToMeters(161.638);
        public static double spikeSeparationY = Units.inchesToMeters(57);

        public static Translation2d[] centerlineTranslations = new Translation2d[5];
        public static Translation2d[] spikeTranslations = new Translation2d[3];

        static {
            for (int i = 0; i < centerlineTranslations.length; i++) {
                centerlineTranslations[i] =
                    new Translation2d(centerlineX, centerlineFirstY + (i * centerlineSeparationY));
            }
        }

        static {
            for (int i = 0; i < spikeTranslations.length; i++) {
                spikeTranslations[i] = new Translation2d(spikeX, spikeFirstY + (i * spikeSeparationY));
            }
        }
    }
}
