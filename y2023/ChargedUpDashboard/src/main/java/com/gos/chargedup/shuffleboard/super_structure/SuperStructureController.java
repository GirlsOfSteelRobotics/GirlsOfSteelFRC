package com.gos.chargedup.shuffleboard.super_structure;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
//import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 100;
    private static final double MAX_HEIGHT = 100;

    private static final double CHASSIS_HEIGHT = 6;
    private static final double CHASSIS_WIDTH = 30;
    private static final double TURRET_HEIGHT = 9.5;
    private static final double TURRET_WIDTH = 15.5;
    private static final double ARM_BASE_HEIGHT = 18.5;
    private static final double ARM_BASE_WIDTH = 2;
    private static final double ARM_HEIGHT = 15;
    private static final double ARM_WIDTH = 1;
    private static final double ARM_JOINT_RADIUS = 1;
    private static final double CLAW_HEIGHT = 10;
    private static final double CLAW_WIDTH = 2;
    private static final double INTAKE_HEIGHT = 15;
    private static final double INTAKE_WIDTH = 6;


    private static final double CHASSIS_X = 35;
    private static final double CHASSIS_Y = 94;
    private static final double TURRET_X = 38.5;
    private static final double TURRET_Y = 90.5;
    private static final double ARM_BASE_X = 45;
    private static final double ARM_BASE_Y = 72;
    private static final double ARM_X = ARM_BASE_X + 1;
    private static final double ARM_Y = ARM_BASE_Y;
    private static final double ARM_JOINT_X = ARM_BASE_X + 1;
    private static final double ARM_JOINT_Y = ARM_BASE_Y;
    private static final double CLAW_X = ARM_X - 1;
    private static final double CLAW_Y = ARM_Y + 15;
    private static final double INTAKE_X = CHASSIS_X + 30;
    private static final double INTAKE_Y = CHASSIS_Y;

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
    private Rectangle m_arm;

    @FXML
    private Circle m_armJoint;

    @FXML
    private Rectangle m_claw;

    @FXML
    private Rectangle m_intake;

    private Rotate m_armRotation;

    private Rotate m_clawRotation;

    private Rotate m_intakeRotation;


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

        m_turret.setX(TURRET_X);
        m_turret.setY(TURRET_Y);
        m_turret.setHeight(TURRET_HEIGHT);
        m_turret.setWidth(TURRET_WIDTH);

        m_armBase.setX(ARM_BASE_X);
        m_armBase.setY(ARM_BASE_Y);
        m_armBase.setHeight(ARM_BASE_HEIGHT);
        m_armBase.setWidth(ARM_BASE_WIDTH);

        m_arm.setX(ARM_X);
        m_arm.setY(ARM_Y);
        m_arm.setHeight(ARM_HEIGHT);
        m_arm.setWidth(ARM_WIDTH);

        m_armJoint.setCenterX(ARM_JOINT_X);
        m_armJoint.setCenterY(ARM_JOINT_Y);
        m_armJoint.setRadius(ARM_JOINT_RADIUS);

        m_claw.setX(CLAW_X);
        m_claw.setY(CLAW_Y);
        m_claw.setHeight(CLAW_HEIGHT);
        m_claw.setWidth(CLAW_WIDTH);

        m_intake.setX(INTAKE_X);
        m_intake.setY(INTAKE_Y);
        m_intake.setHeight(INTAKE_HEIGHT);
        m_intake.setWidth(INTAKE_WIDTH);

        m_armRotation = new Rotate();
        m_armRotation.setAngle(-45);
        m_armRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_arm.getX(), m_arm.xProperty()));
        m_armRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_arm.getY(), m_arm.yProperty()));
        m_arm.getTransforms().add(m_armRotation);

        m_clawRotation = new Rotate();
        m_clawRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_arm.getX(), m_arm.xProperty()));
        m_clawRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_arm.getY(), m_arm.yProperty()));
        m_claw.getTransforms().add(m_armRotation);

        m_intakeRotation = new Rotate();
        m_intakeRotation.setAngle(-66);
        m_intakeRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_intake.getX(), m_intake.xProperty()));
        m_intakeRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_intake.getY(), m_intake.yProperty()));
        m_intake.getTransforms().add(m_intakeRotation);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {
        // TODO implement
        m_armRotation.setAngle(superStructureData.getArmAngle());

        if (superStructureData.isIntakeDown()) {
            m_intakeRotation.setAngle(-90.0);
        } else {
            m_intakeRotation.setAngle(180.0);
        }


        if (!superStructureData.isArmExtension1() && !superStructureData.isArmExtension2()) {
            m_arm.setHeight(15);
            m_claw.setY(ARM_Y + 15);
        } else if (!superStructureData.isArmExtension1() && superStructureData.isArmExtension2()) {
            m_arm.setHeight(25);
            m_claw.setY(ARM_Y + 25);
        } else if (superStructureData.isArmExtension1() && !superStructureData.isArmExtension2()) {
            m_arm.setHeight(35);
            m_claw.setY(ARM_Y + 35);
        }
    }


}
