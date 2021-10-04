package com.gos.codelabs.shuffleboard.sd_widgets.ss.data;

import com.gos.codelabs.shuffleboard.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class SpinningWheelData extends ComplexData<SpinningWheelData> {

    private final double m_motorSpeed;


    public SpinningWheelData() {
        this(0.0);
    }

    public SpinningWheelData(Map<String, Object> map) {
        this("", map);
    }

    public SpinningWheelData(String prefix, Map<String, Object> map) {
        this(
            (Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED, 0.0));
    }

    public SpinningWheelData(double motorSpeed) {
        m_motorSpeed = motorSpeed;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED, m_motorSpeed);
        return map;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.SPINNING_WHEEL_MOTOR_SPEED);

        return changed;
    }

    public double getMotorSpeed() {
        return m_motorSpeed;
    }
}
