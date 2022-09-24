package com.gos.lib.shuffleboard.swerve;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class SwerveModuleData extends ComplexData<SwerveModuleData> {

    private final double m_currentAngle;
    private final double m_desiredAngle;
    private final double m_driveMotorPercentage;
    private final double m_turnMotorPercentage;


    public SwerveModuleData() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public SwerveModuleData(double currentAngle, double desiredAngle, double driveMotorPercentage, double turnMotorPercentage) {
        m_currentAngle = currentAngle;
        m_desiredAngle = desiredAngle;
        m_driveMotorPercentage = driveMotorPercentage;
        m_turnMotorPercentage = turnMotorPercentage;
    }

    public SwerveModuleData(Map<String, Object> map) {
        this("", map);
    }

    public SwerveModuleData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.CURRENT_ANGLE, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.DESIRED_ANGLE, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.DRIVE_MOTOR_PERCENTAGE, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.TURN_MOTOR_PERCENTAGE, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.CURRENT_ANGLE, m_currentAngle);
        output.put(prefix + SmartDashboardNames.DESIRED_ANGLE, m_desiredAngle);
        output.put(prefix + SmartDashboardNames.DRIVE_MOTOR_PERCENTAGE, m_driveMotorPercentage);
        output.put(prefix + SmartDashboardNames.TURN_MOTOR_PERCENTAGE, m_turnMotorPercentage);
        return output;
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.CURRENT_ANGLE);
        changed |= changes.containsKey(prefix + SmartDashboardNames.DESIRED_ANGLE);
        changed |= changes.containsKey(prefix + SmartDashboardNames.DRIVE_MOTOR_PERCENTAGE);
        changed |= changes.containsKey(prefix + SmartDashboardNames.TURN_MOTOR_PERCENTAGE);

        return changed;
    }

    public double getCurrentAngle() {
        return m_currentAngle;
    }

    public double getDesiredAngle() {
        return m_desiredAngle;
    }

    public double getDriveMotorPercentage() {
        return m_driveMotorPercentage;
    }

    public double getTurnMotorPercentage() {
        return m_turnMotorPercentage;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SwerveModuleData [", "]")
                    .add("currentAngle=" + m_currentAngle)
                    .add("desiredAngle=" + m_desiredAngle)
                    .add("driveMotorPercentage=" + m_driveMotorPercentage)
                    .add("turnMotorPercentage=" + m_turnMotorPercentage)
                    .toString();
    }
}
