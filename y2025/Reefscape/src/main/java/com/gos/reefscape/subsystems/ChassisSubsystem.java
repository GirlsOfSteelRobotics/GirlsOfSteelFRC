package com.gos.reefscape.subsystems;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.gos.lib.field.AprilTagCameraObject.DebugConfig;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.pathing.TunablePathConstraints;
import com.gos.lib.phoenix6.alerts.BasePhoenix6Alerts;
import com.gos.lib.phoenix6.alerts.CancoderAlerts;
import com.gos.lib.phoenix6.alerts.PigeonAlerts;
import com.gos.lib.phoenix6.alerts.TalonFxAlerts;
import com.gos.lib.phoenix6.properties.pid.Phoenix6TalonPidPropertyBuilder;
import com.gos.lib.phoenix6.properties.pid.PhoenixPidControllerPropertyBuilder;
import com.gos.lib.photonvision.AprilTagCameraBuilder;
import com.gos.lib.photonvision.AprilTagCameraManager;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiPidPropertyBuilder;
import com.gos.lib.swerve.SwerveDrivePublisher;
import com.gos.reefscape.ChoreoUtils;
import com.gos.reefscape.Constants;
import com.gos.reefscape.GosField;
import com.gos.reefscape.MaybeFlippedPose2d;
import com.gos.reefscape.ReefDetection;
import com.gos.reefscape.RobotExtrinsic;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.generated.ChoreoPoses;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.util.Units;
import frc.robot.generated.TunerConstantsCompetition;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.generated.TunerConstantsCompetition.TunerSwerveDrivetrain;
import org.littletonrobotics.frc2025.FieldConstants;
import org.photonvision.EstimatedRobotPose;


/**
 * Class that extends the Phoenix 6 SwerveDrivetrain class and implements
 * Subsystem so it can easily be used in command-based projects.
 */
@SuppressWarnings("PMD.GodClass")
public class ChassisSubsystem extends TunerSwerveDrivetrain implements Subsystem {
    private static final boolean DEBUG_ODOMETRY = true;
    private static final boolean DEBUG_POSE_ESTIMATION = false;
    private static final boolean DEBUG_SWERVE_STATE = false;

    public static final double MAX_TRANSLATION_SPEED;
    public static final double MAX_ROTATION_SPEED = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    private static final SlotConfigs DEFAULT_STEER_CONFIG;
    private static final SlotConfigs DEFAULT_DRIVE_CONFIG;

    private static final Rotation2d BLUE_ALLIANCE_PERSPECTIVE_ROTATION = Rotation2d.kZero;
    private static final Rotation2d RED_ALLIANCE_PERSPECTIVE_ROTATION = Rotation2d.k180deg;

    private static final TunablePathConstraints TUNABLE_PATH_CONSTRAINTS = new TunablePathConstraints(
        Constants.DEFAULT_CONSTANT_PROPERTIES,
        "Tunable path constraints",
        84,
        120,
        360,
        360);

    static {
        MAX_TRANSLATION_SPEED = TunerConstantsCompetition.kSpeedAt12Volts.in(MetersPerSecond);
        DEFAULT_STEER_CONFIG = SlotConfigs.from(TunerConstantsCompetition.steerGains);
        DEFAULT_DRIVE_CONFIG = SlotConfigs.from(TunerConstantsCompetition.driveGains);
    }

    private static final SwerveDriveKinematics KINEMATICS = new SwerveDriveKinematics(
        new Translation2d(TunerConstantsCompetition.kFrontLeftXPos, TunerConstantsCompetition.kFrontLeftYPos),
        new Translation2d(TunerConstantsCompetition.kFrontRightXPos, TunerConstantsCompetition.kFrontRightYPos),
        new Translation2d(TunerConstantsCompetition.kBackLeftXPos, TunerConstantsCompetition.kBackLeftYPos),
        new Translation2d(TunerConstantsCompetition.kBackRightXPos, TunerConstantsCompetition.kBackRightYPos)
    );

    private static final double SIM_LOOP_PERIOD = 0.005; // 5 ms
    private Notifier m_simNotifier;
    private final PidProperty m_pidControllerProperty;
    private final List<PidProperty> m_moduleProperties;
    private final List<BasePhoenix6Alerts> m_alerts;

