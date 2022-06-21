package com.gos.infinite_recharge.sd_widgets.control_panel;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class ControlPanelData extends ComplexData<ControlPanelData> {

    private final Double m_simAngle;
    private final Double m_simR;
    private final Double m_simG;
    private final Double m_simB;
    private final String m_colorSeen;


    public ControlPanelData() {
        this(0.0, 0.0, 0.0, 0.0, "");
    }

    public ControlPanelData(Double simAngle, Double simR, Double simG, Double simB, String colorSeen) {
        m_simAngle = simAngle;
        m_simR = simR;
        m_simG = simG;
        m_simB = simB;
        m_colorSeen = colorSeen;
    }

    public ControlPanelData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_R_COLOR, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_G_COLOR, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_B_COLOR, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.CONTROL_PANEL_COLOR_SEEN, ""));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_ANGLE, m_simAngle);
        output.put(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_R_COLOR, m_simR);
        output.put(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_G_COLOR, m_simG);
        output.put(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_B_COLOR, m_simB);
        output.put(SmartDashboardNames.CONTROL_PANEL_COLOR_SEEN, m_colorSeen);
        return output;
    }

    public Double getSimAngle() {
        return m_simAngle;
    }

    public Double getSimR() {
        return m_simR;
    }

    public Double getSimG() {
        return m_simG;
    }

    public Double getSimB() {
        return m_simB;
    }

    public String getColorSeen() {
        return m_colorSeen;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ControlPanelData [", "]")
                    .add("simAngle=" + m_simAngle)
                    .add("simR=" + m_simR)
                    .add("simG=" + m_simG)
                    .add("simB=" + m_simB)
                    .add("colorSeen=" + m_colorSeen)
                    .toString();
    }
}
