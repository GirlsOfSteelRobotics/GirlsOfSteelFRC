package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDBase {
    protected final AddressableLEDBuffer m_buffer;

    public LEDBase(AddressableLEDBuffer buffer) {
        m_buffer = buffer;
    }

    public void setLEDs(int startLED, int endLED, int red, int green, int blue) {
        for (int i = startLED; i < endLED; i++) {
            m_buffer.setRGB(i, red, green, blue);
        }

    }
}
