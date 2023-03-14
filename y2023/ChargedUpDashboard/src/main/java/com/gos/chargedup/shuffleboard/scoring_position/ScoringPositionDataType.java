package com.gos.chargedup.shuffleboard.scoring_position;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class ScoringPositionDataType extends ComplexDataType<ScoringPositionData> {
    public static final ScoringPositionDataType INSTANCE = new ScoringPositionDataType();

    private ScoringPositionDataType() {
        super(SmartDashboardNames.SCORING_POSITION, ScoringPositionData.class);
    }

    @Override
    public Function<Map<String, Object>, ScoringPositionData> fromMap() {
        return ScoringPositionData::new;
    }

    @Override
    public ScoringPositionData getDefaultValue() {
        return new ScoringPositionData();
    }
}
