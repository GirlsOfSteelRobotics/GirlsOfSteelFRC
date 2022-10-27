package com.gos.lib.shuffleboard.swerve;

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
public class SwerveDriveStandaloneMain {
    private final SwerveDriveWidget m_controller;

    private double m_leftFrontCurrentAngle;
    private double m_leftFrontDesiredAngle;
    private double m_leftFrontDriveMotorPercentage;
    private double m_leftFrontTurnMotorPercentage;
    private double m_leftRearCurrentAngle;
    private double m_leftRearDesiredAngle;
    private double m_leftRearDriveMotorPercentage;
    private double m_leftRearTurnMotorPercentage;
    private double m_rightFrontCurrentAngle;
    private double m_rightFrontDesiredAngle;
    private double m_rightFrontDriveMotorPercentage;
    private double m_rightFrontTurnMotorPercentage;
    private double m_rightRearCurrentAngle;
    private double m_rightRearDesiredAngle;
    private double m_rightRearDriveMotorPercentage;
    private double m_rightRearTurnMotorPercentage;


    private final Label m_leftFrontCurrentAngleLabel = new Label("Q/A -> leftFrontCurrentAngle");
    private final Label m_leftFrontDesiredAngleLabel = new Label("W/S -> leftFrontDesiredAngle");
    private final Label m_leftFrontDriveMotorPercentageLabel = new Label("E/D -> leftFrontDriveMotorPercentage");
    private final Label m_leftFrontTurnMotorPercentageLabel = new Label("R/F -> leftFrontTurnMotorPercentage");
    private final Label m_leftRearCurrentAngleLabel = new Label("T/G -> leftRearCurrentAngle");
    private final Label m_leftRearDesiredAngleLabel = new Label("Y/H -> leftRearDesiredAngle");
    private final Label m_leftRearDriveMotorPercentageLabel = new Label("U/J -> leftRearDriveMotorPercentage");
    private final Label m_leftRearTurnMotorPercentageLabel = new Label("I/K -> leftRearTurnMotorPercentage");
    private final Label m_rightFrontCurrentAngleLabel = new Label("O/L -> rightFrontCurrentAngle");
    private final Label m_rightFrontDesiredAngleLabel = new Label("P/SEMICOLON -> rightFrontDesiredAngle");
    private final Label m_rightFrontDriveMotorPercentageLabel = new Label("BRACELEFT/QUOTE -> rightFrontDriveMotorPercentage");
    private final Label m_rightFrontTurnMotorPercentageLabel = new Label("Z/X -> rightFrontTurnMotorPercentage");
    private final Label m_rightRearCurrentAngleLabel = new Label("C/V -> rightRearCurrentAngle");
    private final Label m_rightRearDesiredAngleLabel = new Label("B/N -> rightRearDesiredAngle");
    private final Label m_rightRearDriveMotorPercentageLabel = new Label("M/COMMA -> rightRearDriveMotorPercentage");
    private final Label m_rightRearTurnMotorPercentageLabel = new Label("PERIOD/SLASH -> rightRearTurnMotorPercentage");

