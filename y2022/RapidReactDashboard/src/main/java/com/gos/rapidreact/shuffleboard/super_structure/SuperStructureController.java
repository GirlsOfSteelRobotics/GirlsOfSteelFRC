package com.gos.rapidreact.shuffleboard.super_structure;

import com.gos.rapidreact.shuffleboard.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 36;
    private static final double MAX_HEIGHT = 56;
    private static final double CHASSIS_HEIGHT = 4;
    private static final double CHASSIS_WIDTH = 36;
    private static final double INTAKE_WIDTH = 10;
    private static final double INTAKE_HEIGHT = 16;
    private static final double INTAKE_WHEEL_RADIUS = 3;
    private static final double HORI_CONVEYOR_WIDTH = 36;
    private static final double HORI_CONVEYOR_HEIGHT = 4;
    private static final double VERT_CONVEYOR_WIDTH = 8;
    private static final double VERT_CONVEYOR_HEIGHT = 30;
    private static final double HANGER_WIDTH = 2;
    private static final double HANGER_HEIGHT = 32;
    private static final double SHOOTER_WIDTH = 8;
    private static final double SHOOTER_HEIGHT = 6;

    private static final double CHASSIS_X = MAX_WIDTH - CHASSIS_WIDTH;
    private static final double CHASSIS_Y = MAX_HEIGHT - CHASSIS_HEIGHT;
    private static final double INTAKE_X = MAX_WIDTH - INTAKE_WIDTH;
    private static final double INTAKE_Y = MAX_HEIGHT - CHASSIS_HEIGHT - HORI_CONVEYOR_HEIGHT - INTAKE_HEIGHT;
    private static final double INTAKE_WHEEL_X = INTAKE_X;
    private static final double INTAKE_WHEEL_Y = INTAKE_Y;
    private static final double HORI_CONVEYOR_X = MAX_WIDTH - HORI_CONVEYOR_WIDTH;
    private static final double HORI_CONVEYOR_Y = MAX_HEIGHT - CHASSIS_HEIGHT - HORI_CONVEYOR_HEIGHT;
    private static final double VERT_CONVEYOR_X = MAX_WIDTH - INTAKE_WIDTH - HANGER_WIDTH - VERT_CONVEYOR_WIDTH - 14;
    private static final double VERT_CONVEYOR_Y = MAX_HEIGHT - CHASSIS_HEIGHT - HORI_CONVEYOR_HEIGHT - VERT_CONVEYOR_HEIGHT;
    private static final double HANGER_X = MAX_WIDTH - INTAKE_WIDTH - HANGER_WIDTH - 10;
    private static final double HANGER_Y = MAX_HEIGHT - CHASSIS_HEIGHT - HORI_CONVEYOR_HEIGHT - HANGER_HEIGHT;
    private static final double SHOOTER_X = MAX_WIDTH - INTAKE_WIDTH - VERT_CONVEYOR_WIDTH - SHOOTER_WIDTH - 8;
    private static final double SHOOTER_Y = MAX_HEIGHT - CHASSIS_HEIGHT - HORI_CONVEYOR_HEIGHT - VERT_CONVEYOR_HEIGHT;




    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;

    @FXML
    private Rectangle m_chassis;

    @FXML
    private Rectangle m_intake;

    @FXML
    private Circle m_intakeWheel;

    @FXML
    private Rotate m_intakeRotation;

    @FXML
    private Rotate m_intakeWheelRotation;

    @FXML
    private Rectangle m_horizontalConveyor;

    @FXML
    private Rectangle m_verticalConveyor;

    @FXML
    private Rectangle m_hanger;

    @FXML
    private Arc m_shooter;


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

        m_chassis.setX(CHASSIS_X);
        m_chassis.setY(CHASSIS_Y);
        m_chassis.setHeight(CHASSIS_HEIGHT);
        m_chassis.setWidth(CHASSIS_WIDTH);

        m_intake.setX(INTAKE_X);
        m_intake.setY(INTAKE_Y);
        m_intake.setHeight(INTAKE_HEIGHT);
        m_intake.setWidth(INTAKE_WIDTH);

        m_intakeRotation = new Rotate();
        m_intakeRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_intake.getX() + m_intake.getWidth(), m_intake.xProperty()));
        m_intakeRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_intake.getY() + m_intake.getHeight(), m_intake.yProperty()));
        m_intake.getTransforms().add(m_intakeRotation);

        m_intakeWheelRotation = new Rotate();
        m_intakeWheelRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_intake.getX() + m_intake.getWidth(), m_intake.xProperty()));
        m_intakeWheelRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_intake.getY() + m_intake.getHeight(), m_intake.yProperty()));
        m_intakeWheel.getTransforms().add(m_intakeWheelRotation);

        m_intakeWheel.setCenterX(INTAKE_WHEEL_X);
        m_intakeWheel.setCenterY(INTAKE_WHEEL_Y);
        m_intakeWheel.setRadius(INTAKE_WHEEL_RADIUS);

        m_horizontalConveyor.setX(HORI_CONVEYOR_X);
        m_horizontalConveyor.setY(HORI_CONVEYOR_Y);
        m_horizontalConveyor.setHeight(HORI_CONVEYOR_HEIGHT);
        m_horizontalConveyor.setWidth(HORI_CONVEYOR_WIDTH);

        m_verticalConveyor.setX(VERT_CONVEYOR_X);
        m_verticalConveyor.setY(VERT_CONVEYOR_Y);
        m_verticalConveyor.setHeight(VERT_CONVEYOR_HEIGHT);
        m_verticalConveyor.setWidth(VERT_CONVEYOR_WIDTH);

        m_hanger.setX(HANGER_X);
        m_hanger.setY(HANGER_Y);
        m_hanger.setHeight(HANGER_HEIGHT);
        m_hanger.setWidth(HANGER_WIDTH);

        m_shooter.setCenterX(SHOOTER_X);
        m_shooter.setCenterY(SHOOTER_Y);
        m_shooter.setRadiusY(SHOOTER_HEIGHT);
        m_shooter.setRadiusX(SHOOTER_WIDTH);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {

        m_intake.setStroke(Utils.getMotorColor(superStructureData.getIntakeSpeed()));
        m_intakeWheel.setFill(Utils.getMotorColor(superStructureData.getRollerSpeed()));
        m_intakeRotation.setAngle(superStructureData.getIntakeAngle());
        m_intakeWheelRotation.setAngle(m_intakeRotation.getAngle());
        m_hanger.setFill(Utils.getMotorColor(superStructureData.getHangerSpeed()));
        m_shooter.setFill(Utils.getMotorColor(superStructureData.getShooterSpeed()));
        m_horizontalConveyor.setFill(Utils.getMotorColor(superStructureData.getHorizontalConveyorSpeed()));
        m_verticalConveyor.setStroke(Utils.getMotorColor(superStructureData.getVerticalConveyorSpeed()));
        m_hanger.setHeight(superStructureData.getHangerHeight());
        m_hanger.setY(HANGER_Y + HANGER_HEIGHT - m_hanger.getHeight());

    }


}
