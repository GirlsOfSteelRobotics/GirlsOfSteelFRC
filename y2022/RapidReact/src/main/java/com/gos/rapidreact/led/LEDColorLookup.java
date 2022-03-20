package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color8Bit;

import java.util.Map;

public class LEDColorLookup extends LEDBase {
    private final Map<Integer, Color8Bit> m_lookup;
    private final int m_minIndex;
    private final int m_maxIndex;

    public LEDColorLookup(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Map<Integer, Color8Bit> lookup) {
        super(buffer);
        m_lookup = lookup;
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
    }


    public boolean hasKey(int key) {
        return m_lookup.containsKey(key);
    }

    public void setKey(int key) {
        Color8Bit color = m_lookup.get(key);
        if (color == null) {
            return;
        }

        setLEDs(m_minIndex, m_maxIndex, color.red, color.green, color.blue);
    }
}
