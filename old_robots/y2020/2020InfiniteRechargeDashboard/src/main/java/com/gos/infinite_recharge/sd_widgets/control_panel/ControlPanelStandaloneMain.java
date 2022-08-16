package com.gos.infinite_recharge.sd_widgets.control_panel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity"})
public class ControlPanelStandaloneMain {
    private final ControlPanelWidget m_controller;

    private double m_controlPanelSimAngle;

    public ControlPanelStandaloneMain(Scene scene, ControlPanelWidget robotController) {
        m_controller = robotController;

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // ControlPanel
            case A:
                m_controlPanelSimAngle -= 1;
                break;
            case Q:
                m_controlPanelSimAngle += 1;
                break;






            default:
                // ignored
            }
            handleUpdate();
        });
    }

    private void handleUpdate() {

        try {

            ControlPanelData data = new ControlPanelData(
                m_controlPanelSimAngle,
                0.0,
                0.0,
                0.0,
                "yellow"
            );
            m_controller.dataProperty().setValue(data);
        } catch (ClassCastException ignored) {
            // don't worry about it
        }
    }

    public static class PseudoMain extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("control_panel_widget.fxml"));
            Pane root = loader.load();
            ControlPanelWidget controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            new ControlPanelStandaloneMain(scene, controller);
            primaryStage.show();
        }
    }

    @SuppressWarnings("JavadocMethod")
    public static void main(String[] args) {
        // JavaFX 11+ uses GTK3 by default, and has problems on some display
        // servers
        // This flag forces JavaFX to use GTK2
        // System.setProperty("jdk.gtk.version", "2");
        Application.launch(PseudoMain.class, args);
    }
}
