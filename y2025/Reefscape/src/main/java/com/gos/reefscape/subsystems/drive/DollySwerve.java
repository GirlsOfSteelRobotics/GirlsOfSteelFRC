package com.gos.reefscape.subsystems.drive;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.rev.RevMotorControllerModel;
import com.gos.lib.rev.RevMotorModel;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.config.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.config.RevSwerveChassisConstantsBuilder;
import com.gos.lib.rev.swerve.config.SwerveGearingKit;
import com.gos.lib.swerve.SwerveDrivePublisher;
import com.gos.reefscape.GosField;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.json.simple.parser.ParseException;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;
import com.gos.reefscape.Constants;

import java.io.IOException;

public class DollySwerve implements Subsystem, GOSSwerveDrive {
    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 5.4; // Theoretically 6
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(720);


    private final RevSwerveChassis m_swerveDrive;
    private final Pigeon2 m_gyro;
    private final GosField m_field;

    private final SwerveDrivePublisher m_swerveDrivePublisher;

    public DollySwerve() {
        m_gyro = new Pigeon2(Constants.PIGEON_ID);
        m_gyro.getConfigurator().apply(new Pigeon2Configuration());

        RevMotorModel driveMotorModel = RevMotorModel.VORTEX;
        RevMotorControllerModel driveMotorControllerModel = RevMotorControllerModel.SPARK_FLEX;

        RevSwerveChassisConstantsBuilder chassisBuilder = new RevSwerveChassisConstantsBuilder()
            .withFrontLeftConfig(Constants.FRONT_LEFT_DRIVE_MOTOR_ID, Constants.FRONT_LEFT_STEER_MOTOR_ID)
            .withFrontRightConfig(Constants.FRONT_RIGHT_DRIVE_MOTOR_ID, Constants.FRONT_RIGHT_STEER_MOTOR_ID)
            .withRearLeftConfig(Constants.BACK_LEFT_DRIVE_MOTOR_ID, Constants.BACK_LEFT_STEER_MOTOR_ID)
            .withRearRightConfig(Constants.BACK_RIGHT_DRIVE_MOTOR_ID, Constants.BACK_RIGHT_STEER_MOTOR_ID)
            .withDrivingMotorType(driveMotorModel, driveMotorControllerModel)
            .withTrackwidth(TRACK_WIDTH)
            .withWheelBase(WHEEL_BASE)
            .withMaxTranslationSpeed(MAX_TRANSLATION_SPEED)
            .withMaxRotationSpeed(MAX_ROTATION_SPEED)
            .withGearing(SwerveGearingKit.EXTRA_HIGH_1);

        RevSwerveChassisConstants swerveConstants = chassisBuilder.build(false);

        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));

        m_field = new GosField();
        SmartDashboard.putData("Field", m_field.getField2d());
        SmartDashboard.putData("Field3d", m_field.getField3d());

        setupPathPlanner();

        m_swerveDrivePublisher = new SwerveDrivePublisher();
    }

    private void setupPathPlanner() {
        try {
            RobotConfig config = RobotConfig.fromGUISettings();

            AutoBuilder.configure(
                this::getPose,
                this::resetPose,
                this::getChassisSpeed,
                this::setChassisSpeed,
                new PPHolonomicDriveController(
                    new PIDConstants(5, 0, 0),
                    new PIDConstants(10, 0, 0)),
                config,
                GetAllianceUtil::isRedAlliance,
                this
            );
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Pose2d getPose() {
        return m_swerveDrive.getEstimatedPosition();
    }

    @Override
    public final void resetPose(Pose2d pose2d) {
        m_swerveDrive.resetOdometry(pose2d);
    }

    public void setChassisSpeed(ChassisSpeeds speed) {
        m_swerveDrive.setChassisSpeeds(speed);
    }

    public ChassisSpeeds getChassisSpeed() {
        return m_swerveDrive.getChassisSpeed();
    }

    @Override
    public void periodic() {
        m_swerveDrive.periodic();

    }

    @Override
    public void driveWithJoystick(double xJoystick, double yJoystick, double rotationalJoystick) {
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(
            xJoystick * MAX_TRANSLATION_SPEED,
            yJoystick * MAX_TRANSLATION_SPEED,
            rotationalJoystick * MAX_ROTATION_SPEED);
        setChassisSpeed(chassisSpeeds);
    }
}
