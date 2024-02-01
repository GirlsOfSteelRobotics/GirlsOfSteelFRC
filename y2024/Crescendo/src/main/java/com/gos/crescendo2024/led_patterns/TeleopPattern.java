package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDRainbow;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class TeleopPattern {

    private final LEDRainbow m_rainbowPattern;

    public TeleopPattern(int numberOfLeds, AddressableLEDBuffer buffer) {

        m_rainbowPattern = new LEDRainbow(buffer, 0, numberOfLeds);

    }

    public void writeLED() {

        m_rainbowPattern.writeLeds();
    }
}
