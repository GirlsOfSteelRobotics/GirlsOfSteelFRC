package com.gos.infinite_recharge.sd_widgets.super_structure;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ControlPanelData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.LiftData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ShooterConveyorData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ShooterIntakeData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ShooterWheelsData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.SuperStructureData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.WinchData;
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
public class SuperStructureStandaloneMain {
    private final SuperStructureWidget m_controller;

    private double m_controlPanelSpeed;
    private double m_liftSpeed;
    private double m_shooterConveyorSpeed;
    private boolean m_shooterConveyorHandoffBallSensor;
    private boolean m_shooterConveyorSecondaryBallSensor;
    private boolean m_shooterConveyorTopBallSensor;
    private double m_shooterIntakeSpeed;
    private boolean m_shooterIntakeIsUp;
    private double m_shooterWheelCurrentRpm;
    private double m_shooterWheelGoalRpm;
    private double m_shooterWheelSpeed;
    private double m_winchSpeed;

    public SuperStructureStandaloneMain(Scene scene, SuperStructureWidget robotController) {
        m_controller = robotController;

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // Control Panel
            case A:
                m_controlPanelSpeed = .75;
                break;
            case Q:
                m_controlPanelSpeed = -.75;
                break;

            // Lift
            case W:
                m_liftSpeed = .5;
                break;
            case S:
                m_liftSpeed = -.5;
                break;

            // Shooter Conveyor
            case E:
                m_shooterConveyorSpeed = .5;
                break;
            case D:
                m_shooterConveyorSpeed = -.5;
                break;

            case Z:
                m_shooterConveyorHandoffBallSensor = true;
                break;
            case X:
                m_shooterConveyorSecondaryBallSensor = true;
                break;
            case C:
                m_shooterConveyorTopBallSensor = true;
                break;

            // Shooter Intake
            case R:
                m_shooterIntakeSpeed = .5;
                break;
            case F:
                m_shooterIntakeSpeed = -.5;
                break;
            case Y:
                m_shooterIntakeIsUp = true;
                break;

            // Shooter Wheels
            case U:
                m_shooterWheelCurrentRpm -= 10;
                break;
            case J:
                m_shooterWheelCurrentRpm += 10;
                break;
            case I:
                m_shooterWheelGoalRpm -= 10;
                break;
            case K:
                m_shooterWheelGoalRpm += 10;
                break;
            case O:
                m_shooterWheelSpeed = 1;
                break;
            case L:
                m_shooterWheelSpeed = -1;
                break;

            // Winch
            case T:
                m_winchSpeed = .25;
                break;
            case G:
                m_winchSpeed = -.25;
                break;

            default:
                // ignored
            }
            handleUpdate();
        });


        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            switch (code) {
            case A:
            case Q:
                m_controlPanelSpeed = 0;
                break;
            case W:
            case S:
                m_liftSpeed = 0;
                break;
            case E:
            case D:
                m_shooterConveyorSpeed = 0;
                break;
            case R:
            case F:
                m_shooterIntakeSpeed = 0;
                break;
            case O:
            case L:
                m_shooterWheelSpeed = 0;
                break;
            case T:
            case G:
                m_winchSpeed = 0;
                break;
            case Y:
                m_shooterIntakeIsUp = false;
                break;
            case Z:
                m_shooterConveyorHandoffBallSensor = false;
                break;
            case X:
                m_shooterConveyorSecondaryBallSensor = false;
                break;
            case C:
                m_shooterConveyorTopBallSensor = false;
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
            map.putAll(new ControlPanelData(m_controlPanelSpeed).asMap(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME + "/"));
            map.putAll(new LiftData(m_liftSpeed).asMap(SmartDashboardNames.LIFT_TABLE_NAME + "/"));
            map.putAll(new ShooterConveyorData(m_shooterConveyorSpeed, m_shooterConveyorHandoffBallSensor, m_shooterConveyorSecondaryBallSensor, m_shooterConveyorTopBallSensor).asMap(SmartDashboardNames.SHOOTER_CONVEYOR_TABLE_NAME + "/"));
            map.putAll(new ShooterIntakeData(m_shooterIntakeSpeed, m_shooterIntakeIsUp).asMap(SmartDashboardNames.SHOOTER_INTAKE_TABLE_NAME + "/"));
            map.putAll(new ShooterWheelsData(m_shooterWheelSpeed, m_shooterWheelCurrentRpm, m_shooterWheelGoalRpm).asMap(SmartDashboardNames.SHOOTER_WHEELS_TABLE_NAME + "/"));
            map.putAll(new WinchData(m_winchSpeed).asMap(SmartDashboardNames.WINCH_TABLE_NAME + "/"));

            m_controller.dataProperty().setValue(new SuperStructureData(map));
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
