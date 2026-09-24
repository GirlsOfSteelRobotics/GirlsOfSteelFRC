package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDSolidColor extends LEDBase {
    private final int m_minIndex;
    private final int m_maxIndex;
    private final Color8Bit m_color;

    public LEDSolidColor(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Color color) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
    }

    @Override
    public void writeLeds() {
        setLEDs(m_minIndex, m_maxIndex, m_color.red, m_color.green, m_color.blue);
    }
}
