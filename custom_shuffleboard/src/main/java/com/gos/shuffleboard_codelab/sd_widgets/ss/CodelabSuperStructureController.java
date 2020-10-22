package com.gos.shuffleboard_codelab.sd_widgets.ss;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import com.gos.shuffleboard_codelab.sd_widgets.ss.data.ElevatorData;
import com.gos.shuffleboard_codelab.sd_widgets.ss.data.PunchData;
import com.gos.shuffleboard_codelab.sd_widgets.ss.data.SpinningWheelData;

public class CodelabSuperStructureController {

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
