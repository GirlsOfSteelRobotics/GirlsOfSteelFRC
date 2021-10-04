package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class SpinningWheelDataType extends ComplexDataType<SpinningWheelData> {
    public static final SpinningWheelDataType INSTANCE = new SpinningWheelDataType();

    private SpinningWheelDataType() {
        super(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME, SpinningWheelData.class);
    }

    @Override
    public Function<Map<String, Object>, SpinningWheelData> fromMap() {
        return SpinningWheelData::new;
    }

    @Override
    public SpinningWheelData getDefaultValue() {
        return new SpinningWheelData();
    }
}
