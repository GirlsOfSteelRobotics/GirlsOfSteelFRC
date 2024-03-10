package com.gos.swerve2023.subsystems;


import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
import com.gos.swerve2023.Constants;
import com.gos.swerve2023.GoSField;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;

public class ChassisSubsystem extends SubsystemBase {
    private static final GosDoubleProperty TEST_AZIMUTH_ANGLE = new GosDoubleProperty(false, "SwerveTest.AzimuthAngle", 0);
    private static final GosDoubleProperty TEST_VELOCITY = new GosDoubleProperty(false, "SwerveTest.WheelVelocity", 0);

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = Units.feetToMeters(13);
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(360);


    private final RevSwerveChassis m_swerveDrive;
    private final Pigeon2 m_gyro;

    private final GoSField m_field;

    public ChassisSubsystem() {
        m_gyro = new Pigeon2(Constants.PIGEON_PORT);
        m_gyro.getConfigurator().apply(new Pigeon2Configuration());

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,
            Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH,
            Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH,
            Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,
            RevSwerveModuleConstants.DriveMotor.NEO,
            RevSwerveModuleConstants.DriveMotorPinionTeeth.T14,
            RevSwerveModuleConstants.DriveMotorSpurTeeth.T22,
            WHEEL_BASE,
            TRACK_WIDTH,
            MAX_TRANSLATION_SPEED,
            MAX_ROTATION_SPEED);
        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));

        m_field = new GoSField();
        SmartDashboard.putData("Field", m_field.getSendable());


        AutoBuilder.configureHolonomic(
            this::getPose,
            this::resetOdometry,
            this::getChassisSpeed,
            this::setChassisSpeed,
            new HolonomicPathFollowerConfig(
                new PIDConstants(5, 0, 0),
                new PIDConstants(10, 0, 0),
                MAX_TRANSLATION_SPEED,
                WHEEL_BASE,
                new ReplanningConfig(),
                0.02),
            GetAllianceUtil::isRedAlliance,
            this
        );

        PathPlannerLogging.setLogActivePathCallback(m_field::setTrajectory);
        PathPlannerLogging.setLogTargetPoseCallback(m_field::setTrajectorySetpoint);

    }

    public void resetOdometry(Pose2d pose2d) {
        m_swerveDrive.resetOdometry(pose2d);
    }

    public void setChassisSpeed(ChassisSpeeds speed) {
        m_swerveDrive.setChassisSpeeds(speed);
    }

    public ChassisSpeeds getChassisSpeed() {
        return m_swerveDrive.getChassisSpeed();
    }

    public void clearStickyFaults() {
        m_swerveDrive.clearStickyFaults();
    }

    @Override
    public void periodic() {
        m_swerveDrive.periodic();
        m_field.setPoseEstimate(m_swerveDrive.getEstimatedPosition());
        m_field.setOdometry(m_swerveDrive.getOdometryPosition());
    }

    @Override
    public void simulationPeriodic() {
        m_swerveDrive.updateSimulator();
    }

    public void teleopDrive(double xPercent, double yPercent, double rotPercent, boolean fieldRelative) {
        m_swerveDrive.driveWithJoysticks(xPercent, yPercent, rotPercent, fieldRelative);
    }

    public Pose2d getPose() {
        return m_swerveDrive.getEstimatedPosition();
    }

    private void resetGyro() {
        Pose2d currentPose = getPose();
        resetOdometry(new Pose2d(currentPose.getX(), currentPose.getY(), Rotation2d.fromDegrees(0)));
    }

    public Command createTestSingleModleCommand(int moduleId) {
        return run(() -> m_swerveDrive.setModuleState(moduleId, TEST_AZIMUTH_ANGLE.getValue(), TEST_VELOCITY.getValue())).withName("Test " + m_swerveDrive.getModuleName(moduleId));
    }

    public Command createResetGyroCommand() {
        return run(this::resetGyro)
            .ignoringDisable(true)
            .withName("Reset Gyro");
    }
}
