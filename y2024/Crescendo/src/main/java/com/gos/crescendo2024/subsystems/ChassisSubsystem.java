// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.subsystems;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.gos.crescendo2024.AprilTagDetection;
import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.GoSField;
import com.gos.crescendo2024.ObjectDetection;
import com.gos.lib.GetAllianceUtil;
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
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.EstimatedRobotPose;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;

import java.util.Optional;

import java.util.List;

public class ChassisSubsystem extends SubsystemBase {
    private static final double GYRO_TO_CHASSIS_OFFSET = -90;

    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = Units.feetToMeters(13);
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(360);

    private final RevSwerveChassis m_swerveDrive;
    private final Pigeon2 m_gyro;

    private final GoSField m_field;

    private final PIDController m_turnAnglePIDVelocity;
    private final PidProperty m_turnAnglePIDProperties;
    private final AprilTagDetection m_photonVisionSubsystem;
    private final ObjectDetection m_objectDetectonSubsystem;

    private final GosDoubleProperty m_driveToPointMaxVelocity = new GosDoubleProperty(false, "Chassis On the Fly Max Velocity", 48);
    private final GosDoubleProperty m_driveToPointMaxAcceleration = new GosDoubleProperty(false, "Chassis On the Fly Max Acceleration", 48);
    private final GosDoubleProperty m_angularMaxVelocity = new GosDoubleProperty(false, "Chassis On the Fly Max Angular Velocity", 180);
    private final GosDoubleProperty m_angularMaxAcceleration = new GosDoubleProperty(false, "Chassis On the Fly Max Angular Acceleration", 180);

    public ChassisSubsystem() {
        m_gyro = new Pigeon2(Constants.PIGEON_PORT);
        m_gyro.getConfigurator().apply(new Pigeon2Configuration());

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,
            Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH,
            Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH,
            Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,
            RevSwerveModuleConstants.DriveMotorTeeth.T14,
            WHEEL_BASE, TRACK_WIDTH,
            MAX_TRANSLATION_SPEED,
            MAX_ROTATION_SPEED);

        //TODO need change pls
        m_turnAnglePIDVelocity = new PIDController(0, 0, 0);
        m_turnAnglePIDVelocity.enableContinuousInput(0, 360);
        m_turnAnglePIDProperties = new WpiPidPropertyBuilder("Chassis to angle", false, m_turnAnglePIDVelocity)
            .addP(0)
            .addI(0)
            .addD(0)
            .build();

        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));
        m_photonVisionSubsystem = new AprilTagDetection();
        m_objectDetectonSubsystem = new ObjectDetection();

        AutoBuilder.configureHolonomic(
            this::getPose,
            this::resetOdometry,
            this::getChassisSpeed,
            this::setChassisSpeed,
            new HolonomicPathFollowerConfig(
                new PIDConstants(5, 0, 0),
                new PIDConstants(5, 0, 0),
                MAX_TRANSLATION_SPEED,
                WHEEL_BASE,
                new ReplanningConfig(),
                0.02),
            GetAllianceUtil::isRedAlliance,
            this
        );

        m_field = new GoSField();
        SmartDashboard.putData("Field", m_field.getSendable());

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

    @Override
    public void periodic() {
        m_swerveDrive.periodic();

        m_field.setPoseEstimate(m_swerveDrive.getEstimatedPosition());
        m_field.setOdometry(m_swerveDrive.getOdometryPosition());
        m_field.drawNotePoses(m_objectDetectonSubsystem.objectLocations(getPose()));
        m_turnAnglePIDProperties.updateIfChanged();

        Optional<EstimatedRobotPose> cameraResult = m_photonVisionSubsystem.getEstimateGlobalPose(m_swerveDrive.getEstimatedPosition());
        if (cameraResult.isPresent()) {
            EstimatedRobotPose camPose = cameraResult.get();
            Pose2d pose2d = camPose.estimatedPose.toPose2d();
            m_swerveDrive.addVisionMeasurement(pose2d, camPose.timestampSeconds);
        }
        m_field.drawNotePoses(m_objectDetectonSubsystem.objectLocations(getPose()));
    }


    @Override
    public void simulationPeriodic() {
        m_swerveDrive.updateSimulator();
        m_photonVisionSubsystem.updateAprilTagSimulation(getPose());
        m_objectDetectonSubsystem.updateObjectDetectionSimulation(getPose());
    }

    public void teleopDrive(double xPercent, double yPercent, double rotPercent, boolean fieldRelative) {
        m_swerveDrive.driveWithJoysticks(xPercent, yPercent, rotPercent, fieldRelative);
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
        double angleCurrentDegree = m_swerveDrive.getOdometryPosition().getRotation().getDegrees();
        double steerVelocity = m_turnAnglePIDVelocity.calculate(angleCurrentDegree, angle);
        ChassisSpeeds speeds = new ChassisSpeeds(xVel, yVel, steerVelocity);
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
        double xDiff = point.getX() - robotPose.getX();
        double yDiff = point.getY() - robotPose.getY();
        double updateAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
        updateAngle += 180;
        turnToAngleWithVelocity(xVel, yVel, updateAngle);
    }

    public void davidDrive(double x, double y, double angle) {
        turnToAngleWithVelocity(x, y, angle);
    }


    /////////////////////////////////////
    // Checklists
    /////////////////////////////////////


    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////

    public Command createResetGyroCommand() {
        return runOnce(() -> m_gyro.setYaw(GYRO_TO_CHASSIS_OFFSET)).ignoringDisable(true).withName("Reset Gyro");
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
        return createFollowPathCommand(path, false).withName("Follow Path to " + end);
    }

    public Command createDriveToPointCommand(Pose2d endPoint) {
        return createDriveToPointNoFlipCommand(endPoint).withName("Drive to " + endPoint);
    }

    public Command createDriveToDetectedObject() {
        return defer(() -> {
            if (!m_objectDetectonSubsystem.objectLocations(getPose()).isEmpty()) {
                return createDriveToPointNoFlipCommand(m_objectDetectonSubsystem.objectLocations(getPose()).get(0));
            } else {
                return Commands.none();
            }
        });


    }

}
