package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class ControlPanelDataType extends ComplexDataType<ControlPanelData> {

    public ControlPanelDataType() {
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