    private double m_lastSimTime;
    private final GosField m_field;
    private final AprilTagCameraManager m_aprilTagCameras;
    private final ReefDetection m_reefDetection;
    private final SwerveDriveOdometry m_odometryOnly;
    private final SwerveDrivePoseEstimator m_oldPoseEstimator;

    private final PIDController m_dtp2Controller = new PIDController(0, 0, 0);
    private final PidProperty m_dtp2Properties;

    private boolean m_isDrivingToPose;
    private boolean m_isDrivingRobotRelative;

    private final SwerveDrivePublisher m_swerveDrivePublisher;
    private final LoggingUtil m_loggingUtil;

    private final SwerveRequest.RobotCentric m_robotRelativeDriveRequest = new SwerveRequest.RobotCentric()
        .withDeadband(MAX_TRANSLATION_SPEED * 0.05)
        .withRotationalDeadband(MAX_ROTATION_SPEED * .05)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.FieldCentric m_driveRequest = new SwerveRequest.FieldCentric()
        .withDeadband(MAX_TRANSLATION_SPEED * 0.05)
        .withRotationalDeadband(MAX_ROTATION_SPEED * .05)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.FieldCentricFacingAngle m_davidDriveRequest = new SwerveRequest.FieldCentricFacingAngle()
        .withRotationalDeadband(MAX_ROTATION_SPEED * 0.05)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private boolean m_hasAppliedOperatorPerspective;

    /** Swerve request to apply during robot-centric path following */
    private final SwerveRequest.ApplyRobotSpeeds m_pathApplyRobotSpeeds = new SwerveRequest.ApplyRobotSpeeds();

