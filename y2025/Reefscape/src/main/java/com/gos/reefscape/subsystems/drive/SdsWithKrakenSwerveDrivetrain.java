package com.gos.reefscape.subsystems.drive;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;
import static edu.wpi.first.units.Units.*;

import java.util.function.Supplier;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.gos.lib.phoenix6.properties.pid.PhoenixPidControllerPropertyBuilder;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.swerve.SwerveDrivePublisher;
import com.gos.reefscape.ChoreoUtils;
import com.gos.reefscape.GosField;
import com.gos.reefscape.MaybeFlippedPose2d;
import com.gos.reefscape.subsystems.drive.TunerConstants.TunerSwerveDrivetrain;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;


/**
 * Class that extends the Phoenix 6 SwerveDrivetrain class and implements
 * Subsystem so it can easily be used in command-based projects.
 */
public class SdsWithKrakenSwerveDrivetrain extends TunerSwerveDrivetrain implements Subsystem {
    public static final double MAX_TRANSLATION_SPEED = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    public static final double MAX_ROTATION_SPEED = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    private static final Rotation2d BLUE_ALLIANCE_PERSPECTIVE_ROTATION = Rotation2d.kZero;
    private static final Rotation2d RED_ALLIANCE_PERSPECTIVE_ROTATION = Rotation2d.k180deg;

    private static final double SIM_LOOP_PERIOD = 0.005; // 5 ms
    private Notifier m_simNotifier;
    private final PidProperty m_pidControllerProperty;

    private double m_lastSimTime;
    private final GosField m_field;

    private final SwerveDrivePublisher m_swerveDrivePublisher;

    private final SwerveRequest.FieldCentric m_driveRequest = new SwerveRequest.FieldCentric()
        .withDeadband(MAX_TRANSLATION_SPEED * 0.1).withRotationalDeadband(MAX_ROTATION_SPEED * 0.1) // Add a 10% deadband
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.FieldCentricFacingAngle m_davidDriveRequest = new SwerveRequest.FieldCentricFacingAngle()
        .withDeadband(MAX_TRANSLATION_SPEED * 0.1).withRotationalDeadband(MAX_ROTATION_SPEED * 0.1) // Add a 10% deadband
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
    public SdsWithKrakenSwerveDrivetrain(
        SwerveDrivetrainConstants drivetrainConstants,
        SwerveModuleConstants<?, ?, ?>... modules
    ) {
        super(drivetrainConstants, modules);
        if (Utils.isSimulation()) {
            startSimThread();
        }
        configureAutoBuilder();
        m_field = new GosField();
        SmartDashboard.putData("Field", m_field.getField2d());
        SmartDashboard.putData("Field3d", m_field.getField3d());
        m_pidControllerProperty = new PhoenixPidControllerPropertyBuilder("chassis Pid", false, m_davidDriveRequest.HeadingController)
            .addP(0).build();
        m_swerveDrivePublisher = new SwerveDrivePublisher();
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

    /**
     * Returns a command that applies the specified control request to this swerve drivetrain.
     *
     * @param requestSupplier Function returning the request to apply
     * @return Command to run
     */
    public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
        return run(() -> this.setControl(requestSupplier.get()));
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
        m_field.setOdometry(getState().Pose);
        m_swerveDrivePublisher.setMeasuredStates(getState().ModuleStates);
        m_swerveDrivePublisher.setRobotRotation(getState().Pose.getRotation());
        m_swerveDrivePublisher.setDesiredStates(getState().ModuleTargets);
        m_pidControllerProperty.updateIfChanged();
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

    public void davidDrive(double xJoystick, double yJoystick, double angleJoystick) {
        setControl(
            m_davidDriveRequest.withVelocityX(xJoystick * MAX_TRANSLATION_SPEED) // Drive forward with negative Y (forward)
                .withVelocityY(yJoystick * MAX_TRANSLATION_SPEED) // Drive left with negative X (left)
                .withTargetDirection(new Rotation2d(angleJoystick)));
    }

    public void driveWithJoystick(double xJoystick, double yJoystick, double rotationalJoystick) {
        setControl(
            m_driveRequest.withVelocityX(xJoystick * MAX_TRANSLATION_SPEED) // Drive forward with negative Y (forward)
                .withVelocityY(yJoystick * MAX_TRANSLATION_SPEED) // Drive left with negative X (left)
                .withRotationalRate(rotationalJoystick * MAX_ROTATION_SPEED) // Drive counterclockwise with negative X (left)

        );


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
}

