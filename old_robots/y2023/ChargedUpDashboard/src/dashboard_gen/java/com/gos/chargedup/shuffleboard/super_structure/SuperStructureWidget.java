package com.gos.chargedup.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;



@Description(name = SmartDashboardNames.WIDGET_NAME, dataTypes = {SuperStructureData.class})
@ParametrizedController("super_structure_widget.fxml")
public class SuperStructureWidget extends SimpleAnnotatedWidget<SuperStructureData> {
    @FXML
    private Pane m_root;

    @FXML
    protected SuperStructureController m_widgetController;

    @Override
    public Pane getView() {
        return m_root;
    }

    @FXML
    public void initialize() {

        dataOrDefault.addListener((ignored, oldData, newData) -> {
            m_widgetController.updateSuperStructure(newData);
        });
    }

}
