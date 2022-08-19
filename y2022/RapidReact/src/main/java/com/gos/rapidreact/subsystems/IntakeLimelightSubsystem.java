package com.gos.rapidreact.subsystems;


import com.gos.lib.sensors.LimelightSensor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeLimelightSubsystem extends SubsystemBase {

    public static final String LIMELIGHT_NAME = "limelight-terry"; // Dr Richards is too long

    public static final double MOUNTING_ANGLE_RADIANS = Units.degreesToRadians(-25);
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(27.25);
    private static final Number[] RED_CARGO_PARAMS = {0};
    private static final Number[] BLUE_CARGO_PARAMS = {1};
    private static final double FANCY_PIPELINE = 3;
    private static final double EXTRA_DISTANCE = Units.feetToMeters(0.5); //want to go past estimated to ensure it gets to the cargo

    private final LimelightSensor m_limelight;

    // Logging
    private final NetworkTableEntry m_distanceEntry;
    private final NetworkTableEntry m_angleError;

    public IntakeLimelightSubsystem() {
        m_limelight = new LimelightSensor(LIMELIGHT_NAME);

        NetworkTable publishTable = NetworkTableInstance.getDefault().getTable("IntakeLimelight");
        m_distanceEntry = publishTable.getEntry("Distance");
        m_angleError = publishTable.getEntry("Angle Error");
    }

    public double distanceToCargo() {
        return m_limelight.getDistance(LIMELIGHT_HEIGHT, 0, MOUNTING_ANGLE_RADIANS) + EXTRA_DISTANCE;
    }

    public double getAngle() {
        return m_limelight.getHorizontalAngleDegrees();
    }

    public boolean isVisible() {
        return m_limelight.isVisible();
    }

    @Override
    public void periodic() {
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
            m_limelight.setPythonNumber(BLUE_CARGO_PARAMS);
        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            m_limelight.setPythonNumber(RED_CARGO_PARAMS);
        }
        m_limelight.setPipeline(FANCY_PIPELINE);

        m_distanceEntry.setNumber(distanceToCargo());
        m_angleError.setNumber(getAngle());
    }
}