    /**
     * Constructs a CTRE SwerveDrivetrain using the specified constants.
     * <p>
     * This constructs the underlying hardware devices, so users should not construct
     * the devices themselves. If they need the devices, they can access them through
     * getters in the classes.
     *
     * @param drivetrainConstants Drivetrain-wide constants for the swerve drive
     * @param modules             Constants for each specific module
     */
    public ChassisSubsystem(
        SwerveDrivetrainConstants drivetrainConstants,
        SwerveModuleConstants<?, ?, ?>... modules
    ) {
        super(drivetrainConstants, modules);
        if (Utils.isSimulation()) {
            startSimThread();
        }

        m_moduleProperties = new ArrayList<>();
        m_alerts = new ArrayList<>();

        m_dtp2Properties = new WpiPidPropertyBuilder("DTP2", false, m_dtp2Controller)
            .addP(.02)
            .addD(0)
            .build();

        for (int i = 0; i < 4; ++i) {
            SwerveModule<TalonFX, TalonFX, CANcoder> module = getModule(i);
            m_moduleProperties.add(new Phoenix6TalonPidPropertyBuilder("SdsModule.Steer", Constants.DEFAULT_CONSTANT_PROPERTIES, module.getSteerMotor(), 0)
                .fromDefaults(DEFAULT_STEER_CONFIG)
                .build());
            m_moduleProperties.add(new Phoenix6TalonPidPropertyBuilder("SdsModule.Drive", Constants.DEFAULT_CONSTANT_PROPERTIES, module.getDriveMotor(), 0)
                .fromDefaults(DEFAULT_DRIVE_CONFIG)
                .build());

            m_alerts.add(new TalonFxAlerts(module.getDriveMotor(), "Swerve Drive[" + i + "]"));
            m_alerts.add(new TalonFxAlerts(module.getSteerMotor(), "Swerve Steer[" + i + "]"));
            m_alerts.add(new CancoderAlerts(module.getEncoder()));
        }
        m_alerts.add(new PigeonAlerts(getPigeon2()));

        configureAutoBuilder();
        m_field = new GosField();
        SmartDashboard.putData("Field", m_field.getField2d());
        SmartDashboard.putData("Field3d", m_field.getField3d());
        m_pidControllerProperty = new PhoenixPidControllerPropertyBuilder("chassis Pid", Constants.DEFAULT_CONSTANT_PROPERTIES, m_davidDriveRequest.HeadingController)
            .addP(10.0)
            .addD(0.1)
            .build();
        m_swerveDrivePublisher = new SwerveDrivePublisher();

        m_odometryOnly = new SwerveDriveOdometry(
            KINEMATICS,
            getPigeon2().getRotation2d(),
            getState().ModulePositions);
        m_oldPoseEstimator = new SwerveDrivePoseEstimator(
            KINEMATICS,
            getPigeon2().getRotation2d(),
            getState().ModulePositions,
            new Pose2d());

        m_reefDetection = new ReefDetection();

        Matrix<N3, N1> singleTagStddev = VecBuilder.fill(1.5, 1.5, Units.degreesToRadians(180));
        Matrix<N3, N1> multiTagStddev = VecBuilder.fill(.5, 0.5, Units.degreesToRadians(30));

        boolean enableFancyCameraSim = false;

        AprilTagCameraBuilder cameraBuilder = new AprilTagCameraBuilder()
            .withLayout(FieldConstants.TAG_LAYOUT)
            .withField(m_field)
            .withSingleTagStddev(singleTagStddev)
            .withMultiTagStddev(multiTagStddev)
            .withFieldDebugConfig(new DebugConfig(false, true, false))
            .withSingleTagMaxDistanceMeters(4)
            .withSingleTagMaxAmbiguity(.5)
            .withSimEnableRawStream(enableFancyCameraSim)
            .withSimEnableProcessedStream(enableFancyCameraSim)
            .withSimEnableDrawWireframe(enableFancyCameraSim)
            ;


        m_aprilTagCameras = new AprilTagCameraManager(FieldConstants.TAG_LAYOUT, List.of(
            cameraBuilder
                .withCamera("Left Camera")
                .withTransform(RobotExtrinsic.LEFT_CAMERA).build(),
            cameraBuilder
                .withCamera("Right Camera")
                .withTransform(RobotExtrinsic.RIGHT_CAMERA).build(),
            cameraBuilder
                .withCamera("Back Camera")
                .withSingleTagStddev(singleTagStddev.times(3))
                .withTransform(RobotExtrinsic.BACK_CAMERA).build()
        ));

        m_loggingUtil = new LoggingUtil("ChassisLogs");
        m_loggingUtil.addBoolean("AlignedWithReef", this::isAlignedWithReef);

        PathPlannerLogging.setLogActivePathCallback(m_field::setTrajectory);
        PathPlannerLogging.setLogTargetPoseCallback(m_field::setTrajectorySetpoint);
    }

    public boolean isDrivingRobotRelative() {
        return m_isDrivingRobotRelative;
    }

    public boolean isDrivingToPose() {
        return m_isDrivingToPose;
    }

    public void clearStickyFaults() {
        getPigeon2().clearStickyFaults();
        for (int i = 0; i < 4; i++) {
            getModule(i).getSteerMotor().clearStickyFaults();
            getModule(i).getDriveMotor().clearStickyFaults();
            getModule(i).getEncoder().clearStickyFaults();
        }
    }

    private void configureAutoBuilder() {
        try {
            var config = RobotConfig.fromGUISettings();
            AutoBuilder.configure(
                () -> getState().Pose,   // Supplier of current robot pose
                this::resetPose,         // Consumer for seeding pose against auto
                () -> getState().Speeds, // Supplier of current robot speeds
                // Consumer of ChassisSpeeds and feedforwards to drive the robot
                (speeds, feedforwards) -> setControl(
                    m_pathApplyRobotSpeeds.withSpeeds(speeds)
                        .withWheelForceFeedforwardsX(feedforwards.robotRelativeForcesXNewtons())
                        .withWheelForceFeedforwardsY(feedforwards.robotRelativeForcesYNewtons())
                ),
                new PPHolonomicDriveController(
                    // PID constants for translation
                    new PIDConstants(10, 0, 0),
                    // PID constants for rotation
                    new PIDConstants(7, 0, 0)
                ),
                config,
                // Assume the path needs to be flipped for Red vs Blue, this is normally the case
                () -> DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red,
                this // Subsystem for requirements
            );
        } catch (Exception ex) { // NOPMD
            DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", ex.getStackTrace());
        }
    }

