// spotless:off
package com.gos.rebuilt.choreo_gen;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.units.Units;
import org.wpilib.units.measure.*;

/**
 * Generated file containing variables defined in Choreo.
 * DO NOT MODIFY THIS FILE YOURSELF; instead, change these values
 * in the Choreo GUI.
 */
public final class ChoreoVars {
    public static final LinearVelocity DefaultMaxVel = Units.MetersPerSecond.of(2.4384);
    public static final Distance FieldLength = Units.Meters.of(16.2306);
    public static final Distance FieldWidth = Units.Meters.of(8.001);
    public static final Distance HalfBumperSize = Units.Meters.of(0.37465);
    public static final Distance StartWallOffset = Units.Meters.of(1.651);
    public static final Distance StartingLine = Units.Meters.of(3.5644486);
    public static final LinearVelocity TrenchMaxVelocity = Units.MetersPerSecond.of(2.1336);

    public static final class Poses {
        public static final Pose2d LeftBumpEntrance = new Pose2d(3.5162644, 5.5644698, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d LeftBumpExit = new Pose2d(5.7159853, 5.5644698, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d LeftTrenchEntrance = new Pose2d(3.4801512, 7.3569459, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d LeftTrenchExit = new Pose2d(5.8154631, 7.3569459, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d RightBumpEntrance = new Pose2d(3.5162637, 2.499052, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d RightBumpExit = new Pose2d(5.6592183, 2.4848602, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d RightStartPoint = new Pose2d(3.5644486, 1.651, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d RightTrenchEntrance = new Pose2d(3.4801512, 0.6440541, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d RightTrenchExit = new Pose2d(5.8154631, 0.6440541, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d centerShoot = new Pose2d(1.6571456, 3.989186, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d centerStartPoint = new Pose2d(3.5644486, 4.0005, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d depot = new Pose2d(0.8581052, 5.9450612, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d leftShootPoint = new Pose2d(2.5228422, 6.3733997, Rotation2d.fromRadians(2.3349209));
        public static final Pose2d leftStartFaceOppSide = new Pose2d(3.5644486, 6.35, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d leftStartPoint = new Pose2d(3.5644486, 6.35, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d middleShootPoint = new Pose2d(1.8571456, 4.0000892, Rotation2d.fromRadians(3.1415927));
        public static final Pose2d outpost = new Pose2d(0.418637, 0.6745284, Rotation2d.fromRadians(1.5707963));
        public static final Pose2d rightShootPoint = new Pose2d(2.6754, 1.551, Rotation2d.fromRadians(-2.4116929));
        public static final Pose2d rightStartFaceOppSide = new Pose2d(3.5644486, 1.651, Rotation2d.fromRadians(0));
    }
}
// spotless:on
