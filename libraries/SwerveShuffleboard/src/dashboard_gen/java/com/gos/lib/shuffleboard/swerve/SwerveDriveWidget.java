package com.gos.lib.shuffleboard.swerve;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


import java.util.Map;


@Description(name = SmartDashboardNames.WIDGET_NAME, dataTypes = {SwerveDriveData.class})
@ParametrizedController("swerve_drive_widget.fxml")
public class SwerveDriveWidget extends SimpleAnnotatedWidget<SwerveDriveData> {
    @FXML
    private Pane m_root;

    @FXML
    protected SwerveDriveController m_widgetController;

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

            if (SwerveModuleData.hasChanged(SmartDashboardNames.LEFT_FRONT_SWERVE_DRIVE + "/", changes)) {
                m_widgetController.updateLeftFront(newData.getLeftFront());
            }

            if (SwerveModuleData.hasChanged(SmartDashboardNames.LEFT_REAR_SWERVE_DRIVE + "/", changes)) {
                m_widgetController.updateLeftRear(newData.getLeftRear());
            }

            if (SwerveModuleData.hasChanged(SmartDashboardNames.RIGHT_FRONT_SWERVE_DRIVE + "/", changes)) {
                m_widgetController.updateRightFront(newData.getRightFront());
            }

            if (SwerveModuleData.hasChanged(SmartDashboardNames.RIGHT_REAR_SWERVE_DRIVE + "/", changes)) {
                m_widgetController.updateRightRear(newData.getRightRear());
            }


        });
    }

}
