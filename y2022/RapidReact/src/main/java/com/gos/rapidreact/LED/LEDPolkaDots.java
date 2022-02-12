package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDPolkaDots extends LEDBase{
    private int m_minIndex;
    private int m_maxIndex;

    public LEDPolkaDots(AddressableLEDBuffer buffer, int minIndex, int maxIndex) {
        super(buffer);
        m_maxIndex = maxIndex;
        m_minIndex = minIndex;
    }

    public void polkaDots() {
        for (var i = m_minIndex; i < m_maxIndex; i += 2) {
            m_buffer.setRGB(i, 255, 0, 0);
        }
        for (var i = m_minIndex + 1; i < m_maxIndex; i += 2) {
            m_buffer.setRGB(i, 255, 255, 255);
        }
    }
}
