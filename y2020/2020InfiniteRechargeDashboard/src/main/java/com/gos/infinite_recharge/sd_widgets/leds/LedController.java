package com.gos.infinite_recharge.sd_widgets.leds;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LedController {

    private static final int NUM_LEDS = 60;
    private static final double RADIUS = 5;

    @FXML
    private Group m_group;

    private final List<Circle> m_leds;

    public LedController() {
        m_leds = new ArrayList<>();
    }

    @FXML
    public void initialize() {

        for (int i = 0; i < NUM_LEDS; ++i) {
            addCircle(RADIUS + i * RADIUS * 2, RADIUS);
        }
    }

    private void addCircle(double x, double y) {
        Circle led = new Circle(x, y, RADIUS);
        m_group.getChildren().add(led);
        m_leds.add(led);
    }


    public void updateLed(LedData data) {
        if (data.getValues().isEmpty()) {
            setColors(null);
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

        setColors(values);
    }

    private void setColors(List<Color> colors) {
        if (colors == null) {
            for (Circle led : m_leds) {
                led.setFill(Color.BLACK);
            }

            return;
        }

        if (m_leds.size() < colors.size()) {
            int diff = colors.size() - m_leds.size();
            for (int i = 0; i < diff; ++i) {
                addCircle(RADIUS, (i + 1) * RADIUS * 2 + RADIUS);
            }
        }

        for (int i = 0; i < colors.size(); ++i) {
            m_leds.get(i).setFill(colors.get(i));
        }

    }
}
