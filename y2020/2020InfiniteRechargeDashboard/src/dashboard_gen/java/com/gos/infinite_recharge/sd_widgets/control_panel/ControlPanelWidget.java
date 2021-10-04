package com.gos.infinite_recharge.sd_widgets.control_panel;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;



@Description(name = SmartDashboardNames.WIDGET_NAME, dataTypes = {ControlPanelData.class})
@ParametrizedController("control_panel_widget.fxml")
public class ControlPanelWidget extends SimpleAnnotatedWidget<ControlPanelData> {
    @FXML
    private Pane m_root;

    @FXML
    protected ControlPanelController m_widgetController;

    @Override
    public Pane getView() {
        return m_root;
    }

    @FXML
    public void initialize() {

        dataOrDefault.addListener((ignored, oldData, newData) -> {
            m_widgetController.updateControlPanel(newData);
        });
    }

}
