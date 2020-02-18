package com.gos.infinite_recharge.sd_widgets.leds.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

public class LedData extends ComplexData<LedData> {
    private final String m_values;

    public LedData() {
        this("");
    }

    public LedData(String values) {
        m_values = values;
    }

    public LedData(Map<String, Object> map) {
        this((String) map.getOrDefault(SmartDashboardNames.LED_VALUES, ""));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(SmartDashboardNames.LED_VALUES, m_values);
        return map;
    }

    public String getValues() {
        return m_values;
    }
}
