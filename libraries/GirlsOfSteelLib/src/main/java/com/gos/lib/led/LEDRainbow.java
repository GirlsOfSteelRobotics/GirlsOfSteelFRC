package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDRainbow extends LEDBase {
    private final int m_indexLength;
    private final int m_minIndex;
    private final int m_maxIndex;
    private int m_rainbowFirstPixelHue;


    public LEDRainbow(AddressableLEDBuffer buffer, int minIndex, int maxIndex) {
        super(buffer);
        m_indexLength = maxIndex - minIndex;
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
    }

    @Override
    public void writeLeds() {
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
