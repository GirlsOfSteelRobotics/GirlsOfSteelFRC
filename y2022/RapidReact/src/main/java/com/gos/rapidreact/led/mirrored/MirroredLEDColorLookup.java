package com.gos.rapidreact.led.mirrored;

import com.gos.rapidreact.led.LEDColorLookup;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color8Bit;

import java.util.Map;

public class MirroredLEDColorLookup {

    private final LEDColorLookup m_normalStrip;
    private final LEDColorLookup m_invertedStrip;


    public MirroredLEDColorLookup(AddressableLEDBuffer buffer, int minIndex, int numLights, Map<Integer, Color8Bit> lookup) {
        m_normalStrip = new LEDColorLookup(buffer, minIndex, minIndex + numLights, lookup);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        System.out.println(minIndex + ", " + (minIndex + numLights) + ", " + invertedMin + ", " + invertedMax);
        m_invertedStrip = new LEDColorLookup(buffer, invertedMin, invertedMax, lookup);
    }

    public boolean hasKey(int key) {
        return m_normalStrip.hasKey(key);
    }

    public void setKey(int key) {
        m_normalStrip.setKey(key);
        m_invertedStrip.setKey(key);
    }
}
