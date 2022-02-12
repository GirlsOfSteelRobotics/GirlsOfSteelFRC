package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDMovingPixel extends LEDBase{
    private int m_minIndex;
    private int m_maxIndex;
    private int m_pixelCounter;
    private int m_loopCounter = 0;
    private Color8Bit m_color;

    public LEDMovingPixel(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Color color) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_pixelCounter = minIndex; // start at min index
    }

    public void movingPixel() {
        m_buffer.setRGB(m_pixelCounter, m_color.red, m_color.green, m_color.blue);
        if (m_loopCounter % 5 == 0) {
            m_pixelCounter++;
        }

        if (m_pixelCounter >= m_maxIndex) {
            m_pixelCounter = m_minIndex;
        }
        m_loopCounter++;
    }
}
