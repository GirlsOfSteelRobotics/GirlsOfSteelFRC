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
    public static final LinearVelocity TrenchMaxVelocity = Units.MetersPerSecond.of(0.914);
    public static final Distance StartWallOffset = Units.Meters.of(1.28);

    public static final class Poses {
        public static final Pose2d RightStartPoint = new Pose2d(3.584, 1.672, Rotation2d.fromRadians(3.142));
        public static final Pose2d centerStartPoint = new Pose2d(3.584, 3.981, Rotation2d.fromRadians(3.142));
        public static final Pose2d leftShootPoint = new Pose2d(2.523, 6.373, Rotation2d.fromRadians(2.335));
        public static final Pose2d leftStartFaceOppSide = new Pose2d(3.584, 7.609, Rotation2d.kZero);
        public static final Pose2d leftStartPoint = new Pose2d(3.564, 6.385, Rotation2d.fromRadians(3.138));
        public static final Pose2d outpost = new Pose2d(0.419, 0.675, Rotation2d.fromRadians(1.571));
        public static final Pose2d rightShootPoint = new Pose2d(2.675, 1.551, Rotation2d.fromRadians(-2.412));
        public static final Pose2d rightStartFaceOppSide = new Pose2d(3.584, 0.392, Rotation2d.fromRadians(6.283));

        private Poses() {}
    }

    private ChoreoVars() {}
}