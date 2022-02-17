package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDFlash extends LEDBase {
    private double m_brightness;
    private double m_increment;
    private final int m_minIndex;
    private final int m_maxIndex;
    private final Color8Bit m_color;

    public LEDFlash(AddressableLEDBuffer buffer, double secondsOneDirection, Color color, int minIndex, int maxIndex) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_increment = secondsOneDirection / (50 * secondsOneDirection);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
    }

    public void flash() {
        setLEDs(m_minIndex, m_maxIndex, (int) (m_color.red * m_brightness), (int) (m_color.green * m_brightness), (int) (m_color.blue * m_brightness));
        m_brightness += m_increment;
        if (m_brightness >= 1) {
            m_increment *= -1.0;
        }
        if (m_brightness <= 0) {
            m_increment *= -1.0;
        }

    }
}
