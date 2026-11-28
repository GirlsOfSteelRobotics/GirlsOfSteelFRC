package frc.robot;


import com.gos.lib.properties.TunableTransform3d;
import org.wpilib.math.geometry.Rotation3d;
import org.wpilib.math.geometry.Transform3d;
import org.wpilib.math.geometry.Translation3d;
import org.wpilib.math.util.Units;


public class RobotExtrinsic {

    public static final double ROBOT_WIDTH = Units.inchesToMeters(28);
    public static final double ROBOT_LENGTH = Units.inchesToMeters(28);


    public static final TunableTransform3d FRONT_RIGHT_CAMERA = new TunableTransform3d(true, "CameraExtrinsics/ShooterCamera", new Transform3d(
        new Translation3d(
            Units.inchesToMeters(-9.75),
            Units.inchesToMeters(-10),
            Units.inchesToMeters(8)),
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(-30),
            Math.toRadians(-157))
        ));

}
