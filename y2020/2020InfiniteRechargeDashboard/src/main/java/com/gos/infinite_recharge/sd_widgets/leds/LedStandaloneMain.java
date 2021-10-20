package com.gos.infinite_recharge.sd_widgets.leds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity"})
public class LedStandaloneMain {

    private static final String RAINBOW_PATTERN = "-128,127,0,0,-128,119,0,0,-128,110,0,0,-128,101,0,0,-128,92,0,0,-128,83,0,0,-128,74,0,0,-128,65,0,0,-128,56,0,0,-128,47,0,0,-128,0,0,0,-128,0,9,0,-128,0,18,0,-128,0,27,0,-128,0,36,0,-128,0,45,0,-128,0,54,0,-128,0,63,0,-128,0,72,0,-128,0,81,0,127,0,-128,0,119,0,-128,0,110,0,-128,0,101,0,-128,0,92,0,-128,0,83,0,-128,0,74,0,-128,0,65,0,-128,0,56,0,-128,0,47,0,-128,0,0,0,-128,0,0,9,-128,0,0,18,-128,0,0,27,-128,0,0,36,-128,0,0,45,-128,0,0,54,-128,0,0,63,-128,0,0,72,-128,0,0,81,-128,0,0,-128,127,0,0,-128,119,0,0,-128,110,0,0,-128,101,0,0,-128,92,0,0,-128,83,0,0,-128,74,0,0,-128,65,0,0,-128,56,0,0,-128,47,0,0,-128,0,0,9,-128,0,0,18,-128,0,0,27,-128,0,0,36,-128,0,0,45,-128,0,0,54,-128,0,0,63,-128,0,0,72,-128,0,0,81,-128,0,0,";
    private static final String RED_PATTERN = "0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,";
    private static final String GREEN_PATTERN = "0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,";
    private static final String BLUE_PATTERN = "-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,-1,0,0,0,";

    private final LedWidget m_controller;

    private String m_ledValues;

    public LedStandaloneMain(Scene scene, LedWidget robotController) {
        m_controller = robotController;
        m_ledValues = "";

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            case A:
                m_ledValues = RAINBOW_PATTERN;
                break;
            case S:
                m_ledValues = RED_PATTERN;
                break;
            case D:
                m_ledValues = GREEN_PATTERN;
                break;
            case F:
                m_ledValues = BLUE_PATTERN;
                break;
            default:
                // ignored
            }
            handleUpdate();
        });
    }

    private void handleUpdate() {

        try {

            LedData data = new LedData(
                m_ledValues
            );
            m_controller.dataProperty().setValue(data);
        } catch (ClassCastException ignored) {
            // don't worry about it
        }
    }

    public static class PseudoMain extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("led_widget.fxml"));
            Pane root = loader.load();
            LedWidget controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            new LedStandaloneMain(scene, controller);
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
