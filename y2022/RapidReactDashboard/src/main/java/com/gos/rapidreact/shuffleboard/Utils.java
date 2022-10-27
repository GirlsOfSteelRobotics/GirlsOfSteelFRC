package com.gos.rapidreact.shuffleboard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public final class Utils {
    private Utils() {

    }

    public static void setMotorColor(Shape shape, Color defaultColor, double speed) {
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
        return getClampedColor(speed, -1, 1);
    }

    @SuppressWarnings({"PMD.AvoidLiteralsInIfCondition", "PMD.AvoidReassigningParameters"})
    public static Color getClampedColor(double speed, double min, double max) {

        if (Double.isNaN(speed)) {
            speed = 0;
        }
        if (speed > max) {
            speed = max;
        } else if (speed < min) {
            speed = min;
        }

        double spread = max - min;

        double percent = (speed - min) / spread;
        double hue = percent * 120; // Sweep lower third of the color wheel for
        // red -> green
        double saturation = 1;
        double brightness = 1;

        return Color.hsb(hue, saturation, brightness);
    }

    public static Color getSensorColor(boolean booleanValue) {
        if (booleanValue) {
            return Color.color(0.25, 1, 0.25);
        }
        if (!booleanValue) {
            return Color.color(1, 0.5, 0.5);
        }
        return Color.color(0, 0, 0);
    }
}
