package com.gos.outreach.shuffleboard.super_structure;

import com.gos.outreach.shuffleboard.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 24;
    private static final double MAX_HEIGHT = 24;
    private static final double CHASSIS_HEIGHT = 8;
    private static final double CHASSIS_WIDTH = 24;
    private static final double CHASSIS_X = 0;
    private static final double CHASSIS_Y = 16;
    private static final double HOOD_HEIGHT = 9;
    private static final double MAX_COLLECTOR_DIST = 8;
    private static final double COLLECTOR_HEIGHT = 7;
    private static final double COLLECTOR_WIDTH = 7;
    private static final double MIN_COLLECTOR_Y = HOOD_HEIGHT;
    private static final double MIN_COLLECTOR_X = MAX_COLLECTOR_DIST;
    private static final double MAX_COLLECTOR_X = 0;
    private static final double MAX_COLLECTOR_Y = HOOD_HEIGHT;
    private static final double HOOD_ARC_CENTER_INITIAL_X = MAX_COLLECTOR_DIST;
    private static final double HOOD_ARC_CENTER_INITIAL_Y = HOOD_HEIGHT;
    private static final double HOOD_ARC_RADIUS = 7;
    private static final double HOOD_ARC_START_ANGLE = 0;
    private static final double HOOD_ARC_LENGTH = 45;
    private static final double SHOOTER_WHEEL_RADIUS = 1;
    private static final double SHOOTER_WHEEL_CENTER_X = 10;
    private static final double SHOOTER_WHEEL_CENTER_Y = 9;
    private static final double SHOOTER_BASE_X = 10;
    private static final double SHOOTER_BASE_Y = MIN_COLLECTOR_Y;
    private static final double SHOOTER_BASE_HEIGHT = 7;
    private static final double SHOOTER_BASE_WIDTH = 5;

    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;

    @FXML
    private Rectangle m_chassis;

    @FXML
    private Rectangle m_collector;

    @FXML
    private Rectangle m_shooterBase;

    @FXML
    private Arc m_hood;

    @FXML
    private Circle m_shooterWheel;

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

        m_collector.setX(MIN_COLLECTOR_X);
        m_collector.setY(MIN_COLLECTOR_Y);
        m_collector.setHeight(COLLECTOR_HEIGHT);
        m_collector.setWidth(COLLECTOR_WIDTH);

        m_hood.setCenterX(HOOD_ARC_CENTER_INITIAL_X);
        m_hood.setCenterY(HOOD_ARC_CENTER_INITIAL_Y);
        m_hood.setRadiusX(HOOD_ARC_RADIUS);
        m_hood.setRadiusY(HOOD_ARC_RADIUS);
        m_hood.setType(ArcType.OPEN);
        m_hood.setStartAngle(HOOD_ARC_START_ANGLE);
        m_hood.setLength(HOOD_ARC_LENGTH);

        m_shooterWheel.setCenterX(SHOOTER_WHEEL_CENTER_X);
        m_shooterWheel.setCenterY(SHOOTER_WHEEL_CENTER_Y);
        m_shooterWheel.setRadius(SHOOTER_WHEEL_RADIUS);

        m_shooterBase.setX(SHOOTER_BASE_X);
        m_shooterBase.setY(SHOOTER_BASE_Y);
        m_shooterBase.setHeight(SHOOTER_BASE_HEIGHT);
        m_shooterBase.setWidth(SHOOTER_BASE_WIDTH);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {
        if (superStructureData.isCollectorIn()) {
            m_collector.setX(MIN_COLLECTOR_X);
            m_collector.setY(MIN_COLLECTOR_Y);
        }
        else {
            m_collector.setX(MAX_COLLECTOR_X);
            m_collector.setY(MAX_COLLECTOR_Y);
        }
        m_collector.setFill(Utils.getMotorColor(superStructureData.getCollectorSpeed()));

        m_hood.setLength(superStructureData.getHoodAngle());
        m_hood.setStroke(Utils.getMotorColor(superStructureData.getHoodMotorSpeed()));

        m_shooterWheel.setFill(Utils.getMotorColor(superStructureData.getShooterMotorSpeed()));
    }


}
