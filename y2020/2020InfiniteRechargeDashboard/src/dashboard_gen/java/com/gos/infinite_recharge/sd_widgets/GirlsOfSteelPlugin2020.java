package com.gos.infinite_recharge.sd_widgets;

import com.gos.infinite_recharge.sd_widgets.super_structure.SuperStructureWidget;
import com.gos.infinite_recharge.sd_widgets.super_structure.SuperStructureDataType;
import com.gos.infinite_recharge.sd_widgets.leds.LedWidget;
import com.gos.infinite_recharge.sd_widgets.leds.LedDataType;
import com.gos.infinite_recharge.sd_widgets.control_panel.ControlPanelWidget;
import com.gos.infinite_recharge.sd_widgets.control_panel.ControlPanelDataType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(group = "com.gos.infinite_recharge.sd_widgets", name = "GirlsOfSteelPlugin2020", version = PluginVersion.VERSION, summary = "Widget for the 2020 Girls of Steel")
public class GirlsOfSteelPlugin2020 extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(SuperStructureWidget.class),
            WidgetType.forAnnotatedWidget(LedWidget.class),
            WidgetType.forAnnotatedWidget(ControlPanelWidget.class));

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            SuperStructureDataType.INSTANCE,
            LedDataType.INSTANCE,
            ControlPanelDataType.INSTANCE);

    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return ImmutableMap.<DataType, ComponentType>builder()
                .put(SuperStructureDataType.INSTANCE, WidgetType.forAnnotatedWidget(SuperStructureWidget.class))
                .put(LedDataType.INSTANCE, WidgetType.forAnnotatedWidget(LedWidget.class))
                .put(ControlPanelDataType.INSTANCE, WidgetType.forAnnotatedWidget(ControlPanelWidget.class))
                .build();
    }
}
