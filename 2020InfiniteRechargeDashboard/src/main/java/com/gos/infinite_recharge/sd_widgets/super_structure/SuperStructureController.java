package com.gos.infinite_recharge.sd_widgets.super_structure;

import javafx.scene.paint.Color;

import com.gos.infinite_recharge.sd_widgets.Utils;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ControlPanelData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.LiftData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ShooterConveyorData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ShooterIntakeData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.ShooterWheelsData;
import com.gos.infinite_recharge.sd_widgets.super_structure.data.WinchData;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 75;
    private static final double MAX_HEIGHT = 125;

    @FXML
    private Group m_group;
    
    @FXML
    private Pane m_pane;

    @FXML
    private Circle m_robotShooterCurrentRPM;

    @FXML
    private Circle m_robotShooterGoalRPM;

    @FXML
    private Rectangle m_robotConveyor;

    @FXML
    private Rectangle m_robotIntake;

    @FXML
    private Circle m_shooterHandoff;

    @FXML
    private Circle m_shooterSecondary;

    @FXML
    private Circle m_shooterTop;

    @FXML
    private Rectangle m_controlPanel;

    @FXML
    public Rectangle m_winch;

    @FXML
    public Rectangle m_lift;

    private Rotate m_intakeRotation;

    @FXML
    public void initialize() {

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


        m_intakeRotation = new Rotate();
        m_intakeRotation.pivotXProperty().bind(Bindings.createObjectBinding(() -> m_robotIntake.getX() + m_robotIntake.getWidth(), m_robotIntake.xProperty()));
        m_intakeRotation.pivotYProperty().bind(Bindings.createObjectBinding(() -> m_robotIntake.getY() + m_robotIntake.getHeight(), m_robotIntake.yProperty()));
        m_robotIntake.getTransforms().add(m_intakeRotation);
    }

    public void updateShooterConveyor(ShooterConveyorData shooterConveyorData) {
        m_robotConveyor.setStroke(Utils.getMotorColor(shooterConveyorData.getSpeed()));
        if (shooterConveyorData.getHandoffBallSensor()) {
            m_shooterHandoff.setFill(Color.YELLOW);
        }
        else {
            m_shooterSecondary.setFill(Color.TRANSPARENT);
        }
        if (shooterConveyorData.getSecondaryBallSensor()) {
            m_shooterSecondary.setFill(Color.YELLOW);
        }
        else {
            m_shooterSecondary.setFill(Color.TRANSPARENT);
        }
        if (shooterConveyorData.getTopBallSensor()) {
            m_shooterTop.setFill(Color.YELLOW);
        }
        else {
            m_shooterSecondary.setFill(Color.TRANSPARENT);
        }
    }

    public void updateShooterIntake(ShooterIntakeData shooterIntakeData) {
        m_robotIntake.setFill(Utils.getMotorColor(shooterIntakeData.getSpeed()));
        m_intakeRotation.setAngle(shooterIntakeData.getPosition() ? -90 : 0);
    }
    
    public void updateShooterWheels(ShooterWheelsData shooterWheelsData) {
        m_robotShooterCurrentRPM.setFill(Utils.getClampedColor(shooterWheelsData.getCurrentRpm(), 4000, 8000));
        m_robotShooterGoalRPM.setFill(Utils.getClampedColor(shooterWheelsData.getGoalRpm(), 4000, 8000));
    }
    

    public void updateControlPanel(ControlPanelData controlPanelData) {
        m_controlPanel.setFill(Utils.getMotorColor(controlPanelData.getSpeed()));
    }

    public void updateWinch(WinchData winchData) {
        m_winch.setFill(Utils.getMotorColor(winchData.getSpeed()));
    }

    public void updateLift(LiftData liftData) {
        m_lift.setFill(Utils.getMotorColor(liftData.getSpeed()));
    }
}