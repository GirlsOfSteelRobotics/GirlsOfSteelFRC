package com.gos.chargedup.shuffleboard.scoring_position;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.util.function.Consumer;

public class ScoringPositionController {


    @FXML
    private ToggleButton m_highLeft;
    @FXML
    private ToggleButton m_highMiddle;
    @FXML
    private ToggleButton m_highRight;

    @FXML
    private ToggleButton m_mediumLeft;
    @FXML
    private ToggleButton m_mediumMiddle;
    @FXML
    private ToggleButton m_mediumRight;

    @FXML
    private ToggleButton m_lowLeft;
    @FXML
    private ToggleButton m_lowMiddle;
    @FXML
    private ToggleButton m_lowRight;

    private Consumer<ScoringPositionData> m_listener;

    private final ToggleGroup m_toggleGroup = new ToggleGroup();

    @FXML
    public void initialize() {

        setUpButton(m_highLeft, AutoTurretCommands.HIGH_LEFT);
        setUpButton(m_highMiddle, AutoTurretCommands.HIGH_MIDDLE);
        setUpButton(m_highRight, AutoTurretCommands.HIGH_RIGHT);

        setUpButton(m_mediumLeft, AutoTurretCommands.MEDIUM_LEFT);
        setUpButton(m_mediumMiddle, AutoTurretCommands.MEDIUM_MIDDLE);
        setUpButton(m_mediumRight, AutoTurretCommands.MEDIUM_RIGHT);

        setUpButton(m_lowLeft, AutoTurretCommands.LOW_LEFT);
        setUpButton(m_lowMiddle, AutoTurretCommands.LOW_MIDDLE);
        setUpButton(m_lowRight, AutoTurretCommands.LOW_RIGHT);
    }

    private void handleButtonClick(ToggleButton button, AutoTurretCommands position) {
        if (m_listener == null) {
            return;
        }

        if (button.isSelected()) {
            m_listener.accept(new ScoringPositionData(position.ordinal()));
        } else {
            m_listener.accept(new ScoringPositionData(AutoTurretCommands.NONE.ordinal()));
        }

    }

    private void setUpButton(ToggleButton button, AutoTurretCommands scoringPosition) {
        button.setOnAction(event -> handleButtonClick(button, scoringPosition));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        m_toggleGroup.getToggles().add(button);
    }

    public void setListener(Consumer<ScoringPositionData> consumer) {
        m_listener = consumer;
    }


    public void updateScoringPosition(ScoringPositionData scoringPositionData) {
        // TODO implement
    }


}
