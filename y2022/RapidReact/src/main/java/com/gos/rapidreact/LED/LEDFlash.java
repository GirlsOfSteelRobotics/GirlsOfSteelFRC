package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDFlash extends LEDBase {
    private double m_brightness;
    private double m_increment;
    private int m_startLED;
    private int m_endLED;
    private Color8Bit m_color;

    public LEDFlash(int maxIndex, int port, AddressableLED led, AddressableLEDBuffer buffer, double secondsOneDirection, Color color, int startLED, int endLED) {
        super(maxIndex, port, led, buffer);
        m_color = new Color8Bit(color);
        m_increment = secondsOneDirection / (50 * secondsOneDirection);
        m_startLED = startLED;
        m_endLED = endLED;
    }

    public void flash() {
        setLED(m_startLED, m_endLED,(int) (m_color.red * m_brightness) , (int) (m_color.green * m_brightness), (int) (m_color.blue * m_brightness));
        m_brightness += m_increment;
        if (m_brightness >= 1) {
            m_increment *= -1.0;
        }
        if (m_brightness <= 0) {
            m_increment *= -1.0;
        }

    }
}
