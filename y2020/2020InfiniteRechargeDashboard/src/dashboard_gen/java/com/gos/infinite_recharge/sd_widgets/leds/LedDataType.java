package com.gos.infinite_recharge.sd_widgets.leds;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class LedDataType extends ComplexDataType<LedData> {

    public LedDataType() {
        super(SmartDashboardNames.LED_SIM_TABLE_NAME, LedData.class);
    }

    @Override
    public Function<Map<String, Object>, LedData> fromMap() {
        return LedData::new;
    }

    @Override
    public LedData getDefaultValue() {
        return new LedData();
    }
}
