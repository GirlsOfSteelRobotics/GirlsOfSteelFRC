package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


import java.util.Map;


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
            final Map<String, Object> changes = newData.changesFrom(oldData);
            if (!changes.isEmpty()) {
                System.out.println("changes : " + changes); // NOPMD
            }

            if (ControlPanelData.hasChanged(changes)) {
                m_widgetController.updateControlPanel(newData.getControlPanel());
            }

            if (LiftData.hasChanged(changes)) {
                m_widgetController.updateLift(newData.getLift());
            }

            if (ShooterConveyorData.hasChanged(changes)) {
                m_widgetController.updateShooterConveyor(newData.getShooterConveyor());
            }

            if (ShooterIntakeData.hasChanged(changes)) {
                m_widgetController.updateShooterIntake(newData.getShooterIntake());
            }

            if (ShooterWheelsData.hasChanged(changes)) {
                m_widgetController.updateShooterWheels(newData.getShooterWheels());
            }

            if (WinchData.hasChanged(changes)) {
                m_widgetController.updateWinch(newData.getWinch());
            }


        });
    }

}
