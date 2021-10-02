package com.gos.infinite_recharge.sd_widgets.leds;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Description(name = "Addressable LED Sim Widget", dataTypes = LedData.class)
@ParametrizedController("led_widget.fxml")
public class LedWidget extends SimpleAnnotatedWidget<LedData> {
    @FXML
    private Pane m_root;

    @FXML
    private LedController m_ledsController;

    @FXML
    public void initialize() {

        dataOrDefault.addListener((unused, prev, cur) -> {
            handleUpdate(cur);
        });
    }

    private void handleUpdate(LedData data) {
        if (data.getValues().isEmpty()) {
            m_ledsController.setColors(null);
        }
        List<Color> values = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(data.getValues(), ",");

        while (tokenizer.hasMoreTokens()) {
            double blue = (0xFF & Byte.parseByte(tokenizer.nextElement().toString())) / 255.0;
            double green = (0xFF & Byte.parseByte(tokenizer.nextElement().toString())) / 255.0;
            double red = (0xFF & Byte.parseByte(tokenizer.nextElement().toString())) / 255.0;
            tokenizer.nextElement(); // Unused

            values.add(new Color(red, green, blue, 1));
        }

        m_ledsController.setColors(values);
    }

    @Override
    public Pane getView() {
        return m_root;
    }
}
