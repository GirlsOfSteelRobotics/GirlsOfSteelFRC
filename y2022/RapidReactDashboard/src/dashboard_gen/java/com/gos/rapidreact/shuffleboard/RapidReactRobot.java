package com.gos.rapidreact.shuffleboard;

import com.gos.rapidreact.shuffleboard.super_structure.SuperStructureWidget;
import com.gos.rapidreact.shuffleboard.super_structure.SuperStructureDataType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(group = "com.gos.rapidreact.shuffleboard", name = "RapidReactRobot", version = PluginVersion.VERSION, summary = "Widgets for the Rapid React Robot")
public class RapidReactRobot extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(SuperStructureWidget.class));

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            SuperStructureDataType.INSTANCE);

    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return ImmutableMap.<DataType, ComponentType>builder()
                .put(SuperStructureDataType.INSTANCE, WidgetType.forAnnotatedWidget(SuperStructureWidget.class))
                .build();
    }
}
