package com.gos.lib.sensors;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSensor {
    private final NetworkTableEntry m_horizontalAngle;
    private final NetworkTableEntry m_verticalAngle;
    private final NetworkTableEntry m_isVisible;
    private final NetworkTableEntry m_pipeline;
    private final NetworkTableEntry m_pythonRobot;
    private final NetworkTableEntry m_ledOff;

    public LimelightSensor(String limelightName) {
        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable(limelightName);

        m_horizontalAngle = limelightTable.getEntry("tx");
        m_verticalAngle = limelightTable.getEntry("ty");
        m_isVisible = limelightTable.getEntry("tv");
        m_pipeline = limelightTable.getEntry("pipeline");
        m_pythonRobot = limelightTable.getEntry("llrobot");
        m_ledOff = limelightTable.getEntry("ledMode");

    }

    public double getDistance(double limelightHeight, double mountingAngleDeg) {
        double distance;
        distance = (limelightHeight) / Math.tan(-1 * Units.degreesToRadians(mountingAngleDeg + m_verticalAngle.getDouble(0)));
        return distance;
    }

    public double getAngle() {
        return m_horizontalAngle.getDouble(0);
    }

    public boolean isVisible() {
        return m_isVisible.getDouble(0) != 0;
    }

    public void setPythonNumber(Number[] number) {
        m_pythonRobot.setNumberArray(number);
    }

    public void setPipeline(double pipelineNum) {
        m_pipeline.setNumber(pipelineNum);
    }

    public void ledOff(double number) {
        m_ledOff.setDouble(number);
    }

}
