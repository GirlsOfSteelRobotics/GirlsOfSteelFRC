package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDPolkaDots extends LEDBase {
    private final int m_minIndex;
    private final int m_maxIndex;

    public LEDPolkaDots(AddressableLEDBuffer buffer, int minIndex, int maxIndex) {
        super(buffer);
        m_maxIndex = maxIndex;
        m_minIndex = minIndex;
    }

    @Override
    public void writeLeds() {
        for (var i = m_minIndex; i < m_maxIndex; i += 2) {
            m_buffer.setRGB(i, 255, 0, 0);
        }
        for (var i = m_minIndex + 1; i < m_maxIndex; i += 2) {
            m_buffer.setRGB(i, 255, 255, 255);
        }
    }
}
