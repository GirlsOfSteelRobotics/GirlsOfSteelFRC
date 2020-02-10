package com.gos.infinite_recharge.sd_widgets;

import com.google.common.collect.ImmutableList;
import com.gos.infinite_recharge.sd_widgets.control_panel.ControlPanelDataType;
import com.gos.infinite_recharge.sd_widgets.control_panel.ControlPanelWidget;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;

@Description(group = "com.gos.infinite_recharge.sd_widgets", name = "GirlsOfSteelPlugin2020", version = PluginVersion.VERSION, summary = "Widget for the 2020 Girls of Steel")
public class GirlsOfSteelRobotPlugin2020 extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(ControlPanelWidget.class));

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(new ControlPanelDataType());
    }

}
