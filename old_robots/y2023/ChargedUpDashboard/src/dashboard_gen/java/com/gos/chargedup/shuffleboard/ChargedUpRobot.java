package com.gos.chargedup.shuffleboard;

import com.gos.chargedup.shuffleboard.super_structure.SuperStructureWidget;
import com.gos.chargedup.shuffleboard.super_structure.SuperStructureDataType;
import com.gos.chargedup.shuffleboard.scoring_position.ScoringPositionWidget;
import com.gos.chargedup.shuffleboard.scoring_position.ScoringPositionDataType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(group = "com.gos.chargedup.shuffleboard", name = "ChargedUpRobot", version = PluginVersion.VERSION, summary = "Widgets for the Charged Up Robot")
public class ChargedUpRobot extends Plugin {

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
            WidgetType.forAnnotatedWidget(SuperStructureWidget.class),
            WidgetType.forAnnotatedWidget(ScoringPositionWidget.class));

    }

    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            SuperStructureDataType.INSTANCE,
            ScoringPositionDataType.INSTANCE);

    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return ImmutableMap.<DataType, ComponentType>builder()
                .put(SuperStructureDataType.INSTANCE, WidgetType.forAnnotatedWidget(SuperStructureWidget.class))
                .put(ScoringPositionDataType.INSTANCE, WidgetType.forAnnotatedWidget(ScoringPositionWidget.class))
                .build();
    }
}
