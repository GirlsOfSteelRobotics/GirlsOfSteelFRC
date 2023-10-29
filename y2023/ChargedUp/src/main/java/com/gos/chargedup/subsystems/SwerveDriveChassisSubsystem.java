package com.gos.chargedup.subsystems;


import com.gos.chargedup.Constants;
import com.gos.chargedup.GosSwerveAutoBuilder;
import com.gos.chargedup.SwerveDrivePublisher;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.WpiPidPropertyBuilder;
import com.pathplanner.lib.auto.BaseAutoBuilder;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;
import org.snobotv2.sim_wrappers.SwerveSimWrapper;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SwerveDriveChassisSubsystem extends BaseChassis {

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = 2.9;
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(180);

    private static final Translation2d FRONT_LEFT_LOCATION = new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2);
    private static final Translation2d FRONT_RIGHT_LOCATION = new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2);
    private static final Translation2d BACK_LEFT_LOCATION = new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2);
    private static final Translation2d BACK_RIGHT_LOCATION = new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2);

    // Creating my kinematics object using the module locations
    private static final SwerveDriveKinematics SWERVE_KINEMATICS = new SwerveDriveKinematics(
        FRONT_LEFT_LOCATION, FRONT_RIGHT_LOCATION, BACK_LEFT_LOCATION, BACK_RIGHT_LOCATION
    );

    private final SwerveDriveModules m_frontLeft;
    private final SwerveDriveModules m_backLeft;
    private final SwerveDriveModules m_frontRight;
    private final SwerveDriveModules m_backRight;

    private final SwerveDriveModules[] m_modules;

    private final SwerveDriveOdometry m_odometry;
    private final SwerveDrivePoseEstimator m_poseEstimator;

    private final SwerveDrivePublisher m_swervePublisher;


    private final PIDController m_xTranslationPidController;
    private final PIDController m_yTranslationPidController;
    private final PIDController m_rotationPidController;
    private final PidProperty m_xTranslationPidProperties;
    private final PidProperty m_yTranslationPidProperties;
    private final PidProperty m_rotationPidProperties;

    private SwerveSimWrapper m_simulator;

    public SwerveDriveChassisSubsystem() {
        m_frontLeft = new SwerveDriveModules("FL", Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,  -Math.PI / 2);
        m_frontRight = new SwerveDriveModules("FR", Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH, 0);
        m_backLeft = new SwerveDriveModules("BL", Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH, Math.PI);
        m_backRight = new SwerveDriveModules("BR", Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,Math.PI / 2);
        m_modules = new SwerveDriveModules[]{m_frontLeft, m_frontRight, m_backLeft, m_backRight};

        m_odometry = new SwerveDriveOdometry(
            SWERVE_KINEMATICS, m_gyro.getRotation2d(),
            getModulePositions(), new Pose2d());
        m_poseEstimator = new SwerveDrivePoseEstimator(SWERVE_KINEMATICS, m_gyro.getRotation2d(), getModulePositions(), new Pose2d());

        m_swervePublisher = new SwerveDrivePublisher();

        m_xTranslationPidController = new PIDController(0, 0, 0);
        m_xTranslationPidProperties = new WpiPidPropertyBuilder("SwerveTranslation", false, m_xTranslationPidController)
            .addP(0)
            .addD(0)
            .build();

        m_yTranslationPidController = new PIDController(0, 0, 0);
        m_yTranslationPidProperties = new WpiPidPropertyBuilder("SwerveTranslation", false, m_yTranslationPidController)
            .addP(0)
            .addD(0)
            .build();

        m_rotationPidController = new PIDController(0, 0, 0);
        m_rotationPidProperties = new WpiPidPropertyBuilder("SwerveRotation", false, m_rotationPidController)
            .addP(0)
            .addD(0)
            .build();

        PPSwerveControllerCommand.setLoggingCallbacks(
            m_field::setTrajectory,
            m_field::setTrajectorySetpoint,
            this::logTrajectoryChassisSetpoint,
            this::logTrajectoryErrors
        );

        if (RobotBase.isSimulation()) {
            List<SwerveModuleSimWrapper> moduleSims = List.of(
                m_frontLeft.getSimWrapper(),
                m_frontRight.getSimWrapper(),
                m_backLeft.getSimWrapper(),
                m_backRight.getSimWrapper());
            m_simulator = new SwerveSimWrapper(WHEEL_BASE, TRACK_WIDTH, 64.0, 1.0, moduleSims, new CtrePigeonImuWrapper(m_gyro));
        }
    }

    private void logTrajectoryChassisSetpoint(ChassisSpeeds chassisSpeeds) {
        m_trajectoryVelocitySetpointX.setNumber(chassisSpeeds.vxMetersPerSecond);
        m_trajectoryVelocitySetpointY.setNumber(chassisSpeeds.vyMetersPerSecond);
        m_trajectoryVelocitySetpointAngle.setNumber(chassisSpeeds.omegaRadiansPerSecond);
    }

    @Override
    protected void lockDriveTrain() {
        m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
        m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_backLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        m_backRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    }

    @Override
    protected void unlockDriveTrain() {
        // TODO implement
    }

    public final SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] modulePositions = new SwerveModulePosition[4];
        for (int i = 0; i < 4; i += 1) {
            modulePositions[i] = m_modules[i].getPosition();
        }
        return modulePositions;
    }

    public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] modulePositions = new SwerveModuleState[4];
        for (int i = 0; i < 4; i += 1) {
            modulePositions[i] = m_modules[i].getState();
        }
        return modulePositions;
    }

    private SwerveModuleState[] getModuleDesiredStates() {
        SwerveModuleState[] modulePositions = new SwerveModuleState[4];
        for (int i = 0; i < 4; i += 1) {
            modulePositions[i] = m_modules[i].getDesiredState();
        }
        return modulePositions;
    }

    @Override
    public void periodic() {
        m_xTranslationPidProperties.updateIfChanged();
        m_yTranslationPidProperties.updateIfChanged();
        m_rotationPidProperties.updateIfChanged();

        for (SwerveDriveModules module : m_modules) {
            module.periodic();
        }

        SwerveModulePosition[] modulePositions = getModulePositions();
        m_odometry.update(m_gyro.getRotation2d(), modulePositions);
        m_poseEstimator.update(m_gyro.getRotation2d(), modulePositions);

        m_field.setOdometry(m_odometry.getPoseMeters());
        m_field.setPoseEstimate(m_poseEstimator.getEstimatedPosition());

        SwerveModuleState[] moduleStates = getModuleStates();
        SwerveModuleState[] desiredStates = getModuleDesiredStates();
        m_swervePublisher.setMeasuredStates(moduleStates);
        m_swervePublisher.setDesiredStates(desiredStates);
        m_swervePublisher.setRobotRotation(getPose().getRotation());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public void setSpeeds(ChassisSpeeds speedsInp) {
        SwerveModuleState[] moduleStates = SWERVE_KINEMATICS.toSwerveModuleStates(speedsInp);
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, MAX_TRANSLATION_SPEED);
        setModuleStates(moduleStates);
    }

    public void setModuleStates(SwerveModuleState... moduleStates) {
        for (int i = 0; i < 4; i++) {
            m_modules[i].setDesiredState(moduleStates[i]);
        }
    }

    public void setChassisSpeedsPercent(double wheelPercent, double azimuthPercent) {
        for (SwerveDriveModules module: m_modules) {
            module.setSpeedPercent(azimuthPercent, wheelPercent);
        }
    }

    public void setModuleState(int moduleId, double degrees, double velocity) {
        m_modules[moduleId].setDesiredState(new SwerveModuleState(velocity, Rotation2d.fromDegrees(degrees)));
    }

    @Override
    public void resetStickyFaultsChassis() {
        // TODO implement
    }

    @Override
    protected BaseAutoBuilder createPathPlannerAutoBuilder(Map<String, Command> eventMap, Consumer<Pose2d> poseSetter) {
        return new GosSwerveAutoBuilder(
            this::getPose,
            poseSetter,
            m_xTranslationPidController,
            m_yTranslationPidController,
            m_rotationPidController,
            this::setSpeeds,
            eventMap,
            this
        );
    }

    @Override
    public Pose2d getPose() {
        return m_poseEstimator.getEstimatedPosition();
    }

    @Override
    public void stop() {
        // TODO implement
    }

    @Override
    public void turnToAngle(double angleGoal) {
        // TODO implement
    }

    @Override
    public void autoEngage() {
        // TODO implement
    }

    @Override
    public void resetOdometry(Pose2d pose2d) {
        m_odometry.resetPosition(m_gyro.getRotation2d(), getModulePositions(), pose2d);
        m_poseEstimator.resetPosition(m_gyro.getRotation2d(), getModulePositions(), pose2d);
    }

    @Override
    public CommandBase createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse) {
        // TODO implement
        return new InstantCommand();
    }

    @Override
    public CommandBase createSyncOdometryWithPoseEstimatorCommand() {
        return runOnce(() ->  m_odometry.resetPosition(m_gyro.getRotation2d(), getModulePositions(), m_poseEstimator.getEstimatedPosition()))
        .withName("Sync Odometry /w Pose");
    }

    @Override
    public CommandBase createSelfTestMotorsCommand() {
        // TODO implement
        return new SequentialCommandGroup();
    }

    public CommandBase commandSetModuleState(int moduleId, double degrees, double velocity) {
        return this.run(() -> setModuleState(moduleId, degrees, velocity)).withName("Module " + moduleId + "(" + degrees + ", " + velocity + ")");
    }

    public CommandBase commandSetChassisSpeed(ChassisSpeeds chassisSp) {
        return this.run(() -> setSpeeds(chassisSp)).withName("Set Chassis Speeds" + chassisSp);
    }

    public CommandBase commandLockDrivetrain() {
        return this.run(this::lockDriveTrain).withName("Lock Wheels Command");
    }

    public CommandBase commandSetPercentSpeeds(double percentAzimuth, double percentWheel) {
        return this.run(() -> setChassisSpeedsPercent(percentWheel, percentAzimuth));
    }

}
