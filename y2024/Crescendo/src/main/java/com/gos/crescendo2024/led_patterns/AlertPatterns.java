package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import org.littletonrobotics.frc2023.util.Alert;

public class AlertPatterns {
    private final LEDFlash m_alertErrorPattern;
    private final LEDFlash m_alertWarningPattern;
    private final LEDFlash m_noAlertsPattern;

    public AlertPatterns(int numLEDs, AddressableLEDBuffer buffer) {
        m_alertErrorPattern = new LEDFlash(buffer, numLEDs / 2, numLEDs, 2, Color.kRed);
        m_alertWarningPattern = new LEDFlash(buffer, numLEDs / 2, numLEDs, 2, Color.kYellow);
        m_noAlertsPattern = new LEDFlash(buffer, numLEDs / 2, numLEDs, 2, Color.kGreen);
    }

    public void writeLEDs() {
        if (Alert.hasErrors()) {
            m_alertErrorPattern.writeLeds();
        } else if (Alert.hasWarnings()) {
            m_alertWarningPattern.writeLeds();
        } else {
            m_noAlertsPattern.writeLeds();
        }
    }
}
