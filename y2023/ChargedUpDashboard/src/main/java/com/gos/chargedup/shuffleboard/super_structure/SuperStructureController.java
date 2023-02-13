package com.gos.chargedup.shuffleboard.super_structure;

import com.gos.chargedup.shuffleboard.Utils;
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

    private static final double MAX_WIDTH = 100;
    private static final double MAX_HEIGHT = 50;

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
    private static final double CHASSIS_TOP_DOWN_HEIGHT = 20;
    private static final double CHASSIS_TOP_DOWN_WIDTH = 14;
    private static final double TURRET_TOP_DOWN_HEIGHT = 15;
    private static final double TURRET_TOP_DOWN_WIDTH = 3;


    private static final double CHASSIS_X = 35;
    private static final double CHASSIS_Y = MAX_HEIGHT - 6;
    private static final double TURRET_X = 38.5;
    private static final double TURRET_Y = MAX_HEIGHT - 9.5;
    private static final double ARM_BASE_X = 45;
    private static final double ARM_BASE_Y = MAX_HEIGHT - 28;
    private static final double ARM_X = ARM_BASE_X + 1;
    private static final double ARM_Y = ARM_BASE_Y;
    private static final double ARM_JOINT_X = ARM_BASE_X + 1;
    private static final double ARM_JOINT_Y = ARM_BASE_Y;
    private static final double CLAW_X = ARM_X - 1;
    private static final double CLAW_Y = ARM_Y + 15;
    private static final double INTAKE_X = CHASSIS_X + 30;
    private static final double INTAKE_Y = CHASSIS_Y;
    private static final double CHASSIS_TOP_DOWN_X = 10;
    private static final double CHASSIS_TOP_DOWN_Y = 10;
    private static final double TURRET_TOP_DOWN_X = CHASSIS_TOP_DOWN_X + (CHASSIS_TOP_DOWN_WIDTH - TURRET_TOP_DOWN_WIDTH) / 2;
    private static final double TURRET_TOP_DOWN_Y = CHASSIS_TOP_DOWN_Y + CHASSIS_TOP_DOWN_HEIGHT / 2 - TURRET_TOP_DOWN_HEIGHT;

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
    private Rectangle m_armGoal;

    @FXML
    private Circle m_armJoint;

    @FXML
    private Rectangle m_claw;

    @FXML
    private Rectangle m_intake;

    @FXML
    private Rectangle m_chassisTopDown;

    @FXML
    private Rectangle m_turretTopDown;

    @FXML
    private Rectangle m_turretTopDownGoal;

    // Rotations
    private Rotate m_armRotation;
    private Rotate m_armGoalRotation;

    private Rotate m_intakeRotation;

    private Rotate m_turretTopDownRotation;
    private Rotate m_turretTopDownGoalRotation;


    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NcssCount"})
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

        m_armGoal.setX(ARM_X);
        m_armGoal.setY(ARM_Y);
        m_armGoal.setHeight(ARM_HEIGHT);
        m_armGoal.setWidth(ARM_WIDTH);
        m_armGoal.setFill(Color.TRANSPARENT);
        m_armGoal.setStrokeWidth(.1);

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

        m_chassisTopDown.setX(CHASSIS_TOP_DOWN_X);
        m_chassisTopDown.setY(CHASSIS_TOP_DOWN_Y);
        m_chassisTopDown.setHeight(CHASSIS_TOP_DOWN_HEIGHT);
        m_chassisTopDown.setWidth(CHASSIS_TOP_DOWN_WIDTH);

        m_turretTopDown.setX(TURRET_TOP_DOWN_X);
        m_turretTopDown.setY(TURRET_TOP_DOWN_Y);
        m_turretTopDown.setHeight(TURRET_TOP_DOWN_HEIGHT);
        m_turretTopDown.setWidth(TURRET_TOP_DOWN_WIDTH);

        m_turretTopDownGoal.setX(TURRET_TOP_DOWN_X);
        m_turretTopDownGoal.setY(TURRET_TOP_DOWN_Y);
        m_turretTopDownGoal.setHeight(TURRET_TOP_DOWN_HEIGHT);
        m_turretTopDownGoal.setWidth(TURRET_TOP_DOWN_WIDTH);
        m_turretTopDownGoal.setFill(Color.TRANSPARENT);
        m_turretTopDownGoal.setStrokeWidth(0.1);


        // Rotations
        m_armRotation = new Rotate();
        m_armRotation.setAngle(-45);
        m_armRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_arm.getX(), m_arm.xProperty()));
        m_armRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_arm.getY(), m_arm.yProperty()));
        m_arm.getTransforms().add(m_armRotation);
        m_claw.getTransforms().add(m_armRotation);

        m_armGoalRotation = new Rotate();
        m_armGoalRotation.setAngle(-45);
        m_armGoalRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_armGoal.getX(), m_armGoal.xProperty()));
        m_armGoalRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_armGoal.getY(), m_armGoal.yProperty()));
        m_armGoal.getTransforms().add(m_armGoalRotation);

        m_intakeRotation = new Rotate();
        m_intakeRotation.setAngle(-66);
        m_intakeRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_intake.getX(), m_intake.xProperty()));
        m_intakeRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_intake.getY(), m_intake.yProperty()));
        m_intake.getTransforms().add(m_intakeRotation);


        m_turretTopDownRotation = new Rotate();
        m_turretTopDownRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_turretTopDown.getX() + TURRET_TOP_DOWN_WIDTH / 2, m_turretTopDown.xProperty()));
        m_turretTopDownRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_turretTopDown.getY() + TURRET_TOP_DOWN_HEIGHT, m_turretTopDown.yProperty()));
        m_turretTopDown.getTransforms().add(m_turretTopDownRotation);

        m_turretTopDownGoalRotation = new Rotate();
        m_turretTopDownGoalRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_turretTopDownGoal.getX() + TURRET_TOP_DOWN_WIDTH / 2, m_turretTopDownGoal.xProperty()));
        m_turretTopDownGoalRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_turretTopDownGoal.getY() + TURRET_TOP_DOWN_HEIGHT, m_turretTopDownGoal.yProperty()));
        m_turretTopDownGoal.getTransforms().add(m_turretTopDownGoalRotation);
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void updateSuperStructure(SuperStructureData superStructureData) {
        m_armRotation.setAngle(180 + 90 - superStructureData.getArmAngle());
        m_armGoalRotation.setAngle(180 + 90 - superStructureData.getArmGoalAngle());
        if (superStructureData.getArmGoalAngle() == Double.MIN_VALUE) {
            m_armGoal.setStroke(Color.TRANSPARENT);
        } else {
            m_armGoal.setStroke(Color.BLACK);
        }
        m_arm.setFill(Utils.getMotorColor(superStructureData.getArmSpeed()));

        double javafxRobotAngle = 90 - superStructureData.getRobotAngle();
        m_chassisTopDown.setRotate(javafxRobotAngle);
        m_turretTopDownRotation.setAngle(javafxRobotAngle + superStructureData.getTurretAngle());
        m_turretTopDownRotation.setAngle(javafxRobotAngle + superStructureData.getTurretAngle());
        m_turretTopDownGoalRotation.setAngle(javafxRobotAngle + superStructureData.getTurretGoalAngle());

        m_turretTopDown.setFill(Utils.getMotorColor(superStructureData.getTurretSpeed()));

        if (superStructureData.getTurretGoalAngle() == Double.MIN_VALUE) {
            m_turretTopDownGoal.setStroke(Color.TRANSPARENT);
        } else {
            m_turretTopDownGoal.setStroke(Color.BLACK);
        }
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
