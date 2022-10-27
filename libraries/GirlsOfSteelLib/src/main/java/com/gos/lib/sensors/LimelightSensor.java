package com.gos.lib.sensors;

import edu.wpi.first.math.ComputerVisionUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSensor {

    private static final double INVALID_VALUE = -999;

    public enum LedMode {
        USE_PIPELINE(0),
        FORCE_OFF(1),
        FORCE_BLINK(2),
        FORCE_ON(3);

        private final double m_val;

        LedMode(double val) {
            m_val = val;
        }
    }

    public enum CamMode {
        VISION_PROCESSING(0),
        DRIVER_CAMERA(1);

        private final double m_val;

        CamMode(double val) {
            m_val = val;
        }
    }

    // Camera -> Robot
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;
    private final NetworkTableEntry m_area;
    private final NetworkTableEntry m_latency;
    private final NetworkTableEntry m_pythonLimelight;

    // Robot -> Camera
    private final NetworkTableEntry m_ledMode;
    private final NetworkTableEntry m_camMode;
    private final NetworkTableEntry m_pipeline;
    private final NetworkTableEntry m_pythonRobot;

    public LimelightSensor() {
        this("limelight");
    }

    public LimelightSensor(String limelightName) {

        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable(limelightName);

        // Camera -> Robot
        m_isVisible = limelightTable.getEntry("tv");
        m_horizontalAngle = limelightTable.getEntry("tx");
        m_verticalAngle = limelightTable.getEntry("ty");
        m_area = limelightTable.getEntry("ta");
        m_latency = limelightTable.getEntry("tl");
        m_pythonLimelight = limelightTable.getEntry("llpython");

        // Robot -> Camera
        m_ledMode = limelightTable.getEntry("ledMode");
        m_camMode = limelightTable.getEntry("camMode");
        m_pipeline = limelightTable.getEntry("pipeline");
        m_pythonRobot = limelightTable.getEntry("llrobot");
    }

    // distance from wpilib docs
    public double getDistance(
        double cameraHeightMeters,
        double targetHeightMeters,
        double cameraPitchRadians) {
        return ComputerVisionUtil.calculateDistanceToTarget(cameraHeightMeters, targetHeightMeters, cameraPitchRadians,
            getVerticalAngleRadians(), getHorizontalAngleRadians());
    }

    public double getHorizontalAngleDegrees() {
        return m_horizontalAngle.getDouble(INVALID_VALUE);
    }

    public double getHorizontalAngleRadians() {
        return Units.degreesToRadians(getHorizontalAngleDegrees());
    }

    public double getVerticalAngleDegrees() {
        return m_verticalAngle.getDouble(INVALID_VALUE);
    }

    public double getVerticalAngleRadians() {
        return Units.degreesToRadians(getVerticalAngleDegrees());
    }

    public double getArea() {
        return m_area.getDouble(INVALID_VALUE);
    }

    public double getLatency() {
        return m_latency.getDouble(INVALID_VALUE);
    }

    public Number[] getPythonData() {
        return m_pythonLimelight.getNumberArray(new Number[]{});
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    public void setPythonNumber(Number... number) {
        m_pythonRobot.setNumberArray(number);
    }

    public void setPipeline(double pipelineNum) {
        m_pipeline.setNumber(pipelineNum);
    }

    public void setLeds(LedMode ledMode) {
        m_ledMode.setDouble(ledMode.m_val);
    }

    public void setCamMode(CamMode camMode) {
        m_camMode.setDouble(camMode.m_val);
    }
}
