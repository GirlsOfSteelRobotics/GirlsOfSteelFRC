package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class PunchDataType extends ComplexDataType<PunchData> {
    public static final PunchDataType INSTANCE = new PunchDataType();

    private PunchDataType() {
        super(SmartDashboardNames.PUNCH_TABLE_NAME, PunchData.class);
    }

    @Override
    public Function<Map<String, Object>, PunchData> fromMap() {
        return PunchData::new;
    }

    @Override
    public PunchData getDefaultValue() {
        return new PunchData();
    }
}
