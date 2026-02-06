package com.gos.rebuilt.subsystems;

import com.gos.rebuilt.FireOnTheRun;
import com.gos.rebuilt.ShooterSimBalls;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructureViz extends SubsystemBase {

    private final StructArrayPublisher<Pose3d> m_superStructurePublisher;
    private final PivotSubsystem m_pivotSubsystem;
    private final PizzaSubsystem m_pizzaSubsystem;
    private final ShooterSimBalls m_shooterSimBalls;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    private final FireOnTheRun m_fireOnTheRun;

    public SuperStructureViz(PivotSubsystem pivotSubsystem, PizzaSubsystem pizzaSubsystem, ChassisSubsystem chassisSubsystem, ShooterSubsystem shooterSubsystem) {
        m_superStructurePublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz", Pose3d.struct).publish();
        this.m_pivotSubsystem = pivotSubsystem;
        this.m_pizzaSubsystem = pizzaSubsystem;
        m_chassisSubsystem = chassisSubsystem;
        m_shooterSubsystem = shooterSubsystem;
        m_shooterSimBalls = new ShooterSimBalls("ShotPreview");
        m_fireOnTheRun = new FireOnTheRun(m_chassisSubsystem, m_shooterSubsystem);
    }

    @Override
    public void periodic() {

        double shooterPitch = 45;
        double turretYaw = 0;
        double pivotAngle = m_pivotSubsystem.getAngle();
        double pizzaAngle = m_pizzaSubsystem.getAngle();

        Pose3d pizzaPose = new Pose3d(
            Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(0),
            new Rotation3d(Math.toRadians(0), Math.toRadians(0), Math.toRadians(pizzaAngle)));

        Pose3d turretPose = new Pose3d(
            Units.inchesToMeters(-3), Units.inchesToMeters(0), Units.inchesToMeters(14),
            new Rotation3d(Math.toRadians(0), Math.toRadians(-shooterPitch), Math.toRadians(turretYaw)));

        Pose3d intakePose = new Pose3d(
            Units.inchesToMeters(12), Units.inchesToMeters(0), Units.inchesToMeters(9),
            new Rotation3d(Math.toRadians(0), Math.toRadians(-pivotAngle), Math.toRadians(0)));

        m_superStructurePublisher.set(new Pose3d[] {
            pizzaPose,
            turretPose,
            intakePose,
        });

        m_shooterSimBalls.calculatePosition(m_shooterSubsystem.getLaunchSpeed(), m_chassisSubsystem.getState().Speeds, m_chassisSubsystem.getState().Pose);
        m_fireOnTheRun.glue();
    }

}
