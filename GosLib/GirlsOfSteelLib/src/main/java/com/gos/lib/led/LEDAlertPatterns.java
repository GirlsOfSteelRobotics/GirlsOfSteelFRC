package com.gos.lib.led;

import org.wpilib.hardware.led.AddressableLEDBuffer;
import org.wpilib.util.Color;

public class LEDAlertPatterns {
    private final LEDFlash m_alertErrorPattern;
    private final LEDFlash m_alertWarningPattern;
    private final LEDFlash m_noAlertsPattern;

    public LEDAlertPatterns(AddressableLEDBuffer buffer, int startIndex, int numLEDs) {
        m_alertErrorPattern = new LEDFlash(buffer, startIndex, startIndex + numLEDs, 2, Color.RED);
        m_alertWarningPattern = new LEDFlash(buffer, startIndex, startIndex + numLEDs, 2, Color.YELLOW);
        m_noAlertsPattern = new LEDFlash(buffer, startIndex, startIndex + numLEDs, 2, Color.GREEN);
    }

    public void writeLEDs() {
        if (hasErrors()) {
            m_alertErrorPattern.writeLeds();
        } else if (hasWarnings()) {
            m_alertWarningPattern.writeLeds();
        } else {
            m_noAlertsPattern.writeLeds();
        }
    }

    private boolean hasErrors() {
        // TODO wait for wpilib update
        return false;
    }

    public static boolean hasWarnings() {
        // TODO wait for wpilib update
        return false;
    }
}
