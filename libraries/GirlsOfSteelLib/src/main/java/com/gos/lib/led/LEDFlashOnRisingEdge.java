package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class LEDFlashOnRisingEdge {
    private final LEDFlash m_hasPiecePattern;
    private final int m_cyclesToFlash;
    private int m_numCycles;
    private boolean m_hasPiece;

    public LEDFlashOnRisingEdge(AddressableLEDBuffer buffer, int minIndex, int maxIndex, double secondsOneDirection, int cyclesToFlash, Color color) {

        m_hasPiecePattern = new LEDFlash(buffer, minIndex, maxIndex, secondsOneDirection, color);
        m_numCycles = 0;
        m_cyclesToFlash = cyclesToFlash;
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
        if (m_hasPiece && m_numCycles <= m_cyclesToFlash) {
            m_hasPiecePattern.writeLeds();
        }
    }
}
