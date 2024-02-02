package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDFlash;
import com.gos.lib.led.LEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AutoPattern {
    private final LEDSolidColor m_autoPattern;

    public AutoPattern(int numberOfLEDs, AddressableLEDBuffer buffer) {
        m_autoPattern = new LEDSolidColor(buffer, 0, numberOfLEDs, Color.kCornflowerBlue);
    }
    public void writeAutoPattern() {m_autoPattern.writeLeds();}
}
