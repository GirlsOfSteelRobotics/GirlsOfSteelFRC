package com.gos.rapidreact.shuffleboard.super_structure;

import com.gos.rapidreact.shuffleboard.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 36;
    private static final double MAX_HEIGHT = 44;
    private static final double CHASSIS_HEIGHT = 4;
    private static final double CHASSIS_WIDTH = 36;
    private static final double CHASSIS_X = 0;
    private static final double CHASSIS_Y = 40;
    private static final double INTAKE_WIDTH = 10;
    private static final double INTAKE_HEIGHT = 16;
    private static final double INTAKE_X = 26;
    private static final double INTAKE_Y = 20;
    private static final double INTAKE_START_ANGLE = 0;
    private static final double INTAKE_WHEEL_WIDTH = 10;
    private static final double INTAKE_WHEEL_HEIGHT = 4;
    private static final double INTAKE_WHEEL_X = 26;
    private static final double INTAKE_WHEEL_Y = 20;
    private static final double HORI_CONVEYOR_WIDTH = 36;
    private static final double HORI_CONVEYOR_HEIGHT = 4;
    private static final double HORI_CONVEYOR_X = 0;
    private static final double HORI_CONVEYOR_Y = 36;
    private static final double VERT_CONVEYOR_WIDTH = 2;
    private static final double VERT_CONVEYOR_HEIGHT = 30;
    private static final double VERT_CONVEYOR_X = 2;
    private static final double VERT_CONVEYOR_Y = 6;
    private static final double HANGER_WIDTH = 2;
    private static final double HANGER_HEIGHT = 32;
    private static final double HANGER_X = 10;
    private static final double HANGER_Y = 4;
    private static final double SHOOTER_WIDTH = 8;
    private static final double SHOOTER_HEIGHT = 6;
    private static final double SHOOTER_X = 2;
    private static final double SHOOTER_Y = 0;



    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;

    @FXML
    private Rectangle m_chassis;

    @FXML
    private Rectangle m_intake;

    @FXML
    private Rectangle m_intakeWheel;

    @FXML
    private Rectangle m_horizontalConveyor;

    @FXML
    private Rectangle m_verticalConveyor;

    @FXML
    private Rectangle m_hanger;

    @FXML
    private Rectangle m_shooter;


    @FXML
    public void initialize() {

        ///////////////////////////////////////////////////////////
        // Controls the inches <-> pixels conversion. Don't touch
        double minWidthMultiplier = 1;
        m_pane.setMinHeight(MAX_HEIGHT * minWidthMultiplier);
        m_pane.setMinWidth(MAX_WIDTH * minWidthMultiplier);

        DoubleBinding scaleBinding = Bindings.createDoubleBinding(() -> {
            double output = Math.min(m_pane.getWidth() / MAX_WIDTH, m_pane.getHeight() / MAX_HEIGHT);
            return output;
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

        m_intakeWheel.setX(INTAKE_WHEEL_X);
        m_intakeWheel.setY(INTAKE_WHEEL_Y);
        m_intakeWheel.setHeight(INTAKE_WHEEL_HEIGHT);
        m_intakeWheel.setWidth(INTAKE_WHEEL_WIDTH);

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

        m_shooter.setX(SHOOTER_X);
        m_shooter.setY(SHOOTER_Y);
        m_shooter.setHeight(SHOOTER_HEIGHT);
        m_shooter.setWidth(SHOOTER_WIDTH);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {

        m_intakeWheel.setFill(Utils.getMotorColor(superStructureData.getIntakeSpeed()));
        m_hanger.setFill(Utils.getMotorColor(superStructureData.getHangerSpeed()));
        m_shooter.setFill(Utils.getMotorColor(superStructureData.getShooterSpeed()));
        m_horizontalConveyor.setFill(Utils.getMotorColor(superStructureData.getHorizontalConveyorSpeed()));
        m_verticalConveyor.setFill(Utils.getMotorColor(superStructureData.getVerticalConveyorSpeed()));

    }


}
