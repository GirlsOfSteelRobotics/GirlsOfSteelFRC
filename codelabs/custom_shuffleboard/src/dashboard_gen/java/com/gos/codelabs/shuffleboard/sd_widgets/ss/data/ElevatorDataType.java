package com.gos.codelabs.shuffleboard.sd_widgets.ss.data;

import com.gos.codelabs.shuffleboard.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class ElevatorDataType extends ComplexDataType<ElevatorData> {

    public ElevatorDataType() {
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
