package com.gos.rebuilt.choreo_gen;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.*;

/**
 * Generated file containing variables defined in Choreo.
 * DO NOT MODIFY THIS FILE YOURSELF; instead, change these values
 * in the Choreo GUI.
 */
public final class ChoreoVars {
    public static final LinearVelocity DefaultMaxVel = Units.MetersPerSecond.of(1.524);
    public static final Distance StartWallOffset = Units.Meters.of(1.651);
    public static final LinearVelocity TrenchMaxVelocity = Units.MetersPerSecond.of(0.914);
    public static final Distance FieldWidth = Units.Meters.of(8.001);
    public static final Distance FieldLength = Units.Meters.of(16.231);
    public static final Distance HalfBumperSize = Units.Meters.of(0.375);
    public static final Distance StartingLine = Units.Meters.of(3.564);

    public static final class Poses {
        public static final Pose2d RightStartPoint = new Pose2d(3.564, 1.651, Rotation2d.fromRadians(3.142));
        public static final Pose2d centerStartPoint = new Pose2d(3.564, 4, Rotation2d.fromRadians(3.142));
        public static final Pose2d leftShootPoint = new Pose2d(2.523, 6.373, Rotation2d.fromRadians(2.335));
        public static final Pose2d leftStartFaceOppSide = new Pose2d(3.564, 6.35, Rotation2d.kZero);
        public static final Pose2d leftStartPoint = new Pose2d(3.564, 6.35, Rotation2d.fromRadians(3.142));
        public static final Pose2d outpost = new Pose2d(0.419, 0.675, Rotation2d.fromRadians(1.571));
        public static final Pose2d rightShootPoint = new Pose2d(2.675, 1.551, Rotation2d.fromRadians(-2.412));
        public static final Pose2d rightStartFaceOppSide = new Pose2d(3.564, 1.651, Rotation2d.kZero);
        public static final Pose2d LeftTrenchEnterance = new Pose2d(3.48, 7.357, Rotation2d.fromRadians(3.142));
        public static final Pose2d LeftTrenchExit = new Pose2d(5.815, 7.357, Rotation2d.fromRadians(3.142));
        public static final Pose2d RightTrenchEnterance = new Pose2d(3.48, 0.644, Rotation2d.fromRadians(3.142));
        public static final Pose2d RightTrenchExit = new Pose2d(5.815, 0.644, Rotation2d.fromRadians(3.142));
        public static final Pose2d depot = new Pose2d(0.858, 5.945, Rotation2d.fromRadians(3.142));
        public static final Pose2d centerShoot = new Pose2d(1.657, 3.989, Rotation2d.fromRadians(3.142));

        private Poses() {}
    }

    private ChoreoVars() {}
}