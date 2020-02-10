package com.gos.infinite_recharge.sd_widgets;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public final class Utils {
    private Utils() {

    }

    public static void setColor(Shape shape, Color defaultColor, double speed) {
        if (defaultColor != null && Math.abs(speed) < .005) {
            shape.setFill(defaultColor);
        } else {
            shape.setFill(getMotorColor(speed));
        }
    }

    /**
     * Gets a color for a motor based on a speed.
     *
     * @param speed The speed
     * @return The calculated color
     */
    @SuppressWarnings({"PMD.AvoidLiteralsInIfCondition", "PMD.AvoidReassigningParameters"})
    public static Color getMotorColor(double speed) {

        if (Double.isNaN(speed)) {
            speed = 0;
        }
        if (speed > 1) {
            speed = 1;
        } else if (speed < -1) {
            speed = -1;
        }

        double percent = (speed + 1) / 2;
        double hue = percent * 120; // Sweep lower third of the color wheel for
        // red -> green
        double saturation = 1;
        double brightness = 1;

        return Color.hsb(hue, saturation, brightness);
    }
}
