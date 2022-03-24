package com.gos.rapidreact.subsystems;


import com.gos.lib.properties.PropertyManager;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterLimelightSubsystem extends SubsystemBase {
    public static final String LIMELIGHT_NAME = "limelight-george";
    public static final double MOUNTING_ANGLE_DEGREES = 30;
    public static final double LIMELIGHT_HEIGHT = Units.inchesToMeters(29);
    public static final double HUB_HEIGHT = Units.inchesToMeters(104); //8 ft, 8 in
    public static final double MIN_SHOOTING_DISTANCE = 1.46;
    public static final double MAX_SHOOTING_DISTANCE = 3.36;
    // public static final PropertyManager.IProperty<Double> MAX_SHOOTING_DISTANCE = PropertyManager.createDoubleProperty(false, "Max Shoot Dist", 5); //meters
    // public static final PropertyManager.IProperty<Double> MIN_SHOOTING_DISTANCE = PropertyManager.createDoubleProperty(false, "Min Shoot Dist", 0); //meters
    public static final PropertyManager.IProperty<Double> ALLOWABLE_ANGLE_ERROR = PropertyManager.createDoubleProperty(false, "Allowable Shoot Angle Error", 4); //degrees

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
    }

    public double getDistanceToHub() {
        double distance;
        distance = (HUB_HEIGHT - LIMELIGHT_HEIGHT) / Math.tan(Math.toRadians(MOUNTING_ANGLE_DEGREES + m_verticalAngle.getDouble(0)));
        return distance;
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    public double angleError() {
        return m_horizontalAngle.getDouble(0);
    }

    public boolean atAcceptableDistance() {
        double distance = getDistanceToHub();
        return distance > MIN_SHOOTING_DISTANCE && distance < MAX_SHOOTING_DISTANCE;
    }

    public boolean atAcceptableAngle() {
        return Math.abs(angleError()) < ALLOWABLE_ANGLE_ERROR.getValue();
    }

    public boolean isReadyToShoot() {
        return atAcceptableAngle() && atAcceptableDistance();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("ShooterLimelight - Distance", getDistanceToHub());
        SmartDashboard.putNumber("ShooterLimelight - Angle", angleError());

        if (DriverStation.isEnabled()) {
            m_ledOff.setDouble(0);
        } else {
            m_ledOff.setDouble(1);
        }

        m_pipeline.setNumber(2);
    }
}

