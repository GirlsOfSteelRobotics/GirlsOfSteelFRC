package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class LiftDataType extends ComplexDataType<LiftData> {
    public static final LiftDataType INSTANCE = new LiftDataType();

    private LiftDataType() {
        super(SmartDashboardNames.LIFT_TABLE_NAME, LiftData.class);
    }

    @Override
    public Function<Map<String, Object>, LiftData> fromMap() {
        return LiftData::new;
    }

    @Override
    public LiftData getDefaultValue() {
        return new LiftData();
    }
}
