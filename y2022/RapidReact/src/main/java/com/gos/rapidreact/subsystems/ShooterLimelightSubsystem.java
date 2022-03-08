package com.gos.rapidreact.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterLimelightSubsystem extends SubsystemBase {

    public static final String LIMELIGHT_NAME = "limelight-terry"; // Dr Richardson is too long
    public static final double MOUNTING_ANGLE = 0; // TODO verify angle
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(35); // TODO verify height
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;

    public ShooterLimelightSubsystem() {
        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable(LIMELIGHT_NAME);

        m_horizontalAngle = limelightTable.getEntry("tx");
        m_verticalAngle = limelightTable.getEntry("ty");
        m_isVisible = limelightTable.getEntry("tv");

    }

    public double getDistanceToHub() {
        double distance;
        distance = (LIMELIGHT_HEIGHT) / Math.tan(MOUNTING_ANGLE + m_verticalAngle.getDouble(0));
        return distance;
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    public double angleError() {
        return m_horizontalAngle.getDouble(0);
    }
}

