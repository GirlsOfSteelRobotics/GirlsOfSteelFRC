package com.gos.reefscape;

import com.gos.lib.properties.TunableTransform3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class RobotExtrinsic {

    public static final double ROBOT_WIDTH = Units.inchesToMeters(28);
    public static final double ROBOT_LENGTH = Units.inchesToMeters(28);

    public static final TunableTransform3d FRONT_CAMERA = new TunableTransform3d(false, "CameraExtrinsics/Front", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(14.0),
            Units.inchesToMeters(1),
            .235),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-22),
            Math.toRadians(11))
    ));

    public static final TunableTransform3d BACK_CAMERA = new TunableTransform3d(false, "CameraExtrinsics/Back", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(-8),
            Units.inchesToMeters(5.5),
            Units.inchesToMeters(10.0)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-24.0),
            Math.toRadians(175.0))
    ));





}
