package com.gos.infinite_recharge.sd_widgets.control_panel;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

public class ControlPanelData extends ComplexData<ControlPanelData> {

    private final Double m_simAngle;
    private final Double m_simR;
    private final Double m_simG;
    private final Double m_simB;
    private final String m_colorSeen;

    public ControlPanelData() {
        this("", null, null, null, null);
    }

    public ControlPanelData(Map<String, Object> map) {
        this((String) map.getOrDefault(SmartDashboardNames.CONTROL_PANEL_COLOR_SEEN, ""),
            (Double) map.getOrDefault(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_ANGLE, null),
            (Double) map.getOrDefault(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_R_COLOR, null),
            (Double) map.getOrDefault(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_G_COLOR, null),
            (Double) map.getOrDefault(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_B_COLOR, null)
        );
    }

    public ControlPanelData(String colorScene, Double simAngle, Double simR, Double simG, Double simB) {
        m_simAngle = simAngle;
        m_simR = simR;
        m_simG = simG;
        m_simB = simB;
        m_colorSeen = colorScene;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    /**
     * Gets a representation of this data as a map.
     *
     * @param prefix The prefix to prepend to the field names
     * @return The map representation
     */
    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.CONTROL_PANEL_COLOR_SEEN, m_colorSeen);
        map.put(prefix + SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_ANGLE, m_simAngle);
        map.put(prefix + SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_R_COLOR, m_simR);
        map.put(prefix + SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_G_COLOR, m_simG);
        map.put(prefix + SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_B_COLOR, m_simB);
        return map;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return changes.containsKey(SmartDashboardNames.CONTROL_PANEL_COLOR_SEEN)
            || changes.containsKey(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_ANGLE)
            || changes.containsKey(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_R_COLOR)
            || changes.containsKey(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_G_COLOR)
            || changes.containsKey(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_B_COLOR);
    }

    public Double getSimAngle() {
        return m_simAngle;
    }

    public String getColorScene() {
        return m_colorSeen;
    }
}
