package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import org.littletonrobotics.frc2023.util.Alert;

public class LEDAlertPatterns {
    private final LEDFlash m_alertErrorPattern;
    private final LEDFlash m_alertWarningPattern;
    private final LEDFlash m_noAlertsPattern;

    public LEDAlertPatterns(AddressableLEDBuffer buffer, int startIndex, int numLEDs) {
        m_alertErrorPattern = new LEDFlash(buffer, startIndex, startIndex + numLEDs, 2, Color.kRed);
        m_alertWarningPattern = new LEDFlash(buffer, startIndex, startIndex + numLEDs, 2, Color.kYellow);
        m_noAlertsPattern = new LEDFlash(buffer, startIndex, startIndex + numLEDs, 2, Color.kGreen);
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
