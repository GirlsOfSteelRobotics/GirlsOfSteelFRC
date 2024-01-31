package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDRainbow;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Teleop {

    private final LEDRainbow m_RainbowPattern;

    public Teleop (int numberOfLeds, AddressableLEDBuffer buffer) {

        m_RainbowPattern = new LEDRainbow(buffer, 0, numberOfLeds);

    }

    public void writeLED() {

        m_RainbowPattern.writeLeds();
    }
}