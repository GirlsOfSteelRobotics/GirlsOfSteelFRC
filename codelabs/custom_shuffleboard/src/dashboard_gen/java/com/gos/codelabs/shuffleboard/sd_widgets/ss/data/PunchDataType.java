package com.gos.codelabs.shuffleboard.sd_widgets.ss.data;

import com.gos.codelabs.shuffleboard.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class PunchDataType extends ComplexDataType<PunchData> {

    public PunchDataType() {
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
