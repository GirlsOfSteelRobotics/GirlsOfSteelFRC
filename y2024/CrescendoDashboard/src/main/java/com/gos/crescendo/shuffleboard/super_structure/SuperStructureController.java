package com.gos.crescendo.shuffleboard.super_structure;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class SuperStructureController {

    private static final double MAX_WIDTH = 100; // TODO figure out real value
    private static final double MAX_HEIGHT = 100; // TODO figure out real value

    private static final double RECTANGLE_HEIGHT = 40;
    private static final double RECTANGLE_WIDTH = 10;
    private static final double CORCLE_RADIUS = 20;
    private static final double RECTTTT_HEIGHT = 35.04;
    private static final double RECTTTT_WIDTH = 20;
    private static final double CCIIRRCCLLEE_RADIUS = 8;


    private static final double RECTANGLE_X = 50;
    private static final double RECTANGLE_Y = 50;
    private static final double CORCLE_X = 40;
    private static final double CORCLE_Y = 40;
    private static final double RECTTTT_X = 10;
    private static final double RECTTTT_Y = 10;
    private static final double CCIIRRCCLLEE_X = 12;
    private static final double CCIIRRCCLLEE_Y = 17;

    @FXML
    private Group m_group;

    @FXML
    private Pane m_pane;
    
    @FXML
    private Rectangle m_rectangle;

    @FXML
    private Circle m_corcle;

    @FXML
    private Rectangle m_rectttt;

    @FXML
    private Circle m_cciirrccllee;


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

        m_rectangle.setX(RECTANGLE_X);
        m_rectangle.setY(RECTANGLE_Y);
        m_rectangle.setHeight(RECTANGLE_HEIGHT);
        m_rectangle.setWidth(RECTANGLE_WIDTH);

        m_corcle.setCenterX(CORCLE_X);
        m_corcle.setCenterY(CORCLE_Y);
        m_corcle.setRadius(CORCLE_RADIUS);

        m_rectttt.setX(RECTTTT_X);
        m_rectttt.setY(RECTTTT_Y);
        m_rectttt.setHeight(RECTTTT_HEIGHT);
        m_rectttt.setWidth(RECTTTT_WIDTH);

        m_cciirrccllee.setCenterX(CCIIRRCCLLEE_X);
        m_cciirrccllee.setCenterY(CCIIRRCCLLEE_Y);
        m_cciirrccllee.setRadius(CCIIRRCCLLEE_RADIUS);

    }


    public void updateSuperStructure(SuperStructureData superStructureData) {
        // TODO implement
    }


}
