package com.gos.swerve2023.subsystems;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
import com.gos.swerve2023.Constants;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.phoenix5.Pigeon2Wrapper;

public class ChassisSubsystem extends SubsystemBase {

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = Units.feetToMeters(13);
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(360);


    private final RevSwerveChassis m_swerveDrive;
    private final WPI_Pigeon2 m_gyro;

    private final Field2d m_field;

    public ChassisSubsystem() {
        m_gyro = new WPI_Pigeon2(Constants.PIGEON_PORT);
        m_gyro.configFactoryDefault();

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,
            Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH,
            Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH,
            Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,
            RevSwerveModuleConstants.DriveMotorTeeth.T14,
            WHEEL_BASE, TRACK_WIDTH,
            MAX_TRANSLATION_SPEED,
            MAX_ROTATION_SPEED
        );
        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));

        m_field = new Field2d();
        SmartDashboard.putData("Field", m_field);
    }

    @Override
    public void periodic() {
        m_swerveDrive.periodic();
        m_field.setRobotPose(m_swerveDrive.getEstimatedPosition());
    }

    @Override
    public void simulationPeriodic() {
        m_swerveDrive.updateSimulator();
    }

    public void teleopDrive(double xPercent, double yPercent, double rotPercent, boolean fieldRelative) {
        m_swerveDrive.driveWithJoysticks(xPercent, yPercent, rotPercent, fieldRelative);
    }
}
