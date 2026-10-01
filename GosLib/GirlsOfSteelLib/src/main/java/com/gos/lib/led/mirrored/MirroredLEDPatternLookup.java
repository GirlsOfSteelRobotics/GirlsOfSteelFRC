package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.util.Map;

@SuppressWarnings("PMD.GenericsNaming")
public class MirroredLEDPatternLookup<KeyT> implements LEDPattern {

    private final LEDPatternLookup<KeyT> m_normalStrip;
    private final LEDPatternLookup<KeyT> m_invertedStrip;


    public MirroredLEDPatternLookup(AddressableLEDBuffer buffer, Map<KeyT, LEDPattern> lookup) {
        m_normalStrip = new LEDPatternLookup<>(buffer, lookup);
        m_invertedStrip = new LEDPatternLookup<>(buffer, lookup);
    }

    public boolean hasKey(KeyT key) {
        return m_normalStrip.hasKey(key);
    }

    public void setKey(KeyT key) {
        m_normalStrip.setKey(key);
        m_invertedStrip.setKey(key);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }
}
