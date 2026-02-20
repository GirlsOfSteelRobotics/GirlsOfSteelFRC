package com.gos.rebuilt.subsystems;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;
import com.gos.rebuilt.ShooterSimBalls;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructureViz extends SubsystemBase {

    private final StructArrayPublisher<Pose3d> m_measuredPublisher;
    private final StructArrayPublisher<Pose3d> m_goalPublisher;
    private final StructPublisher<Pose2d> m_goalRobotPose;

    private final PivotSubsystem m_pivotSubsystem;
    private final PizzaSubsystem m_pizzaSubsystem;
    private final ShooterSimBalls m_shooterSimBalls;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final ClimberSubsystem m_climberSubsystem;


    public SuperStructureViz(PivotSubsystem pivotSubsystem, PizzaSubsystem pizzaSubsystem, ChassisSubsystem chassisSubsystem, ShooterSubsystem shooterSubsystem, ClimberSubsystem climberSubsystem) {
        m_measuredPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Measured", Pose3d.struct).publish();
        m_goalPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Goal", Pose3d.struct).publish();
        m_goalRobotPose = NetworkTableInstance.getDefault().getStructTopic("SuperStructureViz/Robot Pose Goal", Pose2d.struct).publish();

        m_pivotSubsystem = pivotSubsystem;
        m_pizzaSubsystem = pizzaSubsystem;
        m_chassisSubsystem = chassisSubsystem;
        m_shooterSubsystem = shooterSubsystem;
        m_climberSubsystem = climberSubsystem;

        m_shooterSimBalls = new ShooterSimBalls("ShotPreview");
    }

    @Override
    public void periodic() {
        m_measuredPublisher.set(getPoses(
            ShooterSubsystem.SHOT_ANGLE.getRadians(),
            m_pizzaSubsystem.getAngle(),
            m_pivotSubsystem.getAngle(),
            m_climberSubsystem.getLeftHeight(),
            m_climberSubsystem.getRightHeight()));

        m_goalPublisher.set(getPoses(
            ShooterSubsystem.SHOT_ANGLE.getRadians(),
            0,
            m_pivotSubsystem.getGoalAngle(),
            m_climberSubsystem.getGoalHeight(),
            m_climberSubsystem.getGoalHeight()));

        SwerveDriveState state = m_chassisSubsystem.getState();
        Rotation2d chassisGoalAngle = m_chassisSubsystem.getGoalAngle();
        if (chassisGoalAngle == null) {
            chassisGoalAngle = state.Pose.getRotation();
        }
        m_goalRobotPose.accept(new Pose2d(state.Pose.getX(), state.Pose.getY(), chassisGoalAngle));

        m_shooterSimBalls.calculatePosition(m_shooterSubsystem.getLaunchSpeed(), ChassisSpeeds.fromRobotRelativeSpeeds(m_chassisSubsystem.getState().Speeds, m_chassisSubsystem.getState().Pose.getRotation()), m_chassisSubsystem.getState().Pose);
    }

    private Pose3d[] getPoses(double shooterPitchRad, double pizzaAngleDeg, double pivotAngleDeg, double leftClimberHeight, double rightClimberHeight) {
        double turretYaw = 0;

        Pose3d pizzaPose = new Pose3d(
            Units.inchesToMeters(2), Units.inchesToMeters(2), Units.inchesToMeters(4.5),
            new Rotation3d(Math.toRadians(0), Math.toRadians(0), Math.toRadians(pizzaAngleDeg)));

        Pose3d turretPose = new Pose3d(
            Units.inchesToMeters(-10), Units.inchesToMeters(-9), Units.inchesToMeters(17),
            new Rotation3d(Math.toRadians(0), -shooterPitchRad, Math.toRadians(turretYaw)));

        Pose3d intakePose = new Pose3d(
            Units.inchesToMeters(10), Units.inchesToMeters(0), Units.inchesToMeters(12.5),
            new Rotation3d(Math.toRadians(0), Math.toRadians(-pivotAngleDeg), Math.toRadians(0)));

        Pose3d leftClimberPose = new Pose3d(
            Units.inchesToMeters(-3), Units.inchesToMeters(12), leftClimberHeight,
            new Rotation3d(Math.toRadians(0), Math.toRadians(0), Math.toRadians(-90)));

        Pose3d rightClimberPose = new Pose3d(
            Units.inchesToMeters(5), Units.inchesToMeters(12), rightClimberHeight,
            new Rotation3d(Math.toRadians(0), Math.toRadians(0), Math.toRadians(-90)));

        return new Pose3d[] {
            pizzaPose,
            leftClimberPose,
            rightClimberPose,
            intakePose,
            turretPose,
        };
    }

}
