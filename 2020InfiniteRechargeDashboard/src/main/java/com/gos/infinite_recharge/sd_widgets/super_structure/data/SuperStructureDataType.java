package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class SuperStructureDataType extends ComplexDataType<SuperStructureData> {

    public SuperStructureDataType() {
        super(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME, SuperStructureData.class);
    }

    @Override
    public Function<Map<String, Object>, SuperStructureData> fromMap() {
        return SuperStructureData::new;
    }

    @Override
    public SuperStructureData getDefaultValue() {
        return new SuperStructureData();
    }
}
