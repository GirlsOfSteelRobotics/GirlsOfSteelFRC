package com.gos.lib.shuffleboard;

import com.gos.lib.shuffleboard.swerve.SwerveDriveWidget;
import com.gos.lib.shuffleboard.swerve.SwerveDriveDataType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(group = "com.gos.lib.shuffleboard", name = "SwerveDrive", version = PluginVersion.VERSION, summary = "XXX")
public class SwerveDrive extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(SwerveDriveWidget.class));

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            SwerveDriveDataType.INSTANCE);

    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return ImmutableMap.<DataType, ComponentType>builder()
                .put(SwerveDriveDataType.INSTANCE, WidgetType.forAnnotatedWidget(SwerveDriveWidget.class))
                .build();
    }
}
