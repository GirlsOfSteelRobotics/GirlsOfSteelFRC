package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.ElevatorData;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.PunchData;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.SpinningWheelData;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.CodelabSuperStructureData;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.Map;

@Description(name = "Codelab Super Structure", dataTypes = {CodelabSuperStructureData.class})
@ParametrizedController("codelab_super_structure_widget.fxml")
public class CodelabSuperStructureWidget extends SimpleAnnotatedWidget<CodelabSuperStructureData> {
    @FXML
    private Pane m_root;

    @FXML
    protected CodelabSuperStructureController m_widgetController;

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

            if (ElevatorData.hasChanged(changes)) {
                m_widgetController.updateElevator(newData.getElevator());
            }

            if (PunchData.hasChanged(changes)) {
                m_widgetController.updatePunch(newData.getPunch());
            }

            if (SpinningWheelData.hasChanged(changes)) {
                m_widgetController.updateSpinningWheel(newData.getSpinningWheel());
            }

        });
    }

}
