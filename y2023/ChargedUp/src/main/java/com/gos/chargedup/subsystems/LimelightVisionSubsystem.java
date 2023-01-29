package com.gos.chargedup.subsystems;


import com.gos.lib.sensors.LimelightSensor;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;

import java.io.IOException;
import java.util.Optional;

public class LimelightVisionSubsystem extends SubsystemBase {
    private final LimelightSensor m_limelight;

    private static final String LIMELIGHT_NAME = "limelight";
    private static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(15);
    private static final double TARGET_HEIGHT_METERS = Units.inchesToMeters(20);
    private static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(70);
    public LimelightVisionSubsystem() {
        m_limelight = new LimelightSensor(LIMELIGHT_NAME);

        try {
            AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void periodic() {
        m_limelight.setLeds(LimelightSensor.LedMode.FORCE_OFF);
    }

    public double getHorizontalAngle() {
        return m_limelight.getHorizontalAngleDegrees();
    }

    public double getDistance() {
        return m_limelight.getDistance(CAMERA_HEIGHT_METERS, TARGET_HEIGHT_METERS, CAMERA_PITCH_RADIANS);
    }

    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_limelight.getRobotPose();
        EstimatedRobotPose estimatedRobotPose = new EstimatedRobotPose(m_limelight.getRobotPose(), Timer.getFPGATimestamp());

        Optional<EstimatedRobotPose> estimate = Optional.of(estimatedRobotPose);

        return estimate;
    }

    }

