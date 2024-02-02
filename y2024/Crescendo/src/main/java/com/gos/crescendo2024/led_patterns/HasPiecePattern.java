package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class HasPiecePattern {
    private final LEDFlash m_hasPiecePattern;

    public HasPiecePattern(int numberOfLEDs, AddressableLEDBuffer buffer) {
        m_hasPiecePattern = new LEDFlash(buffer, 0 , numberOfLEDs, 1 , Color.kTomato);

    }
    public void writeHasPiecePattern() {
        m_hasPiecePattern.writeLeds();
    }
}

