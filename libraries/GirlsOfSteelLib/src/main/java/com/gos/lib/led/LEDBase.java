package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public abstract class LEDBase implements LEDPattern {
    protected final AddressableLEDBuffer m_buffer;

    public LEDBase(AddressableLEDBuffer buffer) {
        m_buffer = buffer;
    }

    public void setLEDs(int startLED, int endLED, int red, int green, int blue) {
        for (int i = startLED; i < endLED; i++) {
            m_buffer.setRGB(i, red, green, blue);
        }
    }

    public void setLEDs(int startLED, int endLED, Color8Bit color) {
        setLEDs(startLED, endLED, color.red, color.green, color.blue);
    }

    public void setLEDs(int startLED, int endLED, Color color) {
        setLEDs(startLED, endLED, new Color8Bit(color));
    }
}
