package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDRainbow extends LEDBase{
    private int m_indexLength;

    public LEDRainbow(int maxIndex, int port, AddressableLED led, AddressableLEDBuffer buffer, int minIndex, int maxIndex) {
        super(maxIndex, port, led, buffer);
        m_indexLength = maxIndex - minIndex;
    }
    public void rainbow() {
        // For every pixel
        for (var i = 0; i < m_buffer.getLength(); i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
            // Set the value
            m_ledBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;
    }
}
