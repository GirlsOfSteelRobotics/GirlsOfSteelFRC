package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class ShooterIntakeDataType extends ComplexDataType<ShooterIntakeData> {
    public static final ShooterIntakeDataType INSTANCE = new ShooterIntakeDataType();

    private ShooterIntakeDataType() {
        super(SmartDashboardNames.SHOOTER_INTAKE_TABLE_NAME, ShooterIntakeData.class);
    }

    @Override
    public Function<Map<String, Object>, ShooterIntakeData> fromMap() {
        return ShooterIntakeData::new;
    }

    @Override
    public ShooterIntakeData getDefaultValue() {
        return new ShooterIntakeData();
    }
}
