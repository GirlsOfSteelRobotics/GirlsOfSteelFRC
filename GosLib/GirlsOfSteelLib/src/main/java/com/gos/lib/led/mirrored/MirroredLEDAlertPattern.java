package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDAlertPatterns;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class MirroredLEDAlertPattern {

    private final LEDAlertPatterns m_normalStrip;
    private final LEDAlertPatterns m_invertedStrip;


    public MirroredLEDAlertPattern(AddressableLEDBuffer buffer, int minIndex, int numLights) {
        m_normalStrip = new LEDAlertPatterns(buffer, minIndex, numLights);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        m_invertedStrip = new LEDAlertPatterns(buffer, invertedMin, numLights);
    }

    public void writeLEDs() {
        m_normalStrip.writeLEDs();
        m_invertedStrip.writeLEDs();
    }
}
