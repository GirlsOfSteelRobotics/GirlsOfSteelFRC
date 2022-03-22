package com.gos.rapidreact.subsystems;


import com.gos.lib.properties.PropertyManager;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterLimelightSubsystem extends SubsystemBase {
    public static final String LIMELIGHT_NAME = "limelight-george";
    public static final double MOUNTING_ANGLE = 0; // TODO verify angle
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(35); // TODO verify height
    public static final PropertyManager.IProperty<Double> MAX_SHOOTING_DISTANCE = PropertyManager.createDoubleProperty(false, "Max Shoot Dist", 5); //meters
    public static final PropertyManager.IProperty<Double> MIN_SHOOTING_DISTANCE = PropertyManager.createDoubleProperty(false, "Min Shoot Dist", 2); //meters
    public static final PropertyManager.IProperty<Double> ALLOWABLE_ANGLE_ERROR = PropertyManager.createDoubleProperty(false, "Allowable Shoot Angle Error", 2); //degrees

    public static final PropertyManager.IProperty<Double> TO_XY_MAX_DISTANCE = PropertyManager.createDoubleProperty(false, "To XY Max Dist", 4);

    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;
    private final NetworkTableEntry m_ledOff; // NOPMD
    private final NetworkTableEntry m_pipeline; //which camera (color or cargo) to use

    public ShooterLimelightSubsystem() {
        NetworkTable richardsLimelightTable = NetworkTableInstance.getDefault().getTable(LIMELIGHT_NAME);

        m_horizontalAngle = richardsLimelightTable.getEntry("tx");
        m_verticalAngle = richardsLimelightTable.getEntry("ty");
        m_isVisible = richardsLimelightTable.getEntry("tv");

        m_ledOff = richardsLimelightTable.getEntry("ledMode");

        m_pipeline = richardsLimelightTable.getEntry("pipeline");
        m_pipeline.setDouble(2);

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

    public boolean atAcceptableDistance() {
        return getDistanceToHub() > MIN_SHOOTING_DISTANCE.getValue() && getDistanceToHub() < MAX_SHOOTING_DISTANCE.getValue();
    }

    public boolean atAcceptableAngle() {
        return Math.abs(angleError()) < ALLOWABLE_ANGLE_ERROR.getValue();
    }
}

