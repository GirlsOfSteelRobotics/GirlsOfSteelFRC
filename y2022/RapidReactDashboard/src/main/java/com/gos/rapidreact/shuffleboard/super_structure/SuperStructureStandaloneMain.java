package com.gos.rapidreact.shuffleboard.super_structure;

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

    private double m_superStructureIntakeAngle;
    private double m_superStructureIntakeSpeed;
    private double m_superStructureRollerSpeed;
    private double m_superStructureHangerSpeed;
    private double m_superStructureHangerHeight;
    private double m_superStructureHorizontalConveyorSpeed;
    private double m_superStructureVerticalConveyorSpeed;
    private double m_superStructureShooterSpeed;
    private boolean m_superStructureIntakeIndexingSensor;
    private boolean m_superStructureLowerVerticalConveyorIndexingSensor;
    private boolean m_superStructureUpperVerticalConveyorIndexingSensor;


    private final Label m_superStructureIntakeAngleLabel = new Label("Q/A -> superStructureIntakeAngle");
    private final Label m_superStructureIntakeSpeedLabel = new Label("W/S -> superStructureIntakeSpeed");
    private final Label m_superStructureRollerSpeedLabel = new Label("E/D -> superStructureRollerSpeed");
    private final Label m_superStructureHangerSpeedLabel = new Label("R/F -> superStructureHangerSpeed");
    private final Label m_superStructureHangerHeightLabel = new Label("T/G -> superStructureHangerHeight");
    private final Label m_superStructureHorizontalConveyorSpeedLabel = new Label("Y/H -> superStructureHorizontalConveyorSpeed");
    private final Label m_superStructureVerticalConveyorSpeedLabel = new Label("U/J -> superStructureVerticalConveyorSpeed");
    private final Label m_superStructureShooterSpeedLabel = new Label("I/K -> superStructureShooterSpeed");
    private final Label m_superStructureIntakeIndexingSensorLabel = new Label("DIGIT0 -> superStructureIntakeIndexingSensor");
    private final Label m_superStructureLowerVerticalConveyorIndexingSensorLabel = new Label("DIGIT1 -> superStructureLowerVerticalConveyorIndexingSensor");
    private final Label m_superStructureUpperVerticalConveyorIndexingSensorLabel = new Label("DIGIT2 -> superStructureUpperVerticalConveyorIndexingSensor");

    public SuperStructureStandaloneMain(Scene scene, SuperStructureWidget robotController) {
        m_controller = robotController;

        VBox labelPane = new VBox();
        labelPane.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        labelPane.getChildren().add(m_superStructureIntakeAngleLabel);
        labelPane.getChildren().add(m_superStructureIntakeSpeedLabel);
        labelPane.getChildren().add(m_superStructureRollerSpeedLabel);
        labelPane.getChildren().add(m_superStructureHangerSpeedLabel);
        labelPane.getChildren().add(m_superStructureHangerHeightLabel);
        labelPane.getChildren().add(m_superStructureHorizontalConveyorSpeedLabel);
        labelPane.getChildren().add(m_superStructureVerticalConveyorSpeedLabel);
        labelPane.getChildren().add(m_superStructureShooterSpeedLabel);
        labelPane.getChildren().add(m_superStructureIntakeIndexingSensorLabel);
        labelPane.getChildren().add(m_superStructureLowerVerticalConveyorIndexingSensorLabel);
        labelPane.getChildren().add(m_superStructureUpperVerticalConveyorIndexingSensorLabel);
        ((BorderPane) scene.getRoot()).setBottom(labelPane);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // SuperStructure
            case Q:
                m_superStructureIntakeAngle -= 2;
                m_superStructureIntakeAngleLabel.setTextFill(Color.GREEN);
                break;
            case A:
                m_superStructureIntakeAngle += 2;
                m_superStructureIntakeAngleLabel.setTextFill(Color.GREEN);
                break;
            case W:
                m_superStructureIntakeSpeed = 0.25;
                m_superStructureIntakeSpeedLabel.setTextFill(Color.GREEN);
                break;
            case S:
                m_superStructureIntakeSpeed = -0.25;
                m_superStructureIntakeSpeedLabel.setTextFill(Color.GREEN);
                break;
            case E:
                m_superStructureRollerSpeed = 0.25;
                m_superStructureRollerSpeedLabel.setTextFill(Color.GREEN);
                break;
            case D:
                m_superStructureRollerSpeed = -0.25;
                m_superStructureRollerSpeedLabel.setTextFill(Color.GREEN);
                break;
            case R:
                m_superStructureHangerSpeed = 0.25;
                m_superStructureHangerSpeedLabel.setTextFill(Color.GREEN);
                break;
            case F:
                m_superStructureHangerSpeed = -0.25;
                m_superStructureHangerSpeedLabel.setTextFill(Color.GREEN);
                break;
            case T:
                m_superStructureHangerHeight -= 2;
                m_superStructureHangerHeightLabel.setTextFill(Color.GREEN);
                break;
            case G:
                m_superStructureHangerHeight += 2;
                m_superStructureHangerHeightLabel.setTextFill(Color.GREEN);
                break;
            case Y:
                m_superStructureHorizontalConveyorSpeed = 0.25;
                m_superStructureHorizontalConveyorSpeedLabel.setTextFill(Color.GREEN);
                break;
            case H:
                m_superStructureHorizontalConveyorSpeed = -0.25;
                m_superStructureHorizontalConveyorSpeedLabel.setTextFill(Color.GREEN);
                break;
            case U:
                m_superStructureVerticalConveyorSpeed = 0.25;
                m_superStructureVerticalConveyorSpeedLabel.setTextFill(Color.GREEN);
                break;
            case J:
                m_superStructureVerticalConveyorSpeed = -0.25;
                m_superStructureVerticalConveyorSpeedLabel.setTextFill(Color.GREEN);
                break;
            case I:
                m_superStructureShooterSpeed = 0.25;
                m_superStructureShooterSpeedLabel.setTextFill(Color.GREEN);
                break;
            case K:
                m_superStructureShooterSpeed = -0.25;
                m_superStructureShooterSpeedLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT0:
                m_superStructureIntakeIndexingSensor = true;
                m_superStructureIntakeIndexingSensorLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT1:
                m_superStructureLowerVerticalConveyorIndexingSensor = true;
                m_superStructureLowerVerticalConveyorIndexingSensorLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT2:
                m_superStructureUpperVerticalConveyorIndexingSensor = true;
                m_superStructureUpperVerticalConveyorIndexingSensorLabel.setTextFill(Color.GREEN);
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
                m_superStructureIntakeAngleLabel.setTextFill(Color.BLACK);
                break;
            case W:
            case S:
                m_superStructureIntakeSpeed = 0;
                m_superStructureIntakeSpeedLabel.setTextFill(Color.BLACK);
                break;
            case E:
            case D:
                m_superStructureRollerSpeed = 0;
                m_superStructureRollerSpeedLabel.setTextFill(Color.BLACK);
                break;
            case R:
            case F:
                m_superStructureHangerSpeed = 0;
                m_superStructureHangerSpeedLabel.setTextFill(Color.BLACK);
                break;
            case T:
            case G:
                m_superStructureHangerHeightLabel.setTextFill(Color.BLACK);
                break;
            case Y:
            case H:
                m_superStructureHorizontalConveyorSpeed = 0;
                m_superStructureHorizontalConveyorSpeedLabel.setTextFill(Color.BLACK);
                break;
            case U:
            case J:
                m_superStructureVerticalConveyorSpeed = 0;
                m_superStructureVerticalConveyorSpeedLabel.setTextFill(Color.BLACK);
                break;
            case I:
            case K:
                m_superStructureShooterSpeed = 0;
                m_superStructureShooterSpeedLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT0:
                m_superStructureIntakeIndexingSensor = false;
                m_superStructureIntakeIndexingSensorLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT1:
                m_superStructureLowerVerticalConveyorIndexingSensor = false;
                m_superStructureLowerVerticalConveyorIndexingSensorLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT2:
                m_superStructureUpperVerticalConveyorIndexingSensor = false;
                m_superStructureUpperVerticalConveyorIndexingSensorLabel.setTextFill(Color.BLACK);
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
                m_superStructureShooterSpeed,
                m_superStructureIntakeIndexingSensor,
                m_superStructureLowerVerticalConveyorIndexingSensor,
                m_superStructureUpperVerticalConveyorIndexingSensor
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
