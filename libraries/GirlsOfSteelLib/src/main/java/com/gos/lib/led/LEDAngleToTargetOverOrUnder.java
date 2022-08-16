package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDAngleToTargetOverOrUnder extends LEDBase {
    private final Color8Bit m_color;

    private final int m_minIndex;
    private final int m_maxIndex;
    private final double m_maxAngle;
    private final int m_numIndex;
    private double m_error;

    public LEDAngleToTargetOverOrUnder(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Color color, double maxAngle) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_maxAngle = maxAngle;
        m_numIndex = m_maxIndex - m_minIndex + 1;
    }

    public void angleToTarget(double angleError) {
        m_error = angleError;
    }

    @Override
    public void writeLeds() {
        if (m_error >= m_maxAngle || m_error <= (m_maxAngle * -1)) {
            setLEDs(m_minIndex, m_maxIndex, m_color.red, m_color.green, m_color.blue);
        }

        else {
            double ledProportion = Math.abs(m_error / m_maxAngle);
            int ledOn = (int) (ledProportion * m_numIndex);
            setLEDs(m_minIndex, m_minIndex + ledOn, m_color.red, m_color.green, m_color.blue);
        }

    }
}
