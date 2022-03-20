package com.gos.rapidreact.led.mirrored;

import com.gos.rapidreact.led.LEDRainbow;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class MirroredLEDRainbow {

    private final LEDRainbow m_normalStrip;
    private final LEDRainbow m_invertedStrip;

    public MirroredLEDRainbow(AddressableLEDBuffer buffer, int minIndex, int numLights) {
        m_normalStrip = new LEDRainbow(buffer, minIndex, minIndex + numLights);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDRainbow(buffer, invertedMin, invertedMax);
    }

    public void rainbow() {
        m_normalStrip.rainbow();
        m_invertedStrip.rainbow();
    }
}
