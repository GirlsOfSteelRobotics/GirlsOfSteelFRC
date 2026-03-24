package com.gos.rebuilt;

import com.gos.lib.properties.TunableTransform3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class RobotExtrinsic {


    //ALL INCHES
    //26.625\ on intake side

    //13.3125 is center -  1.625 =11.6875

    //26.5 on nonintake/nonshooter - 1.625
    //13.25 is center - 1.625 = 11.625


    //1.625 distance from edge perpen to shooter
    //1.625 distance

    //height is 12.125


    //ROTATION!!


    public static final double ROBOT_WIDTH = Units.inchesToMeters(28);
    public static final double ROBOT_LENGTH = Units.inchesToMeters(28);

    public static final TunableTransform3d SHOOTER_CAMERA = new TunableTransform3d(true, "CameraExtrinsics/ShooterCamera", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(-9.75),
            Units.inchesToMeters(-10),
            Units.inchesToMeters(8)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-30),
            Math.toRadians(-157))
    ));
    public static final TunableTransform3d OTHER_CAMERA = new TunableTransform3d(true, "CameraExtrinsics/OtherCamera", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(-9.25),
            Units.inchesToMeters(10),
            Units.inchesToMeters(8)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-30),
            Math.toRadians(166))
    ));








}
