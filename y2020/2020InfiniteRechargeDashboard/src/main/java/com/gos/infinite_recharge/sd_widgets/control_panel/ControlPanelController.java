package com.gos.infinite_recharge.sd_widgets.control_panel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Consumer;

public class ControlPanelController {
    public enum Colors {
        RED("#FF0000", 0.561, 0.232, 0.114),
        YELLOW("#FFFF00", 0.361, 0.524, 0.113),
        BLUE("#00FFFF", 0.143, 0.427, 0.429),
        GREEN("#00FF00", 0.197, 0.561, 0.240),
        ;

        public String m_hexColor;
        public double m_red;
        public double m_green;
        public double m_blue;

        Colors(String hexColor, double red, double green, double blue) {
            m_hexColor = hexColor;
            Color temp = Color.web(hexColor.substring(1));
            m_red = temp.getRed();
            m_green = temp.getGreen();
            m_blue = temp.getBlue();

        }
    }

    private static final int NUM_PIECES = Colors.values().length * 2;
    private static final String[] COLORS;
    private static final TreeMap<Double, Colors> ANGLE_TO_SENSOR_COLOR; // NOPMD

    static {
        COLORS = new String[NUM_PIECES];
        ANGLE_TO_SENSOR_COLOR = new TreeMap<>();

        double angleSlice = 360.0 / NUM_PIECES;

        for (int i = 0; i < NUM_PIECES; ++i) {
            Colors color = Colors.values()[i % Colors.values().length];
            COLORS[i] = color.m_hexColor;
            double angleStart = angleSlice * i;
            ANGLE_TO_SENSOR_COLOR.put(angleStart, color);
        }

    }

    @FXML
    private PieChart m_controlPanel;

    @FXML
    private Pane m_pane;

    private Consumer<Colors> m_consumerAction;

    public void setColorConsumer(Consumer<Colors> consumer) {
        m_consumerAction = consumer;
    }

    @FXML
    public void initialize() {
        m_pane.setMinWidth(200);
        m_pane.setMaxWidth(200);
        m_pane.setMinHeight(200);
        m_pane.setMaxHeight(200);

        m_controlPanel.setMinWidth(200);
        m_controlPanel.setMaxWidth(200);
        m_controlPanel.setMinHeight(200);
        m_controlPanel.setMaxHeight(200);

        double pieceSize = 1.0 / NUM_PIECES;
        ArrayList<PieChart.Data> pieChartRawData = new ArrayList<>();
        for (int i = 0; i < NUM_PIECES; ++i) {
            pieChartRawData.add(new PieChart.Data("", pieceSize));
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pieChartRawData);
        m_controlPanel.setData(pieChartData);

        for (int i = 0; i < NUM_PIECES; ++i) {
            pieChartData.get(i).getNode().setStyle(
                "-fx-pie-color: " + COLORS[i % COLORS.length] + ";"
            );
        }

        m_controlPanel.setLegendVisible(false);
    }

    public void updateControlPanel(ControlPanelData data) {
        setControlPanelData(data.getSimAngle(), data.getColorSeen());
    }

    public void setControlPanelData(double angle, String colorSeen) {
        double angleToTest = boundAngle(angle);
        m_controlPanel.setRotate(-angle);
        if (m_consumerAction != null) {
            m_consumerAction.accept(ANGLE_TO_SENSOR_COLOR.lowerEntry(angleToTest).getValue());
        }
    }

    private double boundAngle(double angle) {
        double output = angle;
        while (output <= 0) {
            output += 360;
        }
        while (output > 360) {
            output -= 360;
        }
        return output;
    }
}
