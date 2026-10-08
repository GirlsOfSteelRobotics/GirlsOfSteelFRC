package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPercentScale;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDPercentScale implements LEDPattern {
    private final LEDPercentScale m_normalStrip;
    private final LEDPercentScale m_invertedStrip;


    public MirroredLEDPercentScale(AddressableLEDBuffer buffer, int minIndex, int numLights, Color color, double maxDistance) {
        m_normalStrip = new LEDPercentScale(buffer, minIndex, minIndex + numLights, color, maxDistance);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDPercentScale(buffer, invertedMin, invertedMax, color, maxDistance);
    }

    public void distanceToTarget(double distance) {
        m_normalStrip.distanceToTarget(distance);
        m_invertedStrip.distanceToTarget(distance);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_invertedStrip.writeLeds();
    }
}
