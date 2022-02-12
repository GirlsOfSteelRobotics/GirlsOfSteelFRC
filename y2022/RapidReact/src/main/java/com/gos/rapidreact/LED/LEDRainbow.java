package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDRainbow extends LEDBase{
    private int m_indexLength;
    private int m_rainbowFirstPixelHue;
    private int m_minIndex;
    private int m_maxIndex;

    public LEDRainbow(int maxIndex, AddressableLEDBuffer buffer, int minIndex) {
        super(buffer);
        m_indexLength = maxIndex - minIndex;
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
    }

    public void rainbow() {
        // For every pixel
        for (var i = m_minIndex; i < m_maxIndex; i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_indexLength)) % 180;
            // Set the value
            m_buffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;
    }
}
