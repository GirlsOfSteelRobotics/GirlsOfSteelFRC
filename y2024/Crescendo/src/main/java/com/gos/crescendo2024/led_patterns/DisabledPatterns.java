package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.led_patterns.subpatterns.AlertPatterns;
import com.gos.crescendo2024.led_patterns.subpatterns.AutoModePattern;
import com.gos.lib.led.LEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class DisabledPatterns {

    private final Autos m_autoModeFactory;
    private final AlertPatterns m_alert;

    private final AutoModePattern m_autoModePattern;

    public DisabledPatterns(AddressableLEDBuffer buffer, int numberOfLEDs, Autos autoModeFactory) {
        m_autoModeFactory = autoModeFactory;

        m_alert = new AlertPatterns(buffer, numberOfLEDs / 2, numberOfLEDs / 2);
        m_autoModePattern = new AutoModePattern(buffer, 0, numberOfLEDs / 2);
    }

    public void writeAutoPattern() {
        Autos.AutoModes autoMode = m_autoModeFactory.autoModeLightSignal();
        m_autoModePattern.writeAutoModePattern(autoMode);
        m_alert.writeLEDs();
    }
}