    public void addChassisDebugCommands(boolean isComp) {
        ShuffleboardTab debugTabChassis = Shuffleboard.getTab("chassis");
        if (!isComp) {
            debugTabChassis.add(createSyncOdometryCommand().withName("Sync Odometry"));
        }

        debugTabChassis.add(createChassisToCoastModeCommand().withName("chassis to coast"));
        debugTabChassis.add(createCheckAlertsCommand().withName("Check Chassis Alerts"));

    }
    /**
     * Returns a command that applies the specified control request to this swerve drivetrain.
     *
     * @param requestSupplier Function returning the request to apply
     * @return Command to run
     */

    public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
        return run(() -> this.setControl(requestSupplier.get()));
    }

    public AlgaePositions findClosestAlgae() {
        Pose2d curPose = getState().Pose;
        double minDist = 999999999999999999.9;
        AlgaePositions closestAlgae = AlgaePositions.AB;
        for (AlgaePositions pos : AlgaePositions.values()) {
            Pose2d algaePose = pos.m_pose.getPose();
            double dx = algaePose.getX() - curPose.getX();
            double dy = algaePose.getY() - curPose.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < minDist) {
                minDist = distance;
                closestAlgae = pos;
            }

        }
        return closestAlgae;
    }

    @Override
    public void simulationPeriodic() {
        m_aprilTagCameras.updateSimulator(getState().Pose);
    }

    @Override
    public void periodic() {
        /*
         * Periodically try to apply the operator perspective.
         * If we haven't applied the operator perspective before, then we should apply it regardless of DS state.
         * This allows us to correct the perspective in case the robot code restarts mid-match.
         * Otherwise, only check and apply the operator perspective if the DS is disabled.
         * This ensures driving behavior doesn't change until an explicit disable event occurs during testing.
         */
        if (!m_hasAppliedOperatorPerspective || DriverStation.isDisabled()) {
            DriverStation.getAlliance().ifPresent(allianceColor -> {
                setOperatorPerspectiveForward(
                    allianceColor == Alliance.Red
                        ? RED_ALLIANCE_PERSPECTIVE_ROTATION
                        : BLUE_ALLIANCE_PERSPECTIVE_ROTATION
                );
                m_hasAppliedOperatorPerspective = true;
            });
        }

        SwerveDriveState state = getState();

        if (DEBUG_ODOMETRY) {
            m_odometryOnly.update(getPigeon2().getRotation2d(), state.ModulePositions);
            m_field.setOdometry(m_odometryOnly.getPoseMeters());
        }
        if (DEBUG_POSE_ESTIMATION) {
            m_oldPoseEstimator.update(getPigeon2().getRotation2d(), state.ModulePositions);
            m_field.setOldPoseEstimator(m_oldPoseEstimator.getEstimatedPosition());
        }

        m_field.setPoseEstimate(state.Pose);

        if (DEBUG_SWERVE_STATE) {
            m_swerveDrivePublisher.setMeasuredStates(state.ModuleStates);
            m_swerveDrivePublisher.setRobotRotation(state.Pose.getRotation());
            m_swerveDrivePublisher.setDesiredStates(state.ModuleTargets);
        }
        m_pidControllerProperty.updateIfChanged();
        m_dtp2Properties.updateIfChanged();
        for (PidProperty property : m_moduleProperties) {
            property.updateIfChanged();
        }

        List<Pair<EstimatedRobotPose, Matrix<N3, N1>>> estimates = m_aprilTagCameras.update(state.Pose);
        for (Pair<EstimatedRobotPose, Matrix<N3, N1>> estimatePair : estimates) {

            EstimatedRobotPose camPose = estimatePair.getFirst();
            Pose2d camEstPose = camPose.estimatedPose.toPose2d();
            addVisionMeasurement(camEstPose, camPose.timestampSeconds, estimatePair.getSecond());
        }

        m_reefDetection.periodic();
        m_loggingUtil.updateLogs();
    }

    private void startSimThread() {
        m_lastSimTime = Utils.getCurrentTimeSeconds();

        /* Run simulation at a faster rate so PID gains behave more reasonably */
        m_simNotifier = new Notifier(() -> {
            final double currentTime = Utils.getCurrentTimeSeconds();
            double deltaTime = currentTime - m_lastSimTime;
            m_lastSimTime = currentTime;

            /* use the measured time delta, get battery voltage from WPILib */
            updateSimState(deltaTime, RobotController.getBatteryVoltage());
        });
        m_simNotifier.startPeriodic(SIM_LOOP_PERIOD);
    }

    private void resetGyro() {
        Pose2d currentPose = getState().Pose;
        resetPose(new Pose2d(currentPose.getX(), currentPose.getY(), Rotation2d.fromDegrees(0)));
    }

    public void davidDrive(double xJoystick, double yJoystick, double angleJoystick) {
        m_isDrivingRobotRelative = false;
        setControl(
            m_davidDriveRequest.withVelocityX(xJoystick * MAX_TRANSLATION_SPEED)
                .withVelocityY(yJoystick * MAX_TRANSLATION_SPEED)
                .withTargetDirection(new Rotation2d(angleJoystick)));
    }

    public void driveWithJoystick(double xJoystick, double yJoystick, double rotationalJoystick) {
        m_isDrivingRobotRelative = false;
        setControl(
            m_driveRequest.withVelocityX(xJoystick * MAX_TRANSLATION_SPEED)
                .withVelocityY(yJoystick * MAX_TRANSLATION_SPEED)
                .withRotationalRate(rotationalJoystick * MAX_ROTATION_SPEED)

        );
    }

    public void robotDriveWithJoystick(double xJoystick, double yJoystick, double rotationalJoystick) {
        m_isDrivingRobotRelative = true;
        setControl(
            m_robotRelativeDriveRequest.withVelocityX(xJoystick * MAX_TRANSLATION_SPEED)
                .withVelocityY(yJoystick * MAX_TRANSLATION_SPEED)
                .withRotationalRate(rotationalJoystick * MAX_ROTATION_SPEED)

        );
    }

    @Override
    public void addVisionMeasurement(Pose2d visionRobotPoseMeters, double timestampSeconds) {
        super.addVisionMeasurement(visionRobotPoseMeters, Utils.fpgaToCurrentTime(timestampSeconds));
    }

    @Override
    public void addVisionMeasurement(Pose2d visionRobotPoseMeters, double timestampSeconds, Matrix<N3, N1> stds) {
        super.addVisionMeasurement(visionRobotPoseMeters, Utils.fpgaToCurrentTime(timestampSeconds), stds);
        m_oldPoseEstimator.addVisionMeasurement(visionRobotPoseMeters, timestampSeconds, stds);
    }

    @Override
    public void resetPose(Pose2d pose) {
        super.resetPose(pose);
        m_odometryOnly.resetPose(pose);
        m_oldPoseEstimator.resetPose(pose);
    }

    public void syncOdometryWithPoseEstimator() {
        m_odometryOnly.resetPose(getState().Pose);
    }

    public void setIdleMode(NeutralModeValue neutralModeValue) {
        for (int i = 0; i < 4; i++) {
            getModule(i).getSteerMotor().setNeutralMode(neutralModeValue);
            getModule(i).getDriveMotor().setNeutralMode(neutralModeValue);
        }

    }

    public Optional<Double> getReefCameraYaw() {
        return m_reefDetection.getYaw();
    }

    public void robotDTP2() {
        Optional<Double> maybeError = m_reefDetection.getYaw();
        if (maybeError.isEmpty()) {
            robotDriveWithJoystick(0, 0, 0);
            return;
        }

        double output = m_dtp2Controller.calculate(maybeError.get());
        robotDriveWithJoystick(0, output, 0);
    }

    public Command createCheckAlertsCommand() {
        return run(() -> {
            for (BasePhoenix6Alerts alert : m_alerts) {
                alert.checkAlerts();
            }
        }).ignoringDisable(true);
    }

    public Command createSyncOdometryCommand() {
        return run(this::syncOdometryWithPoseEstimator).ignoringDisable(true);
    }

    public Command createResetPoseCommand(Pose2d pose) {
        return runOnce(() -> resetPose(pose));
    }

    public Command createResetPoseCommand(MaybeFlippedPose2d pose) {
        return runOnce(() -> resetPose(pose.getPose()));
    }


    public Command createResetPoseFromChoreoCommand(String pathName) {
        return createResetPoseCommand(ChoreoUtils.getPathStartingPose(pathName));
    }


    public Command createResetAndFollowChoreoPathCommand(String pathName) {
        return Commands.sequence(
            createResetPoseFromChoreoCommand(pathName),
            followChoreoPath(pathName));
    }

    public Command createDriveToPose(Pose2d pose) {
        return AutoBuilder.pathfindToPose(pose, TUNABLE_PATH_CONSTRAINTS.getConstraints(), 0.0);
    }

    public Command createDriveToPointNoFlipCommand(Pose2d end, Rotation2d endAngle, Pose2d start) {
        List<Waypoint> bezierPoints = PathPlannerPath.waypointsFromPoses(start, end);
        PathPlannerPath path = new PathPlannerPath(
            bezierPoints,
            TUNABLE_PATH_CONSTRAINTS.getConstraints(),
            null,
            new GoalEndState(0.0, endAngle)
        );
        path.preventFlipping = true;
        return runOnce(() -> m_isDrivingToPose = true)
            .andThen(AutoBuilder.followPath(path))
            .andThen(() -> m_isDrivingToPose = false);
    }

    public Command createDriveToMaybeFlippedPose(MaybeFlippedPose2d end) {
        return createDriveToPointNoFlipCommand(end.getPose(), end.getPose().getRotation(), getState().Pose);
    }

    public Command createPathfindToMaybeFlippedPose(MaybeFlippedPose2d pose) {
        return runOnce(() -> m_isDrivingToPose = true)
            .andThen(defer(() -> AutoBuilder.pathfindToPose(pose.getPose(), TUNABLE_PATH_CONSTRAINTS.getConstraints(), 0.0)
            .andThen(() -> m_isDrivingToPose = false)));
    }

    public Command createResetGyroCommand() {
        return run(this::resetGyro)
            .ignoringDisable(true)
            .withName("Reset Gyro");
    }

    public Command createDriveToClosestAlgaeCommand() {
        return defer(() -> {
            AlgaePositions closestAlgae = findClosestAlgae();
            return createDriveToMaybeFlippedPose(closestAlgae.m_pose);
        });
    }

    public Command createDriveToNet() {
        return defer(() -> createDriveToMaybeFlippedPose(ChoreoPoses.BLUE_NET));
    }

    public Command createDriveToRightCoral() {
        return defer(() -> {
            AlgaePositions closestAlgae = findClosestAlgae();
            return createDriveToMaybeFlippedPose(closestAlgae.m_coralRight.m_pose);
        });
    }


    public Command createDriveToLeftCoral() {
        return defer(() -> {
            AlgaePositions closestAlgae = findClosestAlgae();
            return createDriveToMaybeFlippedPose(closestAlgae.m_coralLeft.m_pose);
        });
    }

    public Command createChassisToCoastModeCommand() {
        return this.runEnd(
                () -> setIdleMode(NeutralModeValue.Coast),
                () -> setIdleMode(NeutralModeValue.Brake))
            .ignoringDisable(true).withName("Chassis to Coast");
    }

    public Command createShakeChassisCommand() {
        return defer(() -> {
            Pose2d currentPose = getState().Pose;
            Pose2d forwardOffset = new Pose2d(
                currentPose.getX() + Units.inchesToMeters(2),
                currentPose.getY() + Units.inchesToMeters(2),
                currentPose.getRotation());

            return createDriveToPointNoFlipCommand(forwardOffset, forwardOffset.getRotation(), getState().Pose)
                .andThen(createDriveToPointNoFlipCommand(currentPose, currentPose.getRotation(), forwardOffset));
        });
    }

    public Command createDriveToPosePartTwoCommand() {
        return run(this::robotDTP2).until(this::isAlignedWithReef);
    }

    public Boolean isAlignedWithReef() {
        Optional<Double> maybeError = m_reefDetection.getYaw();
        if (maybeError.isEmpty()) {
            return false;
        } else {
            return Math.abs(maybeError.get()) < 0.75;
        }
    }
}

