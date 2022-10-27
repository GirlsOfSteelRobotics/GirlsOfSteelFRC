package com.gos.rapidreact.subsystems;


import com.gos.lib.sensors.LimelightSensor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterLimelightSubsystem extends SubsystemBase {
    public static final String LIMELIGHT_NAME = "limelight-george";
    public static final double MOUNTING_ANGLE_DEGREES = 30;
    public static final double MIN_SHOOTING_DISTANCE = 1.46;
    public static final double MAX_SHOOTING_DISTANCE = 3.36;
    public static final double ALLOWABLE_TELEOP_ANGLE_ERROR = 4;
    public static final double ALLOWABLE_AUTO_ANGLE_ERROR = 2;

    public static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(29);
    public static final double TARGET_HEIGHT_METERS = Units.inchesToMeters(104); // hub height
    public static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(30);

    private final LimelightSensor m_limelight;

    // Logging
    private final NetworkTableEntry m_distance;
    private final NetworkTableEntry m_angleError;

    public ShooterLimelightSubsystem() {
        m_limelight = new LimelightSensor(LIMELIGHT_NAME);

        NetworkTable networkTable = NetworkTableInstance.getDefault().getTable("ShooterLimelight");
        m_distance = networkTable.getEntry("Distance");
        m_angleError = networkTable.getEntry("AngleError");
    }

    public double getDistanceToHub() {
        return m_limelight.getDistance(CAMERA_HEIGHT_METERS, TARGET_HEIGHT_METERS, CAMERA_PITCH_RADIANS);
        // return m_limelight.getDistance(LIMELIGHT_HEIGHT, MOUNTING_ANGLE_DEGREES);
    }

    public boolean isVisible() {
        return m_limelight.isVisible();
    }

    public double getAngle() {
        return m_limelight.getHorizontalAngleDegrees();
    }

    public boolean atAcceptableDistance() {
        double distance = getDistanceToHub();
        return distance > MIN_SHOOTING_DISTANCE && distance < MAX_SHOOTING_DISTANCE;
    }

    public boolean atAcceptableAngle() {
        return Math.abs(getAngle()) < ALLOWABLE_TELEOP_ANGLE_ERROR;
    }

    public boolean isReadyToShoot() {
        return atAcceptableAngle() && atAcceptableDistance() && isVisible();
    }

    @Override
    public void periodic() {
        m_distance.setNumber(getDistanceToHub());
        m_angleError.setNumber(getAngle());

        // SmartDashboard.putNumber("Limelight Dist to Hub", getDistanceToHub());

        if (DriverStation.isEnabled()) {
            m_limelight.setLeds(LimelightSensor.LedMode.USE_PIPELINE);
        } else {
            m_limelight.setLeds(LimelightSensor.LedMode.FORCE_OFF);
        }

        m_limelight.setPipeline(2);
    }
}
