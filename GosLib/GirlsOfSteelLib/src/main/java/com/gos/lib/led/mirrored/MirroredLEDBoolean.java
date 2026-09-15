package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.LEDPattern;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDBoolean implements LEDPattern {

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

    public void setStateAndWrite(boolean state) {
        m_normalStrip.setStateAndWrite(state);
        m_invertedStrip.setStateAndWrite(state);
    }

    public void setState(boolean state) {
        m_normalStrip.setState(state);
        m_invertedStrip.setState(state);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }

}
