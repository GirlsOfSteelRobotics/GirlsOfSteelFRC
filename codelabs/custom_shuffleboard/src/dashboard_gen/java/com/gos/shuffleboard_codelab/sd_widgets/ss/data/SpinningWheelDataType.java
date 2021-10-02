package com.gos.shuffleboard_codelab.sd_widgets.ss.data;

import com.gos.shuffleboard_codelab.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class SpinningWheelDataType extends ComplexDataType<SpinningWheelData> {

    public SpinningWheelDataType() {
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
