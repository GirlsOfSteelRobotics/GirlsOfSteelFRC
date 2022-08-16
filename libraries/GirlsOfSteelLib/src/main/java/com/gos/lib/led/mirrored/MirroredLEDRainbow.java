package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDRainbow;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class MirroredLEDRainbow implements LEDPattern {

    private final LEDRainbow m_normalStrip;
    private final LEDRainbow m_invertedStrip;

    public MirroredLEDRainbow(AddressableLEDBuffer buffer, int minIndex, int numLights) {
        m_normalStrip = new LEDRainbow(buffer, minIndex, minIndex + numLights);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDRainbow(buffer, invertedMin, invertedMax);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }
}
