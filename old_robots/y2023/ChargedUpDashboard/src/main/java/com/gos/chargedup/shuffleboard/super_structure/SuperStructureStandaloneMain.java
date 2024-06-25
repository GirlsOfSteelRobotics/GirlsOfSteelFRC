package com.gos.chargedup.shuffleboard.super_structure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity", "PMD.TooManyFields"})
public class SuperStructureStandaloneMain {
    private final SuperStructureWidget m_controller;

    private double m_superStructureArmAngle;
    private double m_superStructureArmGoalAngle;
    private double m_superStructureArmSpeed;
    private boolean m_superStructureIntakeDown;
    private boolean m_superStructureArmExtension1;
    private boolean m_superStructureArmExtension2;
    private double m_superStructureIntakeSpeed;
    private double m_superStructureTurretSpeed;
    private double m_superStructureTurretAngle;
    private double m_superStructureTurretGoalAngle;
    private double m_superStructureRobotAngle;


    private final Label m_superStructureArmAngleLabel = new Label("Q/A -> superStructureArmAngle");
    private final Label m_superStructureArmGoalAngleLabel = new Label("W/S -> superStructureArmGoalAngle");
    private final Label m_superStructureArmSpeedLabel = new Label("E/D -> superStructureArmSpeed");
    private final Label m_superStructureIntakeDownLabel = new Label("DIGIT1 -> superStructureIntakeDown");
    private final Label m_superStructureArmExtension1Label = new Label("DIGIT2 -> superStructureArmExtension1");
    private final Label m_superStructureArmExtension2Label = new Label("DIGIT3 -> superStructureArmExtension2");
    private final Label m_superStructureIntakeSpeedLabel = new Label("R/F -> superStructureIntakeSpeed");
    private final Label m_superStructureTurretSpeedLabel = new Label("T/G -> superStructureTurretSpeed");
    private final Label m_superStructureTurretAngleLabel = new Label("Y/H -> superStructureTurretAngle");
    private final Label m_superStructureTurretGoalAngleLabel = new Label("U/J -> superStructureTurretGoalAngle");
    private final Label m_superStructureRobotAngleLabel = new Label("I/K -> superStructureRobotAngle");

