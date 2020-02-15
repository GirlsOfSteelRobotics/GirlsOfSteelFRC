package com.gos.infinite_recharge.sd_widgets.control_panel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlPanelStandaloneMain {
    private final ControlPanelController m_controller;
    private double m_wheelAngle;

    public ControlPanelStandaloneMain(Scene scene, ControlPanelController robotController) {
        m_controller = robotController;
        m_controller.setColorConsumer((ControlPanelController.Colors color) -> {
        });
        m_wheelAngle = 0;
        handleUpdate();

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
            case A:
                m_wheelAngle -= 1;
                break;
            case D:
                m_wheelAngle += 1;
                break;

            default:
                // ignored
            }
            handleUpdate();
        });

        scene.setOnKeyReleased(event -> handleUpdate());
    }

    private void handleUpdate() {
        m_controller.setControlPanelData(m_wheelAngle, "");
    }


    public static class PseudoMain extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("control_panel.fxml"));
            Pane root = loader.load();
            ControlPanelController controller = loader.getController();

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
