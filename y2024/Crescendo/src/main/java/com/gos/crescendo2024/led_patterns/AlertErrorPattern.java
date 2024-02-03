package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.nio.Buffer;

public class AlertErrorPattern {
    private final LEDFlash m_alertErrorPattern;

    public AlertErrorPattern(int numLEDs, AddressableLEDBuffer buffer) {
        m_alertErrorPattern = new LEDFlash(buffer, 0, numLEDs, 2, Color.kYellow)
    }

    public void writeAlertErrorPatter() { m_alertErrorPattern.writeLeds(); }

}
