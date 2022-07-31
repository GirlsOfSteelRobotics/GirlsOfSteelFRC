package com.gos.rapidreact.subsystems.sim;

import com.gos.lib.properties.PropertyManager;
import com.gos.rapidreact.subsystems.IntakeLimelightSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import org.snobotv2.camera.LimelightSimulator;
import org.snobotv2.camera.TargetLocation;
import org.snobotv2.camera.games.RapidReactTargets;
import org.snobotv2.coordinate_gui.CameraRayPublisher;

import java.util.ArrayList;
import java.util.List;

public class LimelightSim {
    private static final PropertyManager.IProperty<Double> CARGO_X = PropertyManager.createDoubleProperty(false, "Sim Limelight Cargo X", 0);
    private static final PropertyManager.IProperty<Double> CARGO_Y = PropertyManager.createDoubleProperty(false, "Sim Limelight Cargo Y", 0);

    private static final double INTAKE_MAX_DISTANCE = Double.MAX_VALUE;
    private static final double SHOOTER_MAX_DISTANCE = Double.MAX_VALUE;

    private final LimelightSimulator m_intakeSimulator;
    private final LimelightSimulator m_shooterSimulator;
    private final CameraRayPublisher m_rayPublisher;
    private final CargoTarget m_cargoTarget;

    private static class CargoTarget extends TargetLocation {

        public CargoTarget() {
            super(new Pose2d());
        }

        public void update() {
            setPosition(new Pose2d(CARGO_X.getValue(), CARGO_Y.getValue(), Rotation2d.fromDegrees(0)));
        }

    }


    public LimelightSim() {
        Transform2d intakeTransform = new Transform2d(new Translation2d(), Rotation2d.fromDegrees(0));
        Transform2d shooterTransform = new Transform2d(new Translation2d(), Rotation2d.fromDegrees(180));

        m_cargoTarget = new CargoTarget();
        m_intakeSimulator = new LimelightSimulator(List.of(m_cargoTarget), intakeTransform, IntakeLimelightSubsystem.LIMELIGHT_HEIGHT, INTAKE_MAX_DISTANCE, IntakeLimelightSubsystem.LIMELIGHT_NAME);
        m_shooterSimulator = new LimelightSimulator(RapidReactTargets.getTargets(), shooterTransform, ShooterLimelightSubsystem.CAMERA_HEIGHT_METERS, SHOOTER_MAX_DISTANCE, ShooterLimelightSubsystem.LIMELIGHT_NAME);
        m_rayPublisher = new CameraRayPublisher();
    }

    @SuppressWarnings("PMD")
    public void update(Pose2d robotPose) {
        m_cargoTarget.update();

        List<Pose2d> bestTargets = new ArrayList<>();

        TargetLocation bestIntake = m_intakeSimulator.update(robotPose);
        if (bestIntake != null) {
            bestTargets.add(bestIntake.getPosition());
        }

        TargetLocation bestShooter = m_shooterSimulator.update(robotPose);
        if (bestShooter != null) {
            bestTargets.add(bestShooter.getPosition());
        }

        // No targets seen
        if (bestTargets.isEmpty()) {
            m_rayPublisher.clear();
        }
        else {
            m_rayPublisher.publish(robotPose, bestTargets.toArray(new Pose2d[0]));
        }
    }
}
