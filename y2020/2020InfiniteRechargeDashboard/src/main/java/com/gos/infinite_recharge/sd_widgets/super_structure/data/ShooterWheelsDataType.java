package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class ShooterWheelsDataType extends ComplexDataType<ShooterWheelsData> {

    public ShooterWheelsDataType() {
        super(SmartDashboardNames.SHOOTER_WHEELS_TABLE_NAME, ShooterWheelsData.class);
    }

    @Override
    public Function<Map<String, Object>, ShooterWheelsData> fromMap() {
        return ShooterWheelsData::new;
    }

    @Override
    public ShooterWheelsData getDefaultValue() {
        return new ShooterWheelsData();
    }
}
