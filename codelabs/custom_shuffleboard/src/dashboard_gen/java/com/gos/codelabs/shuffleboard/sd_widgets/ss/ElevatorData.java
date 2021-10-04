package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class ElevatorData extends ComplexData<ElevatorData> {

    private final double m_speed;
    private final double m_height;
    private final boolean m_atUpperLimit;
    private final boolean m_atLowerLimit;


    public ElevatorData() {
        this(0.0, 0.0, false, false);
    }

    public ElevatorData(double speed, double height, boolean atUpperLimit, boolean atLowerLimit) {
        m_speed = speed;
        m_height = height;
        m_atUpperLimit = atUpperLimit;
        m_atLowerLimit = atLowerLimit;
    }

    public ElevatorData(Map<String, Object> map) {
        this("", map);
    }

    public ElevatorData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.ELEVATOR_MOTOR_SPEED, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.ELEVATOR_HEIGHT, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.ELEVATOR_UPPER_LIMIT_SWITCH, false),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.ELEVATOR_LOWER_LIMIT_SWITCH, false));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.ELEVATOR_MOTOR_SPEED, m_speed);
        output.put(prefix + SmartDashboardNames.ELEVATOR_HEIGHT, m_height);
        output.put(prefix + SmartDashboardNames.ELEVATOR_UPPER_LIMIT_SWITCH, m_atUpperLimit);
        output.put(prefix + SmartDashboardNames.ELEVATOR_LOWER_LIMIT_SWITCH, m_atLowerLimit);
        return output;
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

    public boolean isAtUpperLimit() {
        return m_atUpperLimit;
    }

    public boolean isAtLowerLimit() {
        return m_atLowerLimit;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ElevatorData [", "]")
                    .add("speed=" + m_speed)
                    .add("height=" + m_height)
                    .add("atUpperLimit=" + m_atUpperLimit)
                    .add("atLowerLimit=" + m_atLowerLimit)
                    .toString();
    }
}
