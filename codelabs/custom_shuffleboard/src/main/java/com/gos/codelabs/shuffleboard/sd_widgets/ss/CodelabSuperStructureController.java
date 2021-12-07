package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class CodelabSuperStructureController {

    private static final double MAX_WIDTH = 35; // TODO figure out real value
    private static final double MAX_HEIGHT = 60; // TODO figure out real value


    @FXML
    public Rectangle m_base;

    @FXML
    public Circle m_spinningWheel;

    @FXML
    public Rectangle m_punch;

    @FXML
    public Rectangle m_elevatorBox;

    @FXML
    public Rectangle m_liftUpperLimitSwitch;

    @FXML
    public Rectangle m_liftLowerLimitSwitch;

    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;

    @FXML
    public void initialize() {

        ///////////////////////////////////////////////////////////
        // Controls the inches <-> pixels conversion. Don't touch
        double minWidthMultiplier = 1;
        m_pane.setMinHeight(MAX_HEIGHT * minWidthMultiplier);
        m_pane.setMinWidth(MAX_WIDTH * minWidthMultiplier);

        DoubleBinding scaleBinding = Bindings.createDoubleBinding(() -> Math.min(m_pane.getWidth() / MAX_WIDTH, m_pane.getHeight() / MAX_HEIGHT), m_pane.widthProperty(), m_pane.heightProperty());

        Scale scale = new Scale();
        scale.xProperty().bind(scaleBinding);
        scale.yProperty().bind(scaleBinding);

        m_group.getTransforms().add(scale);
        ///////////////////////////////////////////////////////////

        // TODO implement
    }


    public void updateElevator(ElevatorData elevatorData) {
        // TODO implement
    }

    public void updatePunch(PunchData punchData) {
        // TODO implement
    }

    public void updateSpinningWheel(SpinningWheelData spinningWheelData) {
        // TODO implement
    }


}
