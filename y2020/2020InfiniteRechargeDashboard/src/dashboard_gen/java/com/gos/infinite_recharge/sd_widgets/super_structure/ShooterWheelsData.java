package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class ShooterWheelsData extends ComplexData<ShooterWheelsData> {

    private final double m_currentRpm;
    private final double m_goalRpm;
    private final double m_speed;


    public ShooterWheelsData() {
        this(0.0, 0.0, 0.0);
    }

    public ShooterWheelsData(double currentRpm, double goalRpm, double speed) {
        m_currentRpm = currentRpm;
        m_goalRpm = goalRpm;
        m_speed = speed;
    }

    public ShooterWheelsData(Map<String, Object> map) {
        this("", map);
    }

    public ShooterWheelsData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SHOOTER_WHEELS_GOAL_RPM, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SHOOTER_WHEELS_CURRENT_RPM, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SHOOTER_WHEELS_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.SHOOTER_WHEELS_GOAL_RPM, m_currentRpm);
        output.put(prefix + SmartDashboardNames.SHOOTER_WHEELS_CURRENT_RPM, m_goalRpm);
        output.put(prefix + SmartDashboardNames.SHOOTER_WHEELS_SPEED, m_speed);
        return output;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.SHOOTER_WHEELS_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_WHEELS_GOAL_RPM);
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_WHEELS_CURRENT_RPM);
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_WHEELS_SPEED);

        return changed;
    }

    public double getCurrentRpm() {
        return m_currentRpm;
    }

    public double getGoalRpm() {
        return m_goalRpm;
    }

    public double getSpeed() {
        return m_speed;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ShooterWheelsData [", "]")
                    .add("currentRpm=" + m_currentRpm)
                    .add("goalRpm=" + m_goalRpm)
                    .add("speed=" + m_speed)
                    .toString();
    }
}
