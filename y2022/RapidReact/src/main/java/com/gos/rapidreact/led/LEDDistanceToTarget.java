package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDDistanceToTarget extends LEDBase {
    private final Color8Bit m_color;

    private final int m_minIndex;
    private final int m_maxIndex;
    private final int m_maxDistance;

    public LEDDistanceToTarget(AddressableLEDBuffer buffer, Color color, int minIndex, int maxIndex, int maxDistance) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_maxDistance = maxDistance;
    }

    public void distanceToTarget(double distanceError) {
        double ledProportion = Math.abs(distanceError / m_maxDistance);
        int ledOn = (int) (ledProportion * m_maxIndex);
        setLEDs(m_minIndex, m_minIndex + ledOn, m_color.red, m_color.green, m_color.blue);
    }
}
