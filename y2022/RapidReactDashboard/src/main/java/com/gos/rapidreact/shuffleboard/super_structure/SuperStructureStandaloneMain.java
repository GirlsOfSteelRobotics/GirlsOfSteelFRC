package com.gos.rapidreact.shuffleboard.super_structure;

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

    private double m_superStructureIntakeAngle;
    private double m_superStructureIntakeSpeed;
    private double m_superStructureRollerSpeed;
    private double m_superStructureHangerSpeed;
    private double m_superStructureHangerHeight;
    private double m_superStructureHorizontalConveyorSpeed;
    private double m_superStructureVerticalConveyorSpeed;
    private double m_superStructureShooterSpeed;

    public SuperStructureStandaloneMain(Scene scene, SuperStructureWidget robotController) {
        m_controller = robotController;

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // SuperStructure
            case Q:
                m_superStructureIntakeAngle -= 2;
                break;
            case A:
                m_superStructureIntakeAngle += 2;
                break;
            case W:
                m_superStructureIntakeSpeed = 0.25;
                break;
            case S:
                m_superStructureIntakeSpeed = -0.25;
                break;
            case E:
                m_superStructureRollerSpeed = 0.25;
                break;
            case D:
                m_superStructureRollerSpeed = -0.25;
                break;
            case R:
                m_superStructureHangerSpeed = 0.25;
                break;
            case F:
                m_superStructureHangerSpeed = -0.25;
                break;
            case T:
                m_superStructureHangerHeight -= 2;
                break;
            case G:
                m_superStructureHangerHeight += 2;
                break;
            case Y:
                m_superStructureHorizontalConveyorSpeed = 0.25;
                break;
            case H:
                m_superStructureHorizontalConveyorSpeed = -0.25;
                break;
            case U:
                m_superStructureVerticalConveyorSpeed = 0.25;
                break;
            case J:
                m_superStructureVerticalConveyorSpeed = -0.25;
                break;
            case I:
                m_superStructureShooterSpeed = 0.25;
                break;
            case K:
                m_superStructureShooterSpeed = -0.25;
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
                m_superStructureIntakeSpeed = 0;
                break;
            case E:
            case D:
                m_superStructureRollerSpeed = 0;
                break;
            case R:
            case F:
                m_superStructureHangerSpeed = 0;
                break;

            case Y:
            case H:
                m_superStructureHorizontalConveyorSpeed = 0;
                break;
            case U:
            case J:
                m_superStructureVerticalConveyorSpeed = 0;
                break;
            case I:
            case K:
                m_superStructureShooterSpeed = 0;
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
                m_superStructureIntakeAngle,
                m_superStructureIntakeSpeed,
                m_superStructureRollerSpeed,
                m_superStructureHangerSpeed,
                m_superStructureHangerHeight,
                m_superStructureHorizontalConveyorSpeed,
                m_superStructureVerticalConveyorSpeed,
                m_superStructureShooterSpeed
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
