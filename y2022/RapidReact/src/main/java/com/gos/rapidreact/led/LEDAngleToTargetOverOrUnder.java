package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDAngleToTargetOverOrUnder extends LEDBase {
    private final Color8Bit m_color;

    private final int m_minIndex;
    private final int m_maxIndex;
    private final int m_maxAngle;
    private final int m_numIndex;

    public LEDAngleToTargetOverOrUnder(AddressableLEDBuffer buffer, Color color, int minIndex, int maxIndex, int maxAngle) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_maxAngle = maxAngle;
        m_numIndex = m_maxIndex - m_minIndex + 1;
    }

    public void angleToTarget(double angleError) {
        if (angleError >= m_maxAngle) {
            setLEDs(m_minIndex, m_maxIndex, m_color.red, m_color.green, m_color.blue);
        }

        else {
            int ledProportion = (int) (Math.abs(angleError / m_maxAngle));
            int ledOn = ledProportion * m_numIndex;
            setLEDs(m_minIndex, m_minIndex + ledOn, m_color.red, m_color.green, m_color.blue);

        }

    }
}
