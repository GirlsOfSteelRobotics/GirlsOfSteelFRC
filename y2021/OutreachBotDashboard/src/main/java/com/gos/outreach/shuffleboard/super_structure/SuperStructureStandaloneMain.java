package com.gos.outreach.shuffleboard.super_structure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity"})
public class SuperStructureStandaloneMain {
    private final SuperStructureWidget m_controller;

    private double m_superStructureHoodAngle;
    private double m_superStructureHoodMotorSpeed;
    private boolean m_superStructureCollectorIn;
    private double m_superStructureCollectorSpeed;
    private double m_superStructureShooterMotorSpeed;

    public SuperStructureStandaloneMain(Scene scene, SuperStructureWidget robotController) {
        m_controller = robotController;

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // SuperStructure
            case Q:
                m_superStructureHoodAngle -= 2;
                break;
            case A:
                m_superStructureHoodAngle += 2;
                break;
            case W:
                m_superStructureHoodMotorSpeed = 0.25;
                break;
            case S:
                m_superStructureHoodMotorSpeed = -0.25;
                break;

            case DIGIT0:
                m_superStructureCollectorIn = true;
                break;
            case E:
                m_superStructureCollectorSpeed = 0.25;
                break;
            case D:
                m_superStructureCollectorSpeed = -0.25;
                break;
            case R:
                m_superStructureShooterMotorSpeed = 0.25;
                break;
            case F:
                m_superStructureShooterMotorSpeed = -0.25;
                break;

            default:
                // ignored
            }
            handleUpdate();
        });


        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // SuperStructure

            case W:
            case S:
                m_superStructureHoodMotorSpeed = 0;
                break;

            case DIGIT0:
                m_superStructureCollectorIn = false;
                break;
            case E:
            case D:
                m_superStructureCollectorSpeed = 0;
                break;
            case R:
            case F:
                m_superStructureShooterMotorSpeed = 0;
                break;
            default:
                break;
            }
            handleUpdate();
        });
    }

    private void handleUpdate() {

        try {

            SuperStructureData data = new SuperStructureData(
                m_superStructureHoodAngle,
                m_superStructureHoodMotorSpeed,
                m_superStructureCollectorIn,
                m_superStructureCollectorSpeed,
                m_superStructureShooterMotorSpeed
            );
            m_controller.dataProperty().setValue(data);
        } catch (ClassCastException ignored) {
            // don't worry about it
        }
    }

    public static class PseudoMain extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("super_structure_widget.fxml"));
            Pane root = loader.load();
            SuperStructureWidget controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            new SuperStructureStandaloneMain(scene, controller);
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
