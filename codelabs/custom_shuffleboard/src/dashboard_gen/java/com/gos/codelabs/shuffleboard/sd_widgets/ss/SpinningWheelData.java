package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class SpinningWheelData extends ComplexData<SpinningWheelData> {

    private final double m_speed;


    public SpinningWheelData() {
        this(0.0);
    }

    public SpinningWheelData(double speed) {
        m_speed = speed;
    }

    public SpinningWheelData(Map<String, Object> map) {
        this("", map);
    }

    public SpinningWheelData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED, m_speed);
        return output;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SpinningWheelData [", "]")
                    .add("speed=" + m_speed)
                    .toString();
    }
}
