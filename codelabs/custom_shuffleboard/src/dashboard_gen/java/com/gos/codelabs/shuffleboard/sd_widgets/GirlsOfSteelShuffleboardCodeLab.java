package com.gos.codelabs.shuffleboard.sd_widgets;

import com.gos.codelabs.shuffleboard.sd_widgets.ss.CodelabSuperStructureWidget;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.CodelabSuperStructureDataType;
import com.google.common.collect.ImmutableList;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;

@Description(group = "com.gos.shuffleboard_codelab.sd_widgets", name = "GirlsOfSteelShuffleboardCodeLab", version = PluginVersion.VERSION, summary = "Widget for the Shuffleboard Codelab")
public class GirlsOfSteelShuffleboardCodeLab extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(CodelabSuperStructureWidget.class)
            );

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            new CodelabSuperStructureDataType()
        );

    }

}
