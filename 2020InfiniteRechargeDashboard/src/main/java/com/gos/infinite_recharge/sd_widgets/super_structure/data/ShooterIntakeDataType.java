package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class ShooterIntakeDataType extends ComplexDataType<ShooterIntakeData> {

    public ShooterIntakeDataType() {
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
