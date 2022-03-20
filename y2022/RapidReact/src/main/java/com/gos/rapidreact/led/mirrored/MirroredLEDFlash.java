package com.gos.rapidreact.led.mirrored;

import com.gos.rapidreact.led.LEDFlash;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDFlash {

    private final LEDFlash m_normalStrip;
    private final LEDFlash m_invertedStrip;

    public MirroredLEDFlash(AddressableLEDBuffer buffer, int minIndex, int numLights, double secondsOneDirection, Color color) {
        m_normalStrip = new LEDFlash(buffer, minIndex, minIndex + numLights, secondsOneDirection, color);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDFlash(buffer, invertedMin, invertedMax, secondsOneDirection, color);
    }

    public void flash() {
        m_normalStrip.flash();
        m_invertedStrip.flash();
    }
}
