package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class HasPiecePattern {
    private final LEDFlash m_hasPiecePattern;
    private int m_numCycles;
    private boolean m_hasPiece;

    public HasPiecePattern(AddressableLEDBuffer buffer, int numberOfLEDs) {
        m_hasPiecePattern = new LEDFlash(buffer, 0, numberOfLEDs, 0.25, Color.kTomato);
        m_numCycles = 0;
    }

    public void update(boolean hasPiece) {
        m_hasPiece = hasPiece;
        if (m_hasPiece) {
            m_numCycles++;
        } else {
            m_numCycles = 0;
        }

    }

    public void writeLeds() {
        if (m_hasPiece && m_numCycles <= 100) {
            m_hasPiecePattern.writeLeds();
        }
    }
}


