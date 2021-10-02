package com.gos.infinite_recharge.sd_widgets.control_panel;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.Map;

@Description(name = "Infinite Recharge Control Panel Widget", dataTypes = {ControlPanelData.class})
@ParametrizedController("control_panel_widget.fxml")
public class ControlPanelWidget extends SimpleAnnotatedWidget<ControlPanelData> {
    @FXML
    private Pane m_root;

    @FXML
    protected ControlPanelController m_controlPanelController;

    /**
     * Called after JavaFX initialization.
     */
    @FXML
    public void initialize() {
        m_controlPanelController.setColorConsumer((ControlPanelController.Colors color) -> {
            NetworkTableInstance.getDefault().getTable(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME).getEntry(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_R_COLOR).setDouble(color.m_red);
            NetworkTableInstance.getDefault().getTable(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME).getEntry(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_G_COLOR).setDouble(color.m_green);
            NetworkTableInstance.getDefault().getTable(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME).getEntry(SmartDashboardNames.CONTROL_PANEL_SIM_WHEEL_B_COLOR).setDouble(color.m_blue);
        });
        dataOrDefault.addListener((ignored, oldData, newData) -> {
            final Map<String, Object> changes = newData.changesFrom(oldData);

            if (ControlPanelData.hasChanged(changes)) {
                updateControlPanel(newData);
            }
        });
    }


    @Override
    public Pane getView() {
        return m_root;
    }

    public void updateControlPanel(ControlPanelData data) {
        m_controlPanelController.setControlPanelData(data.getSimAngle(), data.getColorScene());
    }

}
