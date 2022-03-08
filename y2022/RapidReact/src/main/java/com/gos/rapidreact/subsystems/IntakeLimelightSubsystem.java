package com.gos.rapidreact.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeLimelightSubsystem extends SubsystemBase {

    public static final String LIMELIGHT_NAME = "limelight-george";

    public static final double MOUNTING_ANGLE = 0; // TODO verify angle
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(35); // TODO verify height
    private static final double BLUE_CARGO = 0;
    private static final double RED_CARGO = 1;
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_pipeline; //which camera (color or cargo) to use

    public IntakeLimelightSubsystem() {
        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable(LIMELIGHT_NAME);

        m_horizontalAngle = limelightTable.getEntry("tx");
        m_verticalAngle = limelightTable.getEntry("ty");
        m_isVisible = limelightTable.getEntry("tv");
        m_pipeline = limelightTable.getEntry("pipeline");
    }

    public double distanceToCargo() {
        double distance;
        distance = (LIMELIGHT_HEIGHT) / Math.tan(MOUNTING_ANGLE + m_verticalAngle.getDouble(0));
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
            m_pipeline.setNumber(BLUE_CARGO);
        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            m_pipeline.setNumber(RED_CARGO);
        }
    }
}

