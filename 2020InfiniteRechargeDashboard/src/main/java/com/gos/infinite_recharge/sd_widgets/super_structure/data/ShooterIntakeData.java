package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class ShooterIntakeData extends ComplexData<ShooterIntakeData> {

    private final double m_speed;
    private final boolean m_position;


    public ShooterIntakeData() {
        this(0.0, false);
    }

    public ShooterIntakeData(Map<String, Object> map) {
        this("", map);
    }

    public ShooterIntakeData(String prefix, Map<String, Object> map) {
        this((Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.SHOOTER_INTAKE_SPEED, 0.0), (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.SHOOTER_INTAKE_POSITION, false));
    }

    public ShooterIntakeData(double speed, boolean position) {
        m_speed = speed;
        m_position = position;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.SHOOTER_INTAKE_SPEED, m_speed);
        map.put(prefix + SmartDashboardNames.SHOOTER_INTAKE_POSITION, m_position);
        return map;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.SHOOTER_INTAKE_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_INTAKE_SPEED);
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_INTAKE_POSITION);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }

    public boolean getPosition() {
        return m_position;
    }
}
