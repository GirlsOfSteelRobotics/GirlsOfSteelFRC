package com.gos.codelabs.shuffleboard.sd_widgets.ss;

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
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity", "PMD.TooManyFields"})
public class CodelabSuperStructureStandaloneMain {
    private final CodelabSuperStructureWidget m_controller;

    private double m_elevatorSpeed;
    private double m_elevatorHeight;
    private boolean m_elevatorAtUpperLimit;
    private boolean m_elevatorAtLowerLimit;
    private boolean m_punchPunchExtended;
    private double m_spinningWheelSpeed;


    private final Label m_elevatorSpeedLabel = new Label("Q/A -> elevatorSpeed");
    private final Label m_elevatorHeightLabel = new Label("W/S -> elevatorHeight");
    private final Label m_elevatorAtUpperLimitLabel = new Label("DIGIT1 -> elevatorAtUpperLimit");
    private final Label m_elevatorAtLowerLimitLabel = new Label("DIGIT2 -> elevatorAtLowerLimit");
    private final Label m_punchPunchExtendedLabel = new Label("DIGIT3 -> punchPunchExtended");
    private final Label m_spinningWheelSpeedLabel = new Label("E/D -> spinningWheelSpeed");

    public CodelabSuperStructureStandaloneMain(Scene scene, CodelabSuperStructureWidget robotController) {
        m_controller = robotController;

        VBox labelPane = new VBox();
        labelPane.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        labelPane.getChildren().add(m_elevatorSpeedLabel);
        labelPane.getChildren().add(m_elevatorHeightLabel);
        labelPane.getChildren().add(m_elevatorAtUpperLimitLabel);
        labelPane.getChildren().add(m_elevatorAtLowerLimitLabel);
        labelPane.getChildren().add(m_punchPunchExtendedLabel);
        labelPane.getChildren().add(m_spinningWheelSpeedLabel);
        ((BorderPane) scene.getRoot()).setBottom(labelPane);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // Elevator
            case Q:
                m_elevatorSpeed = 0.75;
                m_elevatorSpeedLabel.setTextFill(Color.GREEN);
                break;
            case A:
                m_elevatorSpeed = -0.75;
                m_elevatorSpeedLabel.setTextFill(Color.GREEN);
                break;
            case W:
                m_elevatorHeight -= 1;
                m_elevatorHeightLabel.setTextFill(Color.GREEN);
                break;
            case S:
                m_elevatorHeight += 1;
                m_elevatorHeightLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT1:
                m_elevatorAtUpperLimit = true;
                m_elevatorAtUpperLimitLabel.setTextFill(Color.GREEN);
                break;

            case DIGIT2:
                m_elevatorAtLowerLimit = true;
                m_elevatorAtLowerLimitLabel.setTextFill(Color.GREEN);
                break;
            // Punch

            case DIGIT3:
                m_punchPunchExtended = true;
                m_punchPunchExtendedLabel.setTextFill(Color.GREEN);
                break;
            // SpinningWheel
            case E:
                m_spinningWheelSpeed = 0.5;
                m_spinningWheelSpeedLabel.setTextFill(Color.GREEN);
                break;
            case D:
                m_spinningWheelSpeed = -0.5;
                m_spinningWheelSpeedLabel.setTextFill(Color.GREEN);
                break;

            default:
                // ignored
            }
            handleUpdate();
        });


        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // Elevator
            case Q:
            case A:
                m_elevatorSpeed = 0;
                m_elevatorSpeedLabel.setTextFill(Color.BLACK);
                break;
            case W:
            case S:
                m_elevatorHeightLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT1:
                m_elevatorAtUpperLimit = false;
                m_elevatorAtUpperLimitLabel.setTextFill(Color.BLACK);
                break;

            case DIGIT2:
                m_elevatorAtLowerLimit = false;
                m_elevatorAtLowerLimitLabel.setTextFill(Color.BLACK);
                break;
            // Punch

            case DIGIT3:
                m_punchPunchExtended = false;
                m_punchPunchExtendedLabel.setTextFill(Color.BLACK);
                break;
            // SpinningWheel
            case E:
            case D:
                m_spinningWheelSpeed = 0;
                m_spinningWheelSpeedLabel.setTextFill(Color.BLACK);
                break;
            default:
                break;
            }
            handleUpdate();
        });
    }

    private void handleUpdate() {

        try {

            Map<String, Object> map = new HashMap<>();

            map.putAll(new ElevatorData(
                m_elevatorSpeed,
                m_elevatorHeight,
                m_elevatorAtUpperLimit,
                m_elevatorAtLowerLimit
                ).asMap(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/"));

            map.putAll(new PunchData(
                m_punchPunchExtended
                ).asMap(SmartDashboardNames.PUNCH_TABLE_NAME + "/"));

            map.putAll(new SpinningWheelData(
                m_spinningWheelSpeed
                ).asMap(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/"));

            m_controller.dataProperty().setValue(new CodelabSuperStructureData(map));

        } catch (ClassCastException ignored) {
            // don't worry about it
        }
    }

    public static class PseudoMain extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("codelab_super_structure_widget.fxml"));
            Pane root = loader.load();
            CodelabSuperStructureWidget controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            new CodelabSuperStructureStandaloneMain(scene, controller);
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
