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

    public static final TunableTransform3d RIGHT_CAMERA = new TunableTransform3d(Constants.DEFAULT_CONSTANT_PROPERTIES, "CameraExtrinsics/Right", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(11.6875),
            Units.inchesToMeters(11.625),
            Units.inchesToMeters(12.125)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(58.05),
            Math.toRadians(160.67))
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