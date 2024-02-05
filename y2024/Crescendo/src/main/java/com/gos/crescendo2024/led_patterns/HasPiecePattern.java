package com.gos.crescendo2024.led_patterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class HasPiecePattern {
    private final LEDFlash m_hasPiecePattern;
    private int m_numCycles;

    public HasPiecePattern(int numberOfLEDs, AddressableLEDBuffer buffer) {
        m_hasPiecePattern = new LEDFlash(buffer, 0 , numberOfLEDs, 0.5 , Color.kTomato);
        m_numCycles = 0;
    }
    public void writeHasPiecePattern() {
        if (m_numCycles <= 100){
            m_hasPiecePattern.writeLeds();
        }


    }
    public void incrementCycles() {
        m_numCycles = m_numCycles + 1;
    }
    public void resetCycles() {
        m_numCycles = 0;
    }
}


