package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class ShooterWheelsData extends ComplexData<ShooterWheelsData> {

    private final double m_speed;
    private final double m_currentRpm;
    private final double m_goalRpm;


    public ShooterWheelsData() {
        this(0.0, 0.0, 0.0);
    }

    public ShooterWheelsData(Map<String, Object> map) {
        this("", map);
    }

    public ShooterWheelsData(String prefix, Map<String, Object> map) {
        this((Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.SHOOTER_WHEELS_SPEED, 0.0), (Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.SHOOTER_WHEELS_GOAL_RPM, 0.0), (Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.SHOOTER_WHEELS_CURRENT_RPM, 0.0));
    }

    public ShooterWheelsData(double speed, double currentRpm, double goalRpm) {
        m_speed = speed;
        m_currentRpm = currentRpm;
        m_goalRpm = goalRpm;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.SHOOTER_WHEELS_SPEED, m_speed);
        map.put(prefix + SmartDashboardNames.SHOOTER_WHEELS_GOAL_RPM, m_currentRpm);
        map.put(prefix + SmartDashboardNames.SHOOTER_WHEELS_CURRENT_RPM, m_goalRpm);
        return map;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.SHOOTER_WHEELS_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_WHEELS_SPEED);
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_WHEELS_GOAL_RPM);
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_WHEELS_CURRENT_RPM);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }

    public double getCurrentRpm() {
        return m_currentRpm;
    }

    public double getGoalRpm() {
        return m_goalRpm;
    }
}
