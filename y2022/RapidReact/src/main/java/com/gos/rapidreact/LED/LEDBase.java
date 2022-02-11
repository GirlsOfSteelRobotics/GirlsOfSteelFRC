package com.gos.rapidreact.LED;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDBase {
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    protected final int m_maxIndex;

    public LEDBase(int maxIndex, int port, AddressableLED led, AddressableLEDBuffer buffer) {
        m_maxIndex = maxIndex;
        m_led = led;
        m_buffer = buffer;

        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }

    public void setLED(int startLED, int endLED, int red, int green, int blue) {
        for (int i = startLED; i < endLED; i++) {
            m_buffer.setRGB(i, red, green, blue);
        }

    }
}
