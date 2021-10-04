package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.CodelabSuperStructureData;
import com.gos.codelabs.shuffleboard.sd_widgets.SmartDashboardNames;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.ElevatorData;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.PunchData;
import com.gos.codelabs.shuffleboard.sd_widgets.ss.data.SpinningWheelData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity"})
public class CodelabSuperStructureStandaloneMain {
    private final CodelabSuperStructureWidget m_controller;

    private double m_elevatorSpeed;
    private double m_elevatorHeight;
    private boolean m_elevatorAtLowerLimit;
    private boolean m_elevatorAtUpperLimit;
    private boolean m_punchPunchExtended;
    private double m_spinningWheelMotorSpeed;

    public CodelabSuperStructureStandaloneMain(Scene scene, CodelabSuperStructureWidget robotController) {
        m_controller = robotController;

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // Elevator
            case Q:
                m_elevatorSpeed = 0.75;
                break;
            case A:
                m_elevatorSpeed = -0.75;
                break;
            case S:
                m_elevatorHeight -= 1;
                break;
            case W:
                m_elevatorHeight += 1;
                break;

            case X:
                m_elevatorAtLowerLimit = true;
                break;

            case Y:
                m_elevatorAtUpperLimit = true;
                break;

            // Punch

            case D:
                m_punchPunchExtended = true;
                break;

            // SpinningWheel
            case O:
                m_spinningWheelMotorSpeed = 0.5;
                break;
            case L:
                m_spinningWheelMotorSpeed = -0.5;
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
                break;


            case X:
                m_elevatorAtLowerLimit = false;
                break;

            case Y:
                m_elevatorAtUpperLimit = false;
                break;

            // Punch

            case D:
                m_punchPunchExtended = false;
                break;

            // SpinningWheel
            case O:
            case L:
                m_spinningWheelMotorSpeed = 0;
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
                m_elevatorAtLowerLimit,
                m_elevatorAtUpperLimit
                ).asMap(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/"));

            map.putAll(new PunchData(
                m_punchPunchExtended
                ).asMap(SmartDashboardNames.PUNCH_TABLE_NAME + "/"));

            map.putAll(new SpinningWheelData(
                m_spinningWheelMotorSpeed
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
