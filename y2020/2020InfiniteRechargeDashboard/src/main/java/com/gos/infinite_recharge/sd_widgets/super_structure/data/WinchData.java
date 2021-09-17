package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class WinchData extends ComplexData<WinchData> {

    private final double m_speed;


    public WinchData() {
        this(0.0);
    }

    public WinchData(Map<String, Object> map) {
        this("", map);
    }

    public WinchData(String prefix, Map<String, Object> map) {
        this((Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.WINCH_SPEED, 0.0));
    }

    public WinchData(double speed) {
        m_speed = speed;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.WINCH_SPEED, m_speed);
        return map;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.WINCH_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.WINCH_SPEED);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }
}
