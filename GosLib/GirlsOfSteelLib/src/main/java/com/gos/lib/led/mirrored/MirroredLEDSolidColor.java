package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDSolidColor implements LEDPattern {

    private final LEDSolidColor m_normalStrip;
    private final LEDSolidColor m_invertedStrip;

    public MirroredLEDSolidColor(AddressableLEDBuffer buffer, int minIndex, int numLights, Color color) {
        m_normalStrip = new LEDSolidColor(buffer, minIndex, minIndex + numLights, color);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDSolidColor(buffer, invertedMin, invertedMax, color);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }

}
