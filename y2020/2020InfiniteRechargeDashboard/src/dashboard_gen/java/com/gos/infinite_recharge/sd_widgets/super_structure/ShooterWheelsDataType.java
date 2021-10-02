package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class ShooterWheelsDataType extends ComplexDataType<ShooterWheelsData> {
    public static final ShooterWheelsDataType INSTANCE = new ShooterWheelsDataType();

    private ShooterWheelsDataType() {
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
