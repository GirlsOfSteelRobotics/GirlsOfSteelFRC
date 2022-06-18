package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.util.Map;

@SuppressWarnings("PMD.GenericsNaming")
public class LEDPatternLookup<KeyT> extends LEDBase {
    private final Map<KeyT, LEDPattern> m_lookup;
    private KeyT m_key;

    public LEDPatternLookup(AddressableLEDBuffer buffer, Map<KeyT, LEDPattern> lookup) {
        super(buffer);
        m_lookup = lookup;
    }


    public boolean hasKey(KeyT key) {
        return key != null && m_lookup.containsKey(key);
    }

    public void setKey(KeyT key) {
        m_key = key;
    }

    @Override
    public void writeLeds() {
        LEDPattern pattern = m_lookup.get(m_key);
        if (pattern == null) {
            return;
        }

        pattern.writeLeds();
    }
}
