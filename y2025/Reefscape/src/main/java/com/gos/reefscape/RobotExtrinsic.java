package com.gos.reefscape;

import com.gos.lib.properties.TunableTransform3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class RobotExtrinsic {

    public static final double ROBOT_WIDTH = Units.inchesToMeters(28);
    public static final double ROBOT_LENGTH = Units.inchesToMeters(28);

    public static final TunableTransform3d RIGHT_CAMERA = new TunableTransform3d(Constants.DEFAULT_CONSTANT_PROPERTIES, "CameraExtrinsics/Right", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(10.5),
            Units.inchesToMeters(-10.5),
            Units.inchesToMeters(9)),
        new Rotation3d(
            Math.toRadians(-5),
            Math.toRadians(-14),
            Math.toRadians(5))
    ));

    public static final TunableTransform3d LEFT_CAMERA = new TunableTransform3d(Constants.DEFAULT_CONSTANT_PROPERTIES, "CameraExtrinsics/Left", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(10.5),
            Units.inchesToMeters(12.5),
            Units.inchesToMeters(7)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-14),
            Math.toRadians(-4))
    ));

    public static final TunableTransform3d BACK_CAMERA = new TunableTransform3d(Constants.DEFAULT_CONSTANT_PROPERTIES, "CameraExtrinsics/Back", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(-11.5),
            Units.inchesToMeters(7.5),
            Units.inchesToMeters(7)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-33),
            Math.toRadians(176))
    ));





}
