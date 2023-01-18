package com.gos.chargedup.shuffleboard.super_structure;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
//import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 50; // TODO figure out real value
    private static final double MAX_HEIGHT = 80; // TODO figure out real value

    private static final double CHASSIS_HEIGHT = 5; // TODO figure out real value
    private static final double CHASSIS_WIDTH = 37; // TODO figure out real value
    private static final double TURRET_HEIGHT = 3; // TODO figure out real value
    private static final double TURRET_WIDTH = 7; // TODO figure out real value
    private static final double ARM_BASE_HEIGHT = 30; // TODO figure out real value
    private static final double ARM_BASE_WIDTH = 3; // TODO figure out real value
    private static final double ARM1_HEIGHT = 31; // TODO figure out real value
    private static final double ARM1_WIDTH = 2; // TODO figure out real value
    private static final double ARM2_HEIGHT = 31; // TODO figure out real value
    private static final double ARM2_WIDTH = 2; // TODO figure out real value
    private static final double ARM_JOINT_RADIUS = 1; // TODO figure out real value
    private static final double CLAW_HEIGHT = 12; // TODO figure out real value
    private static final double CLAW_WIDTH = 3; // TODO figure out real value
    private static final double BASKET_HEIGHT = 3; // TODO figure out real value
    private static final double BASKET_WIDTH = 15; // TODO figure out real value
    private static final double HOPPER_HEIGHT = 4; // TODO figure out real value
    private static final double HOPPER_WIDTH = 3; // TODO figure out real value
    private static final double INTAKE_HEIGHT = 7; // TODO figure out real value
    private static final double INTAKE_WIDTH = 7; // TODO figure out real value
    private static final double INTAKE_ROLLER1_RADIUS = 2; // TODO figure out real value
    private static final double INTAKE_ROLLER2_RADIUS = 2; // TODO figure out real value


    private static final double CHASSIS_X = 1; // TODO figure out real value
    private static final double CHASSIS_Y = 1; // TODO figure out real value
    private static final double TURRET_X = 1; // TODO figure out real value
    private static final double TURRET_Y = 1; // TODO figure out real value
    private static final double ARM_BASE_X = 1; // TODO figure out real value
    private static final double ARM_BASE_Y = 1; // TODO figure out real value
    private static final double ARM1_X = 1; // TODO figure out real value
    private static final double ARM1_Y = 1; // TODO figure out real value
    private static final double ARM2_X = 1; // TODO figure out real value
    private static final double ARM2_Y = 1; // TODO figure out real value
    private static final double ARM_JOINT_X = 1; // TODO figure out real value
    private static final double ARM_JOINT_Y = 1; // TODO figure out real value
    private static final double CLAW_X = 1; // TODO figure out real value
    private static final double CLAW_Y = 1; // TODO figure out real value
    private static final double BASKET_X = 1; // TODO figure out real value
    private static final double BASKET_Y = 1; // TODO figure out real value
    private static final double HOPPER_X = 1; // TODO figure out real value
    private static final double HOPPER_Y = 1; // TODO figure out real value
    private static final double INTAKE_X = 1; // TODO figure out real value
    private static final double INTAKE_Y = 1; // TODO figure out real value
    private static final double INTAKE_ROLLER1_X = 1; // TODO figure out real value
    private static final double INTAKE_ROLLER1_Y = 1; // TODO figure out real value
    private static final double INTAKE_ROLLER2_X = 1; // TODO figure out real value
    private static final double INTAKE_ROLLER2_Y = 1; // TODO figure out real value

    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;

    
    @FXML
    private Rectangle m_chassis;

    @FXML
    private Rectangle m_turret;

    @FXML
    private Rectangle m_armBase;

    @FXML
    private Rectangle m_arm1;

    @FXML
    private Rectangle m_arm2;

    @FXML
    private Circle m_armJoint;

    @FXML
    private Rectangle m_claw;

    @FXML
    private Rectangle m_basket;

    @FXML
    private Rectangle m_hopper;

    @FXML
    private Rectangle m_intake;

    @FXML
    private Circle m_intakeRoller1;

    @FXML
    private Circle m_intakeRoller2;


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

        m_turret.setX(TURRET_X);
        m_turret.setY(TURRET_Y);
        m_turret.setHeight(TURRET_HEIGHT);
        m_turret.setWidth(TURRET_WIDTH);

        m_armBase.setX(ARM_BASE_X);
        m_armBase.setY(ARM_BASE_Y);
        m_armBase.setHeight(ARM_BASE_HEIGHT);
        m_armBase.setWidth(ARM_BASE_WIDTH);

        m_arm1.setX(ARM1_X);
        m_arm1.setY(ARM1_Y);
        m_arm1.setHeight(ARM1_HEIGHT);
        m_arm1.setWidth(ARM1_WIDTH);

        m_arm2.setX(ARM2_X);
        m_arm2.setY(ARM2_Y);
        m_arm2.setHeight(ARM2_HEIGHT);
        m_arm2.setWidth(ARM2_WIDTH);

        m_armJoint.setCenterX(ARM_JOINT_X);
        m_armJoint.setCenterY(ARM_JOINT_Y);
        m_armJoint.setRadius(ARM_JOINT_RADIUS);

        m_claw.setX(CLAW_X);
        m_claw.setY(CLAW_Y);
        m_claw.setHeight(CLAW_HEIGHT);
        m_claw.setWidth(CLAW_WIDTH);

        m_basket.setX(BASKET_X);
        m_basket.setY(BASKET_Y);
        m_basket.setHeight(BASKET_HEIGHT);
        m_basket.setWidth(BASKET_WIDTH);

        m_hopper.setX(HOPPER_X);
        m_hopper.setY(HOPPER_Y);
        m_hopper.setHeight(HOPPER_HEIGHT);
        m_hopper.setWidth(HOPPER_WIDTH);

        m_intake.setX(INTAKE_X);
        m_intake.setY(INTAKE_Y);
        m_intake.setHeight(INTAKE_HEIGHT);
        m_intake.setWidth(INTAKE_WIDTH);

        m_intakeRoller1.setCenterX(INTAKE_ROLLER1_X);
        m_intakeRoller1.setCenterY(INTAKE_ROLLER1_Y);
        m_intakeRoller1.setRadius(INTAKE_ROLLER1_RADIUS);

        m_intakeRoller2.setCenterX(INTAKE_ROLLER2_X);
        m_intakeRoller2.setCenterY(INTAKE_ROLLER2_Y);
        m_intakeRoller2.setRadius(INTAKE_ROLLER2_RADIUS);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {
        // TODO implement
    }


}
