package com.gos.chargedup.shuffleboard.scoring_position;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;



@Description(name = SmartDashboardNames.WIDGET_NAME, dataTypes = {ScoringPositionData.class})
@ParametrizedController("scoring_position_widget.fxml")
public class ScoringPositionWidget extends SimpleAnnotatedWidget<ScoringPositionData> {
    @FXML
    private Pane m_root;

    @FXML
    protected ScoringPositionController m_widgetController;

    @Override
    public Pane getView() {
        return m_root;
    }

    @FXML
    public void initialize() {

        dataOrDefault.addListener((ignored, oldData, newData) -> {
            m_widgetController.updateScoringPosition(newData);
        });

        m_widgetController.setListener(this::setData);
    }

}
