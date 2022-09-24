package com.gos.lib.shuffleboard.swerve;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class SwerveDriveDataType extends ComplexDataType<SwerveDriveData> {
    public static final SwerveDriveDataType INSTANCE = new SwerveDriveDataType();

    private SwerveDriveDataType() {
        super(SmartDashboardNames.SWERVE_DRIVE_TABLE_NAME, SwerveDriveData.class);
    }

    @Override
    public Function<Map<String, Object>, SwerveDriveData> fromMap() {
        return SwerveDriveData::new;
    }

    @Override
    public SwerveDriveData getDefaultValue() {
        return new SwerveDriveData();
    }
}
