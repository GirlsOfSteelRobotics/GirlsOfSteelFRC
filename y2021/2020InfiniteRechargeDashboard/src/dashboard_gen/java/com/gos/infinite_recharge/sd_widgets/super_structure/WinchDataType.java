package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class WinchDataType extends ComplexDataType<WinchData> {
    public static final WinchDataType INSTANCE = new WinchDataType();

    private WinchDataType() {
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
