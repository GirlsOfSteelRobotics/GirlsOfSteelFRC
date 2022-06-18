package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDMovingPixel;
import com.gos.lib.led.LEDPattern;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDMovingPixel implements LEDPattern {

    private final LEDMovingPixel m_normalStrip;
    private final LEDMovingPixel m_invertedStrip;

    public MirroredLEDMovingPixel(AddressableLEDBuffer buffer, int minIndex, int numLights, Color color) {
        m_normalStrip = new LEDMovingPixel(buffer, minIndex, minIndex + numLights, color);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDMovingPixel(buffer, invertedMin, invertedMax, color);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }

}
