package com.gos.shuffleboard_codelab.sd_widgets.ss.data;

import com.gos.shuffleboard_codelab.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class ElevatorData extends ComplexData<ElevatorData> {

    private final double m_speed;
    private final double m_height;
    private final boolean m_atLowerLimit;
    private final boolean m_atUpperLimit;


    public ElevatorData() {
        this(0.0, 0.0, false, false);
    }

    public ElevatorData(Map<String, Object> map) {
        this("", map);
    }

    public ElevatorData(String prefix, Map<String, Object> map) {
        this(
            (Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.ELEVATOR_MOTOR_SPEED, 0.0),
            (Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.ELEVATOR_HEIGHT, 0.0),
            (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.ELEVATOR_UPPER_LIMIT_SWITCH, false),
            (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.ELEVATOR_LOWER_LIMIT_SWITCH, false));
    }

    public ElevatorData(double speed, double height, boolean atLowerLimit, boolean atUpperLimit) {
        m_speed = speed;
        m_height = height;
        m_atLowerLimit = atLowerLimit;
        m_atUpperLimit = atUpperLimit;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.ELEVATOR_MOTOR_SPEED, m_speed);
        map.put(prefix + SmartDashboardNames.ELEVATOR_HEIGHT, m_height);
        map.put(prefix + SmartDashboardNames.ELEVATOR_UPPER_LIMIT_SWITCH, m_atLowerLimit);
        map.put(prefix + SmartDashboardNames.ELEVATOR_LOWER_LIMIT_SWITCH, m_atUpperLimit);
        return map;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.ELEVATOR_MOTOR_SPEED);
        changed |= changes.containsKey(prefix + SmartDashboardNames.ELEVATOR_HEIGHT);
        changed |= changes.containsKey(prefix + SmartDashboardNames.ELEVATOR_UPPER_LIMIT_SWITCH);
        changed |= changes.containsKey(prefix + SmartDashboardNames.ELEVATOR_LOWER_LIMIT_SWITCH);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }

    public double getHeight() {
        return m_height;
    }

    public boolean isAtLowerLimit() {
        return m_atLowerLimit;
    }

    public boolean isAtUpperLimit() {
        return m_atUpperLimit;
    }
}
