package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class ShooterConveyorDataType extends ComplexDataType<ShooterConveyorData> {

    public ShooterConveyorDataType() {
        super(SmartDashboardNames.SHOOTER_CONVEYOR_TABLE_NAME, ShooterConveyorData.class);
    }

    @Override
    public Function<Map<String, Object>, ShooterConveyorData> fromMap() {
        return ShooterConveyorData::new;
    }

    @Override
    public ShooterConveyorData getDefaultValue() {
        return new ShooterConveyorData();
    }
}
