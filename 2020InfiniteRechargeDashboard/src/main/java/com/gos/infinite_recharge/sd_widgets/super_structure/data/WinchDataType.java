package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class WinchDataType extends ComplexDataType<WinchData> {

    public WinchDataType() {
        super(SmartDashboardNames.WINCH_TABLE_NAME, WinchData.class);
    }

    @Override
    public Function<Map<String, Object>, WinchData> fromMap() {
        return WinchData::new;
    }

    @Override
    public WinchData getDefaultValue() {
        return new WinchData();
    }
}
