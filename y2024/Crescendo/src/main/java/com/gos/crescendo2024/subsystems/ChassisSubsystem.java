// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.subsystems;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.gos.crescendo2024.AllianceFlipper;
import com.gos.crescendo2024.AprilTagDetection;
import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.FieldConstants;
import com.gos.crescendo2024.GoSField;
import com.gos.crescendo2024.ObjectDetection;
import com.gos.lib.GetAllianceUtil;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiPidPropertyBuilder;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.EstimatedRobotPose;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;

import java.util.List;
import java.util.Optional;

public class ChassisSubsystem extends SubsystemBase {
    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED;
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(720);

    static {
        if (Constants.IS_COMPETITION_ROBOT) {
            MAX_TRANSLATION_SPEED = 5.5; // Theoretically 5.74
        } else {
            MAX_TRANSLATION_SPEED = 3.9624; // 13fps, Theoretically 4.8
        }
    }

    private static final boolean USE_APRIL_TAGS = true;

    private final RevSwerveChassis m_swerveDrive;
    private final Pigeon2 m_gyro;

    private final GoSField m_field;

    private final PIDController m_turnAnglePIDVelocity;
    private final PidProperty m_turnAnglePIDProperties;
    private final AprilTagDetection m_photonVisionSubsystem;

    private final ObjectDetection m_objectDetectionSubsystem;

    private final GosDoubleProperty m_driveToPointMaxVelocity = new GosDoubleProperty(false, "Chassis On the Fly Max Velocity", 48);
    private final GosDoubleProperty m_driveToPointMaxAcceleration = new GosDoubleProperty(false, "Chassis On the Fly Max Acceleration", 48);
    private final GosDoubleProperty m_angularMaxVelocity = new GosDoubleProperty(false, "Chassis On the Fly Max Angular Velocity", 180);
    private final GosDoubleProperty m_angularMaxAcceleration = new GosDoubleProperty(false, "Chassis On the Fly Max Angular Acceleration", 180);
    private final GosDoubleProperty m_translationJoystickDampening = new GosDoubleProperty(false, "TranslationJoystickDampening", .5);
    private final GosDoubleProperty m_rotationJoystickDampening = new GosDoubleProperty(false, "RotationJoystickDampening", .7);



    private final LoggingUtil m_logging;

    private boolean m_isSlowTeleop;

    public ChassisSubsystem() {
        m_gyro = new Pigeon2(Constants.PIGEON_PORT);
        m_gyro.getConfigurator().apply(new Pigeon2Configuration());

        m_field = new GoSField();
        SmartDashboard.putData("Field", m_field.getSendable());

        RevSwerveModuleConstants.DriveMotor motorType;
        if (Constants.IS_COMPETITION_ROBOT) {
            motorType = RevSwerveModuleConstants.DriveMotor.VORTEX;
        } else {
            motorType = RevSwerveModuleConstants.DriveMotor.NEO;
        }

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,
            Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH,
            Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH,
            Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,
            motorType,
            RevSwerveModuleConstants.DriveMotorPinionTeeth.T14,
            RevSwerveModuleConstants.DriveMotorSpurTeeth.T22,
            WHEEL_BASE,
            TRACK_WIDTH,
            MAX_TRANSLATION_SPEED, MAX_ROTATION_SPEED);

