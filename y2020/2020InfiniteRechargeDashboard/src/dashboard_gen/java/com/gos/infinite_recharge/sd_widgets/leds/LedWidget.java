package com.gos.infinite_recharge.sd_widgets.leds;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;



@Description(name = SmartDashboardNames.WIDGET_NAME, dataTypes = {LedData.class})
@ParametrizedController("led_widget.fxml")
public class LedWidget extends SimpleAnnotatedWidget<LedData> {
    @FXML
    private Pane m_root;

    @FXML
    protected LedController m_widgetController;

    @Override
    public Pane getView() {
        return m_root;
    }

    @FXML
    public void initialize() {

        dataOrDefault.addListener((ignored, oldData, newData) -> {
            m_widgetController.updateLed(newData);
        });
    }

}
