package com.gos.rebuilt.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructureViz extends SubsystemBase {

    private final StructArrayPublisher<Pose3d> m_superStructurePublisher;

    public SuperStructureViz() {
        m_superStructurePublisher = NetworkTableInstance.getDefault().getStructArrayTopic("SuperStructureViz", Pose3d.struct).publish();
    }

    @Override
    public void periodic() {

        // TODO grab these from subsystems
        double hackyTimestamp = Timer.getFPGATimestamp();
        double shooterPitch = 5 * hackyTimestamp;
        double turretYaw = 30 * hackyTimestamp;
        double intakeAngle = 10 * hackyTimestamp;
        double pizzaAngle = 360 * hackyTimestamp;

        Pose3d pizzaPose = new Pose3d(
            Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(0),
            new Rotation3d(Math.toRadians(0), Math.toRadians(0), Math.toRadians(pizzaAngle)));

        Pose3d turretPose = new Pose3d(
            Units.inchesToMeters(-3), Units.inchesToMeters(0), Units.inchesToMeters(14),
            new Rotation3d(Math.toRadians(0), Math.toRadians(-shooterPitch), Math.toRadians(turretYaw)));

        Pose3d intakePose = new Pose3d(
            Units.inchesToMeters(12), Units.inchesToMeters(0), Units.inchesToMeters(9),
            new Rotation3d(Math.toRadians(0), Math.toRadians(-intakeAngle), Math.toRadians(0)));

        m_superStructurePublisher.set(new Pose3d[]{
            pizzaPose,
            turretPose,
            intakePose,
        });
    }

}
