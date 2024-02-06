package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AlertPatterns {
    private final LEDFlash m_alertErrorPattern;
    private final LEDFlash m_alertWarningPattern;
    private final LEDFlash m_noAlertsPattern;
    public AlertPatterns(int numLEDs, AddressableLEDBuffer buffer) {
        m_alertErrorPattern = new LEDFlash(buffer, numLEDs/2, numLEDs, 2, Color.kRed);
        m_alertWarningPattern = new LEDFlash(buffer, numLEDs/2, numLEDs, 2, Color.kYellow);
        m_noAlertsPattern = new LEDFlash(buffer, numLEDs/2, numLEDs, 2, Color.kGreen);
    }

    public void writeAlertErrorPattern() { m_alertErrorPattern.writeLeds(); }
    public void writeAlertWarningPattern() { m_alertWarningPattern.writeLeds(); }
    public void writeNoAlertsPattern() { m_noAlertsPattern.writeLeds(); }

}
