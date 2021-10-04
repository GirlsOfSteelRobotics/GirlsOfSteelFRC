package com.gos.codelabs.shuffleboard.sd_widgets;

import com.gos.codelabs.shuffleboard.sd_widgets.ss.CodelabSuperStructureWidget;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.CodelabSuperStructureDataType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(group = "com.gos.codelabs.shuffleboard.sd_widgets", name = "GirlsOfSteelCodelabPlugin", version = PluginVersion.VERSION, summary = "Widget for the GOS Shuffleoard Codelab")
public class GirlsOfSteelCodelabPlugin extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(CodelabSuperStructureWidget.class));

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            CodelabSuperStructureDataType.INSTANCE);

    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return ImmutableMap.<DataType, ComponentType>builder()
                .put(CodelabSuperStructureDataType.INSTANCE, WidgetType.forAnnotatedWidget(CodelabSuperStructureWidget.class))
                .build();
    }
}
