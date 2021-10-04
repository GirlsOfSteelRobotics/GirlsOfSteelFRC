package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class ElevatorDataType extends ComplexDataType<ElevatorData> {
    public static final ElevatorDataType INSTANCE = new ElevatorDataType();

    private ElevatorDataType() {
        super(SmartDashboardNames.ELEVATOR_TABLE_NAME, ElevatorData.class);
    }

    @Override
    public Function<Map<String, Object>, ElevatorData> fromMap() {
        return ElevatorData::new;
    }

    @Override
    public ElevatorData getDefaultValue() {
        return new ElevatorData();
    }
}
