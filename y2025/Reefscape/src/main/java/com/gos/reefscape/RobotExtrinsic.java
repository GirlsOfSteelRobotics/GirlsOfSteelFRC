package com.gos.reefscape;

import com.gos.lib.properties.TunableTransform3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class RobotExtrinsic {

    public static final double ROBOT_WIDTH = Units.inchesToMeters(28);
    public static final double ROBOT_LENGTH = Units.inchesToMeters(28);

    public static final TunableTransform3d FRONT_CAMERA = new TunableTransform3d(Constants.DEFAULT_CONSTANT_PROPERTIES, "CameraExtrinsics/Front", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(0.5),
            Units.inchesToMeters(0),
            Units.inchesToMeters(6.25)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-13),
            Math.toRadians(0))
    ));

    public static final TunableTransform3d BACK_CAMERA = new TunableTransform3d(Constants.DEFAULT_CONSTANT_PROPERTIES, "CameraExtrinsics/Back", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(7.33),
            Units.inchesToMeters(12.75),
            Units.inchesToMeters(6.5)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-32),
            Math.toRadians(0))
    ));





}
