package com.gos.rapidreact.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeLimelightSubsystem extends SubsystemBase {
    public static final double MOUNTING_ANGLE = 0;
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(35);
    private final NetworkTableEntry m_angleToCargo;
    private final NetworkTableEntry m_cargoHeight;

    public IntakeLimelightSubsystem() {
        m_angleToCargo = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        m_cargoHeight = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv");
    }

    public double distanceToCargo() {
        double distance;
        distance = (m_cargoHeight.getDouble(0) - LIMELIGHT_HEIGHT) / Math.tan(MOUNTING_ANGLE + m_angleToCargo.getDouble(0));
        return distance;
    }

    public double getAngle() {
        return m_angleToCargo.getDouble(0) + MOUNTING_ANGLE;
    }
}