        //TODO need change pls
        m_turnAnglePIDVelocity = new PIDController(0, 0, 0);
        m_turnAnglePIDVelocity.setTolerance(5);
        m_turnAnglePIDVelocity.enableContinuousInput(0, 360);
        m_turnAnglePIDProperties = new WpiPidPropertyBuilder("Chassis to angle", Constants.DEFAULT_CONSTANT_PROPERTIES, m_turnAnglePIDVelocity)
            .addP(0.2)
            .addI(0)
            .addD(0)
            .build();

        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));
        m_photonVisionSubsystem = new AprilTagDetection(m_field);
        m_objectDetectionSubsystem = new ObjectDetection();

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

        m_logging = new LoggingUtil("Chassis");
        m_logging.addDouble("GyroAngle", m_gyro::getAngle);
        m_logging.addDouble("PoseAngle", () -> getPose().getRotation().getDegrees());
        m_logging.addDouble("Angle Setpoint", m_turnAnglePIDVelocity::getSetpoint);
        m_logging.addBoolean("At Angle Setpoint", this::isAngleAtGoal);
        m_logging.addDouble("Distance to Speaker", this::getDistanceToSpeaker);
    }

    public double getDistanceToSpeaker() {
        Pose2d speaker = AllianceFlipper.maybeFlip(FieldConstants.Speaker.CENTER_SPEAKER_OPENING);
        Translation2d roboManTranslation = getPose().getTranslation();
        return roboManTranslation.getDistance(speaker.getTranslation());
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
        m_logging.updateLogs();

        m_field.setPoseEstimate(m_swerveDrive.getEstimatedPosition());
        m_field.setOdometry(m_swerveDrive.getOdometryPosition());
        m_field.drawNotePoses(m_objectDetectionSubsystem.objectLocations(getPose()));
        m_turnAnglePIDProperties.updateIfChanged();
        m_field.setFuturePose(getFuturePose(0.3));

        Optional<EstimatedRobotPose> cameraResult = m_photonVisionSubsystem.getEstimateGlobalPose(m_swerveDrive.getEstimatedPosition());
        if (USE_APRIL_TAGS && cameraResult.isPresent()) {
            EstimatedRobotPose camPose = cameraResult.get();
            Pose2d camEstPose = camPose.estimatedPose.toPose2d();
            m_swerveDrive.addVisionMeasurement(camEstPose, camPose.timestampSeconds, m_photonVisionSubsystem.getEstimationStdDevs(camEstPose));
        }
    }


    @Override
    public void simulationPeriodic() {
        m_swerveDrive.updateSimulator();
        m_photonVisionSubsystem.updateAprilTagSimulation(getPose());
        m_objectDetectionSubsystem.updateObjectDetectionSimulation(getPose());
    }

    public void teleopDrive(double xPercent, double yPercent, double rotPercent, boolean fieldRelative) {
        if (m_isSlowTeleop) {
            m_swerveDrive.driveWithJoysticks(xPercent * m_translationJoystickDampening.getValue(), yPercent * m_translationJoystickDampening.getValue(),
                rotPercent * m_rotationJoystickDampening.getValue(), fieldRelative);
        } else {
            m_swerveDrive.driveWithJoysticks(xPercent, yPercent, rotPercent, fieldRelative);
        }

    }

    public Pose2d getPose() {
        return m_swerveDrive.getEstimatedPosition();
    }

    public boolean isAngleAtGoal() {
        return m_turnAnglePIDVelocity.atSetpoint();
    }

    public void turnToAngle(double angleGoal) {
        double angleCurrentDegree = m_swerveDrive.getOdometryPosition().getRotation().getDegrees();
        double steerVelocity = m_turnAnglePIDVelocity.calculate(angleCurrentDegree, angleGoal);
        //TODO add ff

        if (isAngleAtGoal()) {
            steerVelocity = 0;
        }
        ChassisSpeeds speeds = new ChassisSpeeds(0, 0, steerVelocity);
        m_swerveDrive.setChassisSpeeds(speeds);
    }

    public void turnToAngleWithVelocity(double xVel, double yVel, double angle) {
        double angleCurrentDegree = getPose().getRotation().getDegrees();
        double steerVelocity = m_turnAnglePIDVelocity.calculate(angleCurrentDegree, angle);
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xVel, yVel, steerVelocity, getPose().getRotation());


        m_swerveDrive.setChassisSpeeds(speeds);
    }

    public void turnToFacePoint(Pose2d point, double xVel, double yVel) {
        Pose2d robotPose = getPose();
        double xDiff = point.getX() - robotPose.getX();
        double yDiff = point.getY() - robotPose.getY();
        double updateAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
        turnToAngleWithVelocity(xVel, yVel, updateAngle);
    }

    public void turnButtToFacePoint(Pose2d point, double xVel, double yVel) {
        Pose2d robotPose = getPose();
        turnButtToFacePoint(robotPose, point, xVel, yVel);
    }

    public void turnButtToFacePoint(Pose2d currentPos, Pose2d endPos, double xVel, double yVel) {
        double xDiff = endPos.getX() - currentPos.getX();
        double yDiff = endPos.getY() - currentPos.getY();
        double updateAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
        updateAngle += 180;
        turnToAngleWithVelocity(xVel, yVel, updateAngle);
    }

    public void davidDrive(double x, double y, double angle) {
        if (m_isSlowTeleop) {
            turnToAngleWithVelocity(x * m_translationJoystickDampening.getValue(), y * m_translationJoystickDampening.getValue(),
                angle);
        } else {
            turnToAngleWithVelocity(x, y, angle);
        }
    }

    public Pose2d getFuturePose() {
        return getFuturePose(.3);
    }

    private Pose2d getFuturePose(double seconds) {
        ChassisSpeeds currentRobotVelocity = m_swerveDrive.getChassisSpeed();
        ChassisSpeeds currentFieldVelocity = ChassisSpeeds.fromRobotRelativeSpeeds(currentRobotVelocity, getPose().getRotation());
        Pose2d currentPos = m_swerveDrive.getEstimatedPosition();
        Pose2d deltaPos = new Pose2d(
            currentFieldVelocity.vxMetersPerSecond * seconds,
            currentFieldVelocity.vyMetersPerSecond * seconds,
            new Rotation2d(currentFieldVelocity.omegaRadiansPerSecond * seconds));

        return new Pose2d(currentPos.getX() + deltaPos.getX(), currentPos.getY() + deltaPos.getY(), new Rotation2d());
    }

    private void resetGyro() {
        Pose2d currentPose = getPose();
        resetOdometry(new Pose2d(currentPose.getX(), currentPose.getY(), Rotation2d.fromDegrees(0)));
    }

    public boolean isNoteDetected() {
        return !m_objectDetectionSubsystem.objectLocations(getPose()).isEmpty();
    }

    public int numAprilTagsSeen() {
        return m_photonVisionSubsystem.getLatestResult().targets.size();
    }
    /////////////////////////////////////
    // Checklists
    /////////////////////////////////////


    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////

    public Command createResetGyroCommand() {
        return run(this::resetGyro)
            .ignoringDisable(true)
            .withName("Reset Gyro");
    }

    public Command createTurnToAngleCommand(double angleGoal) {
        return runOnce(m_turnAnglePIDVelocity::reset)
            .andThen(this.run(() -> turnToAngle(angleGoal))
                .until(this::isAngleAtGoal))
            .withName("Chassis to Angle: " + angleGoal);
    }

    public Command createFollowPathCommand(PathPlannerPath path, boolean resetPose) {
        Command followPathCommand = AutoBuilder.followPath(path);
        if (resetPose) {
            return Commands.runOnce(() -> m_swerveDrive.resetOdometry(path.getStartingDifferentialPose())).andThen(followPathCommand).withName("Reset position and follow path");
        }
        return followPathCommand.withName("Follow Path");
    }

    public Command createDriveToPointNoFlipCommand(Pose2d end) {
        List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(getPose(), end);
        PathPlannerPath path = new PathPlannerPath(
            bezierPoints,
            new PathConstraints(
                Units.inchesToMeters(m_driveToPointMaxVelocity.getValue()),
                Units.inchesToMeters(m_driveToPointMaxAcceleration.getValue()),
                Units.degreesToRadians(m_angularMaxVelocity.getValue()),
                Units.degreesToRadians((m_angularMaxAcceleration.getValue()))),
            new GoalEndState(0.0, end.getRotation())
        );
        path.preventFlipping = true;
        return createFollowPathCommand(path, false).withName("Follow Path to " + end);
    }

    public Command createDriveToPointCommand(Pose2d endPoint) {
        return createDriveToPointNoFlipCommand(endPoint).withName("Drive to " + endPoint);
    }

    public Command createDriveToPointMaybeFlippedCommand(Pose2d endPoint) {
        return defer(() -> createDriveToPointNoFlipCommand(AllianceFlipper.maybeFlip(endPoint))).withName("Drive to " + endPoint);
    }

    public Command createResetPoseCommand(Pose2d pose) {
        return runOnce(() -> resetOdometry(pose))
            .ignoringDisable(true)
            .withName("Reset Pose " + pose);
    }

    public Command createPushForwardModeCommand() {
        SwerveModuleState state = new SwerveModuleState(0, new Rotation2d());
        return run(() -> {
            m_swerveDrive.setModuleStates(state);
        }).withName("Chassis Push Forward");
    }

    public Command setIsSlowMode(boolean setBoolean) {
        return runOnce(() -> m_isSlowTeleop = setBoolean);
    }


}
