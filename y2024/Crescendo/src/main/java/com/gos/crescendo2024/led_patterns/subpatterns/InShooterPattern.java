package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.lib.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class InShooterPattern {
    private final LEDFlash m_inShooterPattern;
    private int m_numCycles;
    private boolean m_inShooter;

    public InShooterPattern(AddressableLEDBuffer buffer, int numberOfLEDs) {

        m_inShooterPattern = new LEDFlash(buffer, 0, numberOfLEDs, 0.25, Color.kGreen);
        m_numCycles = 0;
    }

    public void update(boolean inShooter) {
        m_inShooter = inShooter;
        if (m_inShooter) {
            m_numCycles++;
        } else {
            m_numCycles = 0;
        }

    }

    public void writeLeds() {
        if (m_inShooter && m_numCycles <= 100) {
            m_inShooterPattern.writeLeds();
        }
    }
}
