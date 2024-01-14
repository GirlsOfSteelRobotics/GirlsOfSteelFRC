package com.gos.crescendo.shuffleboard.super_structure;

import com.gos.crescendo.shuffleboard.Utils;
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

public class SuperStructureController {

    private static final double MAX_WIDTH = 15; // TODO figure out real value
    private static final double MAX_HEIGHT = 15; // TODO figure out real value

    private static final double CHASSIS_HEIGHT = 2;
    private static final double CHASSIS_WIDTH = 8;
    private static final double PIVOT_MOTOR_RADIUS = 1;
    private static final double ARM_RECT_HEIGHT = 6;
    private static final double ARM_RECT_WIDTH = 1;
    private static final double SHOOTER_RECT_HEIGHT = 1;
    private static final double SHOOTER_RECT_WIDTH = 6;
    private static final double SHOOTER_MOTOR_RADIUS = 0.5;
    private static final double INTAKE_MOTOR_RADIUS = 0.5;
    private static final double PIVOT_ANGLE_GOAL_HEIGHT = ARM_RECT_HEIGHT;
    private static final double PIVOT_ANGLE_GOAL_WIDTH = ARM_RECT_WIDTH;


    private static final double CHASSIS_X = 3;
    private static final double CHASSIS_Y = 9;
    private static final double PIVOT_MOTOR_X = 3.5;
    private static final double PIVOT_MOTOR_Y = 9;
    private static final double ARM_RECT_X = 3;
    private static final double ARM_RECT_Y = 3;
    private static final double SHOOTER_RECT_X = 2;
    private static final double SHOOTER_RECT_Y = 2;
    private static final double SHOOTER_MOTOR_X = 1.75;
    private static final double SHOOTER_MOTOR_Y = 2.5;
    private static final double INTAKE_MOTOR_X = 8.25;
    private static final double INTAKE_MOTOR_Y = 2.5;
    private static final double PIVOT_ANGLE_GOAL_X = ARM_RECT_X;
    private static final double PIVOT_ANGLE_GOAL_Y = ARM_RECT_Y;

    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;
    
    @FXML
    private Rectangle m_chassis;

    @FXML
    private Circle m_pivotMotor;

    @FXML
    private Rectangle m_armRect;

    @FXML
    private Rectangle m_shooterRect;

    @FXML
    private Circle m_shooterMotor;

    @FXML
    private Circle m_intakeMotor;

    @FXML
    private Rectangle m_pivotAngleGoal;

    private Rotate m_armRectRotation;

    private Rotate m_pivotAngleGoalRotation;

    @FXML
    public void initialize() {

        ///////////////////////////////////////////////////////////
        // Controls the inches <-> pixels conversion. Don't touch
        double minWidthMultiplier = 1;
        m_pane.setMinHeight(MAX_HEIGHT * minWidthMultiplier);
        m_pane.setMinWidth(MAX_WIDTH * minWidthMultiplier);

        DoubleBinding scaleBinding = Bindings.createDoubleBinding(() -> Math.min(m_pane.getWidth() / MAX_WIDTH, m_pane.getHeight() / MAX_HEIGHT), m_pane.widthProperty(), m_pane.heightProperty());

        Scale scale = new Scale();
        scale.xProperty().bind(scaleBinding);
        scale.yProperty().bind(scaleBinding);

        m_group.getTransforms().add(scale);
        ///////////////////////////////////////////////////////////

        m_chassis.setX(CHASSIS_X);
        m_chassis.setY(CHASSIS_Y);
        m_chassis.setHeight(CHASSIS_HEIGHT);
        m_chassis.setWidth(CHASSIS_WIDTH);

        m_pivotMotor.setCenterX(PIVOT_MOTOR_X);
        m_pivotMotor.setCenterY(PIVOT_MOTOR_Y);
        m_pivotMotor.setRadius(PIVOT_MOTOR_RADIUS);

        m_armRect.setX(ARM_RECT_X);
        m_armRect.setY(ARM_RECT_Y);
        m_armRect.setHeight(ARM_RECT_HEIGHT);
        m_armRect.setWidth(ARM_RECT_WIDTH);

        m_shooterRect.setX(SHOOTER_RECT_X);
        m_shooterRect.setY(SHOOTER_RECT_Y);
        m_shooterRect.setHeight(SHOOTER_RECT_HEIGHT);
        m_shooterRect.setWidth(SHOOTER_RECT_WIDTH);

        m_shooterMotor.setCenterX(SHOOTER_MOTOR_X);
        m_shooterMotor.setCenterY(SHOOTER_MOTOR_Y);
        m_shooterMotor.setRadius(SHOOTER_MOTOR_RADIUS);

        m_intakeMotor.setCenterX(INTAKE_MOTOR_X);
        m_intakeMotor.setCenterY(INTAKE_MOTOR_Y);
        m_intakeMotor.setRadius(INTAKE_MOTOR_RADIUS);

        m_pivotAngleGoal.setX(PIVOT_ANGLE_GOAL_X);
        m_pivotAngleGoal.setY(PIVOT_ANGLE_GOAL_Y);
        m_pivotAngleGoal.setHeight(PIVOT_ANGLE_GOAL_HEIGHT);
        m_pivotAngleGoal.setWidth(PIVOT_ANGLE_GOAL_WIDTH);

        m_armRectRotation = new Rotate();
        m_armRectRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_armRect.getX() + ARM_RECT_WIDTH, m_armRect.xProperty()));
        m_armRectRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_armRect.getY() + ARM_RECT_HEIGHT, m_armRect.yProperty()));
        m_armRect.getTransforms().add(m_armRectRotation);
        m_shooterRect.getTransforms().add(m_armRectRotation);
        m_intakeMotor.getTransforms().add(m_armRectRotation);
        m_shooterMotor.getTransforms().add(m_armRectRotation);

        m_pivotAngleGoalRotation = new Rotate();
        m_pivotAngleGoalRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_armRect.getX() + ARM_RECT_WIDTH, m_armRect.xProperty()));
        m_pivotAngleGoalRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_armRect.getY() + ARM_RECT_HEIGHT, m_armRect.yProperty()));
        m_pivotAngleGoal.getTransforms().add(m_pivotAngleGoalRotation);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {

        m_armRectRotation.setAngle(superStructureData.getPivotMotorAngle());
        m_pivotAngleGoalRotation.setAngle(superStructureData.getGoalAngle());

        m_pivotMotor.setFill(Utils.getMotorColor(superStructureData.getPivotMotorPercentage()));
        m_shooterMotor.setFill(Utils.getMotorColor(superStructureData.getShooterMotorPercentage()));
        m_intakeMotor.setFill(Utils.getMotorColor(superStructureData.getIntakeMotorPercentage()));

        if (superStructureData.isHasGamePiece()) {
            m_shooterRect.setFill(Color.ORANGE);
        }
        else {
            m_shooterRect.setFill(Color.TRANSPARENT);
        }

        if (superStructureData.getGoalAngle() == Double.MIN_VALUE) {
            m_pivotAngleGoal.setStroke(Color.TRANSPARENT);
        } else {
            m_pivotAngleGoal.setStroke(Color.BLACK);
        }
    }


}
