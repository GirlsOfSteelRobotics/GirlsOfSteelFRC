package com.gos.lib.led.mirrored;

import com.gos.lib.led.LEDDistanceToTarget;
import com.gos.lib.led.LEDPattern;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class MirroredLEDDistanceToTarget implements LEDPattern {
    private final LEDDistanceToTarget m_normalStrip;
    private final LEDDistanceToTarget m_invertedStrip;


    public MirroredLEDDistanceToTarget(AddressableLEDBuffer buffer, int minIndex, int numLights, Color color, double maxDistance) {
        m_normalStrip = new LEDDistanceToTarget(buffer, minIndex, minIndex + numLights, color, maxDistance);

        int invertedMin = buffer.getLength() - numLights - minIndex;
        int invertedMax = buffer.getLength() - minIndex;
        m_invertedStrip = new LEDDistanceToTarget(buffer, invertedMin, invertedMax, color, maxDistance);
    }

    public void distanceToTarget(double distance) {
        m_normalStrip.distanceToTarget(distance);
        m_invertedStrip.distanceToTarget(distance);
    }

    @Override
    public void writeLeds() {
        m_normalStrip.writeLeds();
        m_normalStrip.writeLeds();
    }
}