    public SwerveDriveStandaloneMain(Scene scene, SwerveDriveWidget robotController) {
        m_controller = robotController;

        VBox labelPane = new VBox();
        labelPane.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        labelPane.getChildren().add(m_leftFrontCurrentAngleLabel);
        labelPane.getChildren().add(m_leftFrontDesiredAngleLabel);
        labelPane.getChildren().add(m_leftFrontDriveMotorPercentageLabel);
        labelPane.getChildren().add(m_leftFrontTurnMotorPercentageLabel);
        labelPane.getChildren().add(m_leftRearCurrentAngleLabel);
        labelPane.getChildren().add(m_leftRearDesiredAngleLabel);
        labelPane.getChildren().add(m_leftRearDriveMotorPercentageLabel);
        labelPane.getChildren().add(m_leftRearTurnMotorPercentageLabel);
        labelPane.getChildren().add(m_rightFrontCurrentAngleLabel);
        labelPane.getChildren().add(m_rightFrontDesiredAngleLabel);
        labelPane.getChildren().add(m_rightFrontDriveMotorPercentageLabel);
        labelPane.getChildren().add(m_rightFrontTurnMotorPercentageLabel);
        labelPane.getChildren().add(m_rightRearCurrentAngleLabel);
        labelPane.getChildren().add(m_rightRearDesiredAngleLabel);
        labelPane.getChildren().add(m_rightRearDriveMotorPercentageLabel);
        labelPane.getChildren().add(m_rightRearTurnMotorPercentageLabel);
        ((BorderPane) scene.getRoot()).setBottom(labelPane);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // LeftFront
            case Q:
                m_leftFrontCurrentAngle -= 2;
                m_leftFrontCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case A:
                m_leftFrontCurrentAngle += 2;
                m_leftFrontCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case W:
                m_leftFrontDesiredAngle -= 2;
                m_leftFrontDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case S:
                m_leftFrontDesiredAngle += 2;
                m_leftFrontDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case E:
                m_leftFrontDriveMotorPercentage -= 0.05;
                m_leftFrontDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case D:
                m_leftFrontDriveMotorPercentage += 0.05;
                m_leftFrontDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case R:
                m_leftFrontTurnMotorPercentage -= 0.05;
                m_leftFrontTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case F:
                m_leftFrontTurnMotorPercentage += 0.05;
                m_leftFrontTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            // LeftRear
            case T:
                m_leftRearCurrentAngle -= 2;
                m_leftRearCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case G:
                m_leftRearCurrentAngle += 2;
                m_leftRearCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case Y:
                m_leftRearDesiredAngle -= 2;
                m_leftRearDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case H:
                m_leftRearDesiredAngle += 2;
                m_leftRearDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case U:
                m_leftRearDriveMotorPercentage -= 0.05;
                m_leftRearDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case J:
                m_leftRearDriveMotorPercentage += 0.05;
                m_leftRearDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case I:
                m_leftRearTurnMotorPercentage -= 0.05;
                m_leftRearTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case K:
                m_leftRearTurnMotorPercentage += 0.05;
                m_leftRearTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            // RightFront
            case O:
                m_rightFrontCurrentAngle -= 2;
                m_rightFrontCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case L:
                m_rightFrontCurrentAngle += 2;
                m_rightFrontCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case P:
                m_rightFrontDesiredAngle -= 2;
                m_rightFrontDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case SEMICOLON:
                m_rightFrontDesiredAngle += 2;
                m_rightFrontDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case BRACELEFT:
                m_rightFrontDriveMotorPercentage -= 0.05;
                m_rightFrontDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case QUOTE:
                m_rightFrontDriveMotorPercentage += 0.05;
                m_rightFrontDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case Z:
                m_rightFrontTurnMotorPercentage -= 0.05;
                m_rightFrontTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case X:
                m_rightFrontTurnMotorPercentage += 0.05;
                m_rightFrontTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            // RightRear
            case C:
                m_rightRearCurrentAngle -= 2;
                m_rightRearCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case V:
                m_rightRearCurrentAngle += 2;
                m_rightRearCurrentAngleLabel.setTextFill(Color.GREEN);
                break;
            case B:
                m_rightRearDesiredAngle -= 2;
                m_rightRearDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case N:
                m_rightRearDesiredAngle += 2;
                m_rightRearDesiredAngleLabel.setTextFill(Color.GREEN);
                break;
            case M:
                m_rightRearDriveMotorPercentage -= 0.05;
                m_rightRearDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case COMMA:
                m_rightRearDriveMotorPercentage += 0.05;
                m_rightRearDriveMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case PERIOD:
                m_rightRearTurnMotorPercentage -= 0.05;
                m_rightRearTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;
            case SLASH:
                m_rightRearTurnMotorPercentage += 0.05;
                m_rightRearTurnMotorPercentageLabel.setTextFill(Color.GREEN);
                break;

            default:
                // ignored
            }
            handleUpdate();
        });


        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            switch (code) {

            // LeftFront
            case Q:
            case A:
                m_leftFrontCurrentAngleLabel.setTextFill(Color.BLACK);
                break;
            case W:
            case S:
                m_leftFrontDesiredAngleLabel.setTextFill(Color.BLACK);
                break;
            case E:
            case D:
                m_leftFrontDriveMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            case R:
            case F:
                m_leftFrontTurnMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            // LeftRear
            case T:
            case G:
                m_leftRearCurrentAngleLabel.setTextFill(Color.BLACK);
                break;
            case Y:
            case H:
                m_leftRearDesiredAngleLabel.setTextFill(Color.BLACK);
                break;
            case U:
            case J:
                m_leftRearDriveMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            case I:
            case K:
                m_leftRearTurnMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            // RightFront
            case O:
            case L:
                m_rightFrontCurrentAngleLabel.setTextFill(Color.BLACK);
                break;
            case P:
            case SEMICOLON:
                m_rightFrontDesiredAngleLabel.setTextFill(Color.BLACK);
                break;
            case BRACELEFT:
            case QUOTE:
                m_rightFrontDriveMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            case Z:
            case X:
                m_rightFrontTurnMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            // RightRear
            case C:
            case V:
                m_rightRearCurrentAngleLabel.setTextFill(Color.BLACK);
                break;
            case B:
            case N:
                m_rightRearDesiredAngleLabel.setTextFill(Color.BLACK);
                break;
            case M:
            case COMMA:
                m_rightRearDriveMotorPercentageLabel.setTextFill(Color.BLACK);
                break;
            case PERIOD:
            case SLASH:
                m_rightRearTurnMotorPercentageLabel.setTextFill(Color.BLACK);
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

            map.putAll(new SwerveModuleData(
                m_leftFrontCurrentAngle,
                m_leftFrontDesiredAngle,
                m_leftFrontDriveMotorPercentage,
                m_leftFrontTurnMotorPercentage
                ).asMap(SmartDashboardNames.LEFT_FRONT_SWERVE_DRIVE + "/"));

            map.putAll(new SwerveModuleData(
                m_leftRearCurrentAngle,
                m_leftRearDesiredAngle,
                m_leftRearDriveMotorPercentage,
                m_leftRearTurnMotorPercentage
                ).asMap(SmartDashboardNames.LEFT_REAR_SWERVE_DRIVE + "/"));

            map.putAll(new SwerveModuleData(
                m_rightFrontCurrentAngle,
                m_rightFrontDesiredAngle,
                m_rightFrontDriveMotorPercentage,
                m_rightFrontTurnMotorPercentage
                ).asMap(SmartDashboardNames.RIGHT_FRONT_SWERVE_DRIVE + "/"));

            map.putAll(new SwerveModuleData(
                m_rightRearCurrentAngle,
                m_rightRearDesiredAngle,
                m_rightRearDriveMotorPercentage,
                m_rightRearTurnMotorPercentage
                ).asMap(SmartDashboardNames.RIGHT_REAR_SWERVE_DRIVE + "/"));

            SwerveDriveData data = new SwerveDriveData(map);
            final SwerveDriveData oldData =  m_controller.dataProperty().getValue();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("swerve_drive_widget.fxml"));
            Pane root = loader.load();
            SwerveDriveWidget controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            new SwerveDriveStandaloneMain(scene, controller);
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
