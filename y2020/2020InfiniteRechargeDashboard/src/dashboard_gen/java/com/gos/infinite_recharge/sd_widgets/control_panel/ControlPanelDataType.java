package com.gos.infinite_recharge.sd_widgets.control_panel;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public final class ControlPanelDataType extends ComplexDataType<ControlPanelData> {
    public static final ControlPanelDataType INSTANCE = new ControlPanelDataType();

    private ControlPanelDataType() {
        super(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME, ControlPanelData.class);
    }

    @Override
    public Function<Map<String, Object>, ControlPanelData> fromMap() {
        return ControlPanelData::new;
    }

    @Override
    public ControlPanelData getDefaultValue() {
        return new ControlPanelData();
    }
}
