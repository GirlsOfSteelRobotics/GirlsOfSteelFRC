package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class CodelabSuperStructureDataType extends ComplexDataType<CodelabSuperStructureData> {
    public static final CodelabSuperStructureDataType INSTANCE = new CodelabSuperStructureDataType();

    private CodelabSuperStructureDataType() {
        super(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME, CodelabSuperStructureData.class);
    }

    @Override
    public Function<Map<String, Object>, CodelabSuperStructureData> fromMap() {
        return CodelabSuperStructureData::new;
    }

    @Override
    public CodelabSuperStructureData getDefaultValue() {
        return new CodelabSuperStructureData();
    }
}
