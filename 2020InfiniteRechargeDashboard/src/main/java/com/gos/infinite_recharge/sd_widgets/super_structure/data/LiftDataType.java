package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class LiftDataType extends ComplexDataType<LiftData> {

    public LiftDataType() {
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
