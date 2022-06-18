package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDFlash;
import com.gos.lib.led.LEDPattern;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDFlash implements LEDPattern {

    private final LEDFlash m_normalStrip;
    private final LEDFlash m_invertedStrip;

    public MirroredLEDFlash(AddressableLEDBuffer buffer, int minIndex, int numLights, double secondsOneDirection, Color color) {
        m_normalStrip = new LEDFlash(buffer, minIndex, minIndex + numLights, secondsOneDirection, color);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDFlash(buffer, invertedMin, invertedMax, secondsOneDirection, color);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }
}
