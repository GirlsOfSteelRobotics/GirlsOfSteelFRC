package com.gos.lib.shuffleboard.swerve;

import com.gos.lib.shuffleboard.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class SwerveDriveController {

    private static final double MAX_WIDTH = 28;
    private static final double MAX_HEIGHT = 28;

    private static final double WHEEL_WIDTH = .5;
    private static final double WHEEL_HEIGHT = 4;

    private static final double RIGHT_X = 18;
    private static final double LEFT_X = 8;
    private static final double REAR_Y = 18;
    private static final double FRONT_Y = 8;


    private SwerveModule m_frontLeftModule;
    private SwerveModule m_frontRightModule;
    private SwerveModule m_rearLeftModule;
    private SwerveModule m_rearRightModule;

    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;

    private class SwerveModule {
        private final ModuleState m_currentState;
        private final ModuleState m_desiredState;

        private class ModuleState {
            private final Rectangle m_state;
            private final Circle m_directionIndicator;
            private final Rotate m_directionIndicatorRotation;

            public ModuleState(double x, double y, Color fillColor) {
                m_state = new Rectangle(x, y, WHEEL_WIDTH, WHEEL_HEIGHT);
                m_state.setFill(fillColor);
                m_group.getChildren().add(m_state);

                m_directionIndicator = new Circle(x + WHEEL_WIDTH / 2, y, WHEEL_WIDTH / 2);
                m_directionIndicator.setFill(fillColor);
                m_directionIndicator.setStroke(Color.BLACK);
                m_directionIndicator.setStrokeWidth(0.1);
                m_directionIndicatorRotation = new Rotate();
                m_directionIndicatorRotation.setPivotX(x + WHEEL_WIDTH / 2);
                m_directionIndicatorRotation.setPivotY(y + WHEEL_HEIGHT / 2);
                m_directionIndicator.getTransforms().add(m_directionIndicatorRotation);
                m_group.getChildren().add(m_directionIndicator);
            }

            public ModuleState(double x, double y, Color fillColor, Color strokeColor) {
                this(x, y, fillColor);
                m_state.setStroke(strokeColor);
                m_state.setStrokeWidth(0.1);
            }

            public void setAngle(double angle) {
                double correctedAngle = -angle;
                m_state.setRotate(correctedAngle);
                m_directionIndicatorRotation.setAngle(correctedAngle);
            }

            public void setMotorPercentage(double driveMotorPercentage, double turnMotorPercentage) {
                m_directionIndicator.setFill(Utils.getMotorColor(driveMotorPercentage));
                m_state.setFill(Utils.getMotorColor(turnMotorPercentage));
            }
        }

        public SwerveModule(double x, double y) {
            m_currentState = new ModuleState(x, y, Color.AQUA);
            m_desiredState = new ModuleState(x, y, Color.TRANSPARENT, Color.BLACK);
        }

        public void setData(SwerveModuleData data) {
            m_currentState.setAngle(data.getCurrentAngle());
            m_currentState.setMotorPercentage(data.getDriveMotorPercentage(), data.getTurnMotorPercentage());
            m_desiredState.setAngle(data.getDesiredAngle());
        }
    }

    @FXML
    public void initialize() {

        ///////////////////////////////////////////////////////////
        // Controls the inches <-> pixels conversion. Don't touch
        double minWidthMultiplier = 1;
        m_pane.setMinHeight(MAX_HEIGHT * minWidthMultiplier);
        m_pane.setMinWidth(MAX_WIDTH * minWidthMultiplier);

        DoubleBinding scaleBinding = Bindings.createDoubleBinding(() -> {
            return Math.min(m_pane.getWidth() / MAX_WIDTH, m_pane.getHeight() / MAX_HEIGHT);
        }, m_pane.widthProperty(), m_pane.heightProperty());

        Scale scale = new Scale();
        scale.xProperty().bind(scaleBinding);
        scale.yProperty().bind(scaleBinding);

        m_group.getTransforms().add(scale);
        ///////////////////////////////////////////////////////////


        m_frontLeftModule = new SwerveModule(LEFT_X, FRONT_Y);
        m_frontRightModule = new SwerveModule(RIGHT_X, FRONT_Y);
        m_rearLeftModule = new SwerveModule(LEFT_X, REAR_Y);
        m_rearRightModule = new SwerveModule(RIGHT_X, REAR_Y);

    }

    public void updateLeftFront(SwerveModuleData data) {
        m_frontLeftModule.setData(data);
    }

    public void updateLeftRear(SwerveModuleData data) {
        m_rearLeftModule.setData(data);
    }

    public void updateRightFront(SwerveModuleData data) {
        m_frontRightModule.setData(data);
    }

    public void updateRightRear(SwerveModuleData data) {
        m_rearRightModule.setData(data);
    }


}