    public SuperStructureStandaloneMain(Scene scene, SuperStructureWidget robotController) {
        m_controller = robotController;

        VBox labelPane = new VBox();
        labelPane.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        labelPane.getChildren().add(m_superStructureArmAngleLabel);
        labelPane.getChildren().add(m_superStructureArmGoalAngleLabel);
        labelPane.getChildren().add(m_superStructureArmSpeedLabel);
        labelPane.getChildren().add(m_superStructureIntakeDownLabel);
        labelPane.getChildren().add(m_superStructureArmExtension1Label);
        labelPane.getChildren().add(m_superStructureArmExtension2Label);
        labelPane.getChildren().add(m_superStructureIntakeSpeedLabel);
        labelPane.getChildren().add(m_superStructureTurretSpeedLabel);
        labelPane.getChildren().add(m_superStructureTurretAngleLabel);
        labelPane.getChildren().add(m_superStructureTurretGoalAngleLabel);
        labelPane.getChildren().add(m_superStructureRobotAngleLabel);
        ((BorderPane) scene.getRoot()).setBottom(labelPane);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // SuperStructure
            case Q:
                m_superStructureArmAngle -= 2;
                m_superStructureArmAngleLabel.setTextFill(Color.GREEN);
                break;
            case A:
                m_superStructureArmAngle += 2;
                m_superStructureArmAngleLabel.setTextFill(Color.GREEN);
                break;
            case W:
                m_superStructureArmGoalAngle -= 2;
                m_superStructureArmGoalAngleLabel.setTextFill(Color.GREEN);
                break;
            case S:
                m_superStructureArmGoalAngle += 2;
                m_superStructureArmGoalAngleLabel.setTextFill(Color.GREEN);
                break;
            case E:
                m_superStructureArmSpeed = 0.25;
                m_superStructureArmSpeedLabel.setTextFill(Color.GREEN);
                break;
            case D:
                m_superStructureArmSpeed = -0.25;
                m_superStructureArmSpeedLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT1:
                m_superStructureIntakeDown = true;
                m_superStructureIntakeDownLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT2:
                m_superStructureArmExtension1 = true;
                m_superStructureArmExtension1Label.setTextFill(Color.GREEN);
                break;

            case DIGIT3:
                m_superStructureArmExtension2 = true;
                m_superStructureArmExtension2Label.setTextFill(Color.GREEN);
                break;
            case R:
                m_superStructureIntakeSpeed = 0.25;
                m_superStructureIntakeSpeedLabel.setTextFill(Color.GREEN);
                break;
            case F:
                m_superStructureIntakeSpeed = -0.25;
                m_superStructureIntakeSpeedLabel.setTextFill(Color.GREEN);
                break;
            case T:
                m_superStructureTurretSpeed = 0.25;
                m_superStructureTurretSpeedLabel.setTextFill(Color.GREEN);
                break;
            case G:
                m_superStructureTurretSpeed = -0.25;
                m_superStructureTurretSpeedLabel.setTextFill(Color.GREEN);
                break;
            case Y:
                m_superStructureTurretAngle -= 2;
                m_superStructureTurretAngleLabel.setTextFill(Color.GREEN);
                break;
            case H:
                m_superStructureTurretAngle += 2;
                m_superStructureTurretAngleLabel.setTextFill(Color.GREEN);
                break;
            case U:
                m_superStructureTurretGoalAngle -= 2;
                m_superStructureTurretGoalAngleLabel.setTextFill(Color.GREEN);
                break;
            case J:
                m_superStructureTurretGoalAngle += 2;
                m_superStructureTurretGoalAngleLabel.setTextFill(Color.GREEN);
                break;
            case I:
                m_superStructureRobotAngle -= 2;
                m_superStructureRobotAngleLabel.setTextFill(Color.GREEN);
                break;
            case K:
                m_superStructureRobotAngle += 2;
                m_superStructureRobotAngleLabel.setTextFill(Color.GREEN);
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
            case Q:
            case A:
                m_superStructureArmAngleLabel.setTextFill(Color.BLACK);
                break;
            case W:
            case S:
                m_superStructureArmGoalAngleLabel.setTextFill(Color.BLACK);
                break;
            case E:
            case D:
                m_superStructureArmSpeed = 0;
                m_superStructureArmSpeedLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT1:
                m_superStructureIntakeDown = false;
                m_superStructureIntakeDownLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT2:
                m_superStructureArmExtension1 = false;
                m_superStructureArmExtension1Label.setTextFill(Color.BLACK);
                break;

            case DIGIT3:
                m_superStructureArmExtension2 = false;
                m_superStructureArmExtension2Label.setTextFill(Color.BLACK);
                break;
            case R:
            case F:
                m_superStructureIntakeSpeed = 0;
                m_superStructureIntakeSpeedLabel.setTextFill(Color.BLACK);
                break;
            case T:
            case G:
                m_superStructureTurretSpeed = 0;
                m_superStructureTurretSpeedLabel.setTextFill(Color.BLACK);
                break;
            case Y:
            case H:
                m_superStructureTurretAngleLabel.setTextFill(Color.BLACK);
                break;
            case U:
            case J:
                m_superStructureTurretGoalAngleLabel.setTextFill(Color.BLACK);
                break;
            case I:
            case K:
                m_superStructureRobotAngleLabel.setTextFill(Color.BLACK);
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
                m_superStructureArmAngle,
                m_superStructureArmGoalAngle,
                m_superStructureArmSpeed,
                m_superStructureIntakeDown,
                m_superStructureArmExtension1,
                m_superStructureArmExtension2,
                m_superStructureIntakeSpeed,
                m_superStructureTurretSpeed,
                m_superStructureTurretAngle,
                m_superStructureTurretGoalAngle,
                m_superStructureRobotAngle
            );

            final SuperStructureData oldData =  m_controller.dataProperty().getValue();
            Map<String, Object> changes = data.changesFrom(oldData);
            System.out.println(changes);

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
