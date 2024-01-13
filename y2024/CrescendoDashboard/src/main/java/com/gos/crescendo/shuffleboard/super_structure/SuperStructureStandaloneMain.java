package com.gos.crescendo.shuffleboard.super_structure;

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

    private double m_superStructurePivotMotorAngle;
    private double m_superStructureGoalAngle;
    private double m_superStructureShooterMotorPercentage;
    private double m_superStructurePivotMotorPercentage;
    private boolean m_superStructureHasGamePiece;
    private double m_superStructureIntakeMotorPercentage;


    private final Label m_superStructurePivotMotorAngleLabel = new Label("Q/A -> superStructurePivotMotorAngle");
    private final Label m_superStructureGoalAngleLabel = new Label("W/S -> superStructureGoalAngle");
    private final Label m_superStructureShooterMotorPercentageLabel = new Label("E/D -> superStructureShooterMotorPercentage");
    private final Label m_superStructurePivotMotorPercentageLabel = new Label("R/F -> superStructurePivotMotorPercentage");
    private final Label m_superStructureHasGamePieceLabel = new Label("DIGIT1 -> superStructureHasGamePiece");
    private final Label m_superStructureIntakeMotorPercentageLabel = new Label("T/G -> superStructureIntakeMotorPercentage");

    public SuperStructureStandaloneMain(Scene scene, SuperStructureWidget robotController) {
        m_controller = robotController;

        VBox labelPane = new VBox();
        labelPane.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        labelPane.getChildren().add(m_superStructurePivotMotorAngleLabel);
        labelPane.getChildren().add(m_superStructureGoalAngleLabel);
        labelPane.getChildren().add(m_superStructureShooterMotorPercentageLabel);
        labelPane.getChildren().add(m_superStructurePivotMotorPercentageLabel);
        labelPane.getChildren().add(m_superStructureHasGamePieceLabel);
        labelPane.getChildren().add(m_superStructureIntakeMotorPercentageLabel);
        ((BorderPane) scene.getRoot()).setBottom(labelPane);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // SuperStructure
            case Q:
                m_superStructurePivotMotorAngle -= 2;
                m_superStructurePivotMotorAngleLabel.setTextFill(Color.GREEN);
                break;
            case A:
                m_superStructurePivotMotorAngle += 2;
                m_superStructurePivotMotorAngleLabel.setTextFill(Color.GREEN);
                break;
            case W:
                m_superStructureGoalAngle -= 2;
                m_superStructureGoalAngleLabel.setTextFill(Color.GREEN);
                break;
            case S:
                m_superStructureGoalAngle += 2;
                m_superStructureGoalAngleLabel.setTextFill(Color.GREEN);
                break;
            case E:
                m_superStructureShooterMotorPercentage = 0.25;
                m_superStructureShooterMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case D:
                m_superStructureShooterMotorPercentage = -0.25;
                m_superStructureShooterMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case R:
                m_superStructurePivotMotorPercentage = 0.25;
                m_superStructurePivotMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case F:
                m_superStructurePivotMotorPercentage = -0.25;
                m_superStructurePivotMotorPercentageLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT1:
                m_superStructureHasGamePiece = true;
                m_superStructureHasGamePieceLabel.setTextFill(Color.GREEN);
                break;
            case T:
                m_superStructureIntakeMotorPercentage = 0.25;
                m_superStructureIntakeMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case G:
                m_superStructureIntakeMotorPercentage = -0.25;
                m_superStructureIntakeMotorPercentageLabel.setTextFill(Color.GREEN);
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
                m_superStructurePivotMotorAngleLabel.setTextFill(Color.BLACK);
                break;
            case W:
            case S:
                m_superStructureGoalAngleLabel.setTextFill(Color.BLACK);
                break;
            case E:
            case D:
                m_superStructureShooterMotorPercentage = 0;
                m_superStructureShooterMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            case R:
            case F:
                m_superStructurePivotMotorPercentage = 0;
                m_superStructurePivotMotorPercentageLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT1:
                m_superStructureHasGamePiece = false;
                m_superStructureHasGamePieceLabel.setTextFill(Color.BLACK);
                break;
            case T:
            case G:
                m_superStructureIntakeMotorPercentage = 0;
                m_superStructureIntakeMotorPercentageLabel.setTextFill(Color.BLACK);
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
                m_superStructurePivotMotorAngle,
                m_superStructureGoalAngle,
                m_superStructureShooterMotorPercentage,
                m_superStructurePivotMotorPercentage,
                m_superStructureHasGamePiece,
                m_superStructureIntakeMotorPercentage
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
