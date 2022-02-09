package com.gos.rapidreact.subsystems;


import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.sql.Driver;

public class IntakeLimelightSubsystem extends SubsystemBase {
    public static final double MOUNTING_ANGLE = 0;
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(35);
    public static final double BLUE_CARGO = 0;
    public static final double RED_CARGO = 1;
    private final NetworkTableEntry m_angleToCargo;
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_pipeline; //which camera (color or cargo) to use
    private final DriverStation.Alliance m_alliance;

    public IntakeLimelightSubsystem() {
        m_angleToCargo = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        m_isVisible = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv");
        m_pipeline = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline");
        m_alliance = DriverStation.getAlliance();
    }

    public double distanceToCargo() {
        double distance;
        distance = (LIMELIGHT_HEIGHT) / Math.tan(MOUNTING_ANGLE + m_angleToCargo.getDouble(0));
        return distance;
    }

    public double getAngle() {
        return m_angleToCargo.getDouble(0) + MOUNTING_ANGLE;
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    public void periodic() {
        if (m_alliance == DriverStation.Alliance.Blue) {
            m_pipeline.setNumber(BLUE_CARGO);
        }
        if (m_alliance == DriverStation.Alliance.Blue) {
            m_pipeline.setNumber(RED_CARGO);
        }
    }
}

