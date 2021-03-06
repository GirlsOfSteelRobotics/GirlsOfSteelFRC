package com.gos.rapidreact.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeLimelightSubsystem extends SubsystemBase {

    public static final String LIMELIGHT_NAME = "limelight-terry"; // Dr Richards is too long

    public static final double MOUNTING_ANGLE_DEGREES = -25; //degrees
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(27.25);
    private static final Number[] RED_CARGO_PARAMS = {0};
    private static final Number[] BLUE_CARGO_PARAMS = {1};
    private static final double FANCY_PIPELINE = 3;
    private static final double EXTRA_DISTANCE = Units.feetToMeters(0.5); //want to go past estimated to ensure it gets to the cargo
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_pipeline; //which camera (color or cargo) to use
    private final NetworkTableEntry m_pythonRobot;

    // Logging
    private final NetworkTableEntry m_distanceEntry;
    private final NetworkTableEntry m_angleError;

    public IntakeLimelightSubsystem() {
        NetworkTable georgeLimelightTable = NetworkTableInstance.getDefault().getTable(LIMELIGHT_NAME);

        m_horizontalAngle = georgeLimelightTable.getEntry("tx");
        m_verticalAngle = georgeLimelightTable.getEntry("ty");
        m_isVisible = georgeLimelightTable.getEntry("tv");
        m_pipeline = georgeLimelightTable.getEntry("pipeline");

        NetworkTable publishTable = NetworkTableInstance.getDefault().getTable("IntakeLimelight");
        m_distanceEntry = publishTable.getEntry("Distance");
        m_angleError = publishTable.getEntry("Angle Error");
        m_pythonRobot = georgeLimelightTable.getEntry("llrobot");
    }

    public double distanceToCargo() {
        double distance;
        distance = (LIMELIGHT_HEIGHT) / Math.tan(-1 * Units.degreesToRadians(MOUNTING_ANGLE_DEGREES + m_verticalAngle.getDouble(0))) + EXTRA_DISTANCE;
        return distance;
    }

    public double getAngle() {
        return m_horizontalAngle.getDouble(0);
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    @Override
    public void periodic() {
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
            m_pythonRobot.setNumberArray(BLUE_CARGO_PARAMS);
        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            m_pythonRobot.setNumberArray(RED_CARGO_PARAMS);
        }
        m_pipeline.setNumber(FANCY_PIPELINE);

        m_distanceEntry.setNumber(distanceToCargo());
        m_angleError.setNumber(getAngle());
    }
}
