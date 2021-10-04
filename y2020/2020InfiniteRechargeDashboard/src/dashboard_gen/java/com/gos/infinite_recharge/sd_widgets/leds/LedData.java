package com.gos.infinite_recharge.sd_widgets.leds;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class LedData extends ComplexData<LedData> {

    private final String m_values;


    public LedData() {
        this("");
    }

    public LedData(String values) {
        m_values = values;
    }

    public LedData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.LED_VALUES, ""));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.LED_VALUES, m_values);
        return output;
    }

    public String getValues() {
        return m_values;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "LedData [", "]")
                    .add("values=" + m_values)
                    .toString();
    }
}
