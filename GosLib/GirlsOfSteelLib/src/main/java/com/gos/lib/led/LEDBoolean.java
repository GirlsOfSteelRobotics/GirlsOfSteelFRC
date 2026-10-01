package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDBoolean extends LEDBase {
    private final int m_minIndex;
    private final int m_maxIndex;
    private final Color8Bit m_trueColor;
    private final Color8Bit m_falseColor;
    private boolean m_state;

    public LEDBoolean(AddressableLEDBuffer buffer, int minIndex, int maxIndex,
                      Color trueColor, Color falseColor) {
        super(buffer);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_trueColor = new Color8Bit(trueColor);
        m_falseColor = new Color8Bit(falseColor);
    }

    public void setState(boolean state) {
        m_state = state;
    }

    public void setStateAndWrite(boolean state) {
        setState(state);
        writeLeds();
    }

    @Override
    public void writeLeds() {
        if (m_state) {
            setLEDs(m_minIndex, m_maxIndex, m_trueColor.red, m_trueColor.green, m_trueColor.blue);
        }
        else {
            setLEDs(m_minIndex, m_maxIndex, m_falseColor.red, m_falseColor.green, m_falseColor.blue);
        }
    }
}
