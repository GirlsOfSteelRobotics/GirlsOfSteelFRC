package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDBoolean extends LEDBase{
    private int m_startLED;
    private int m_endLED;
    private Color8Bit m_trueColor;
    private Color8Bit m_falseColor;

    public LEDBoolean(AddressableLEDBuffer buffer, int startLED, int endLED,
                      Color trueColor, Color falseColor) {
        super(buffer);
        m_startLED = startLED;
        m_endLED = endLED;
        m_trueColor = new Color8Bit(trueColor);
        m_falseColor = new Color8Bit(falseColor);
    }

    public void checkBoolean(boolean checkBoolean) {
        if(checkBoolean) {
            setLED(m_startLED, m_endLED, m_trueColor.red, m_trueColor.green, m_trueColor.blue);
        }
        if(!checkBoolean) {
            setLED(m_startLED, m_endLED, m_falseColor.red, m_falseColor.green, m_falseColor.blue);
        }
    }
}
