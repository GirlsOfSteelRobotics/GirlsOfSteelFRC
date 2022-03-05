package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class ControlPanelData extends ComplexData<ControlPanelData> {

    private final double m_speed;


    public ControlPanelData() {
        this(0.0);
    }

    public ControlPanelData(double speed) {
        m_speed = speed;
    }

    public ControlPanelData(Map<String, Object> map) {
        this("", map);
    }

    public ControlPanelData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.CONTROL_PANEL_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.CONTROL_PANEL_SPEED, m_speed);
        return output;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.CONTROL_PANEL_SPEED);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ControlPanelData [", "]")
                    .add("speed=" + m_speed)
                    .toString();
    }
}
