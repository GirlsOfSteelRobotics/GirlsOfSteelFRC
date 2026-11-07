package com.gos.rebuilt.subsystems;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;
import com.gos.rebuilt.ShooterSimBalls;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructureViz extends SubsystemBase {
    private static final Translation3d SPINDEXER_PIVOT = new Translation3d(0.0769, 0.039, 0.1667);
    private static final Translation3d INTAKE_PIVOT = new Translation3d(0.046, 0.0, 0.3699);

    private final StructArrayPublisher<Pose3d> m_measuredPublisher;
    private final StructArrayPublisher<Pose3d> m_goalPublisher;
    private final StructPublisher<Pose2d> m_goalRobotPose;

    private final PivotSubsystem m_pivotSubsystem;
    private final PizzaSubsystem m_pizzaSubsystem;
    private final ShooterSimBalls m_shooterSimBalls;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;


    public SuperStructureViz(PivotSubsystem pivotSubsystem, PizzaSubsystem pizzaSubsystem, ChassisSubsystem chassisSubsystem, ShooterSubsystem shooterSubsystem) {
        m_measuredPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Measured", Pose3d.struct).publish();
        m_goalPublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz/Goal", Pose3d.struct).publish();
        m_goalRobotPose = NetworkTableInstance.getDefault().getStructTopic("SuperStructureViz/Robot Pose Goal", Pose2d.struct).publish();

        m_pivotSubsystem = pivotSubsystem;
        m_pizzaSubsystem = pizzaSubsystem;
        m_chassisSubsystem = chassisSubsystem;
        m_shooterSubsystem = shooterSubsystem;

        m_shooterSimBalls = new ShooterSimBalls("ShotPreview");
    }

    @Override
    public void periodic() {
        m_measuredPublisher.set(getPoses(m_pizzaSubsystem.getAngle(), m_pivotSubsystem.getAngle()));
        m_goalPublisher.set(getPoses(0, m_pivotSubsystem.getGoalAngle()));

        SwerveDriveState state = m_chassisSubsystem.getState();
        Rotation2d chassisGoalAngle = m_chassisSubsystem.getGoalAngle();
        if (chassisGoalAngle == null) {
            chassisGoalAngle = state.Pose.getRotation();
        }
        m_goalRobotPose.accept(new Pose2d(state.Pose.getX(), state.Pose.getY(), chassisGoalAngle));

        m_shooterSimBalls.calculatePosition(m_shooterSubsystem.getLaunchSpeed(), ChassisSpeeds.fromRobotRelativeSpeeds(m_chassisSubsystem.getState().Speeds, m_chassisSubsystem.getState().Pose.getRotation()), m_chassisSubsystem.getState().Pose);
    }

    private Pose3d[] getPoses(double pizzaAngleDeg, double pivotAngleDeg) {
        Pose3d pizzaPose = new Pose3d(SPINDEXER_PIVOT, new Rotation3d(0, 0, Math.toRadians(pizzaAngleDeg)));
        Pose3d intakePose = new Pose3d(INTAKE_PIVOT, new Rotation3d(0, Math.toRadians(-pivotAngleDeg), 0));

        return new Pose3d[] {
            pizzaPose,
            intakePose,
        };
    }

}
