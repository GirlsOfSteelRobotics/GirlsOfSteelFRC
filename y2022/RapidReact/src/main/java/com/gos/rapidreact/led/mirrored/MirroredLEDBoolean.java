package com.gos.rapidreact.led.mirrored;

import com.gos.rapidreact.led.LEDBoolean;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDBoolean {

    private final LEDBoolean m_normalStrip;
    private final LEDBoolean m_invertedStrip;

    public MirroredLEDBoolean(AddressableLEDBuffer buffer, int minIndex, int numLights,
                              Color trueColor) {
        this(buffer, minIndex, numLights, trueColor, Color.kBlack);
    }

    public MirroredLEDBoolean(AddressableLEDBuffer buffer, int minIndex, int numLights,
                              Color trueColor, Color falseColor) {
        m_normalStrip = new LEDBoolean(buffer, minIndex, minIndex + numLights, trueColor, falseColor);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDBoolean(buffer, invertedMin, invertedMax, trueColor, falseColor);
    }

    public void checkBoolean(boolean checkBoolean) {
        m_normalStrip.checkBoolean(checkBoolean);
        m_invertedStrip.checkBoolean(checkBoolean);
    }
}
