package com.gos.rapidreact.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterLimelightSubsystem extends SubsystemBase {

    public static final double MOUNTING_ANGLE = 0;
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(35);
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;

    public ShooterLimelightSubsystem() {
        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

        m_horizontalAngle = limelightTable.getEntry("tx");
        m_verticalAngle = limelightTable.getEntry("ty");
        m_isVisible = limelightTable.getEntry("tv");

    }

    public double distanceToHub() {
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

