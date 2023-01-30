package com.gos.chargedup.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class SuperStructureDataType extends ComplexDataType<SuperStructureData> {
    public static final SuperStructureDataType INSTANCE = new SuperStructureDataType();

    private SuperStructureDataType() {
        super(SmartDashboardNames.SUPER_STRUCTURE, SuperStructureData.class);
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
