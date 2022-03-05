package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class ShooterIntakeData extends ComplexData<ShooterIntakeData> {

    private final double m_speed;
    private final boolean m_position;


    public ShooterIntakeData() {
        this(0.0, false);
    }

    public ShooterIntakeData(double speed, boolean position) {
        m_speed = speed;
        m_position = position;
    }

    public ShooterIntakeData(Map<String, Object> map) {
        this("", map);
    }

    public ShooterIntakeData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SHOOTER_INTAKE_SPEED, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SHOOTER_INTAKE_POSITION, false));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.SHOOTER_INTAKE_SPEED, m_speed);
        output.put(prefix + SmartDashboardNames.SHOOTER_INTAKE_POSITION, m_position);
        return output;
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

    public boolean isPosition() {
        return m_position;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ShooterIntakeData [", "]")
                    .add("speed=" + m_speed)
                    .add("position=" + m_position)
                    .toString();
    }
}
