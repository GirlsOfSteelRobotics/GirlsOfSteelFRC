package com.gos.infinite_recharge.sd_widgets.leds;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class LedDataType extends ComplexDataType<LedData> {
    public static final LedDataType INSTANCE = new LedDataType();

    private LedDataType() {
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
