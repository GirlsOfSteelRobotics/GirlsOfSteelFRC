package com.gos.lib.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDAngleToTargetOverAndUnder extends LEDBase {
    private final Color8Bit m_underAngleColor;
    private final Color8Bit m_aboveAngleColor;

    private final int m_numLedsPerSide;
    private final int m_middleLED;
    private final double m_maxAngle;
    private double m_error;

    public LEDAngleToTargetOverAndUnder(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Color underAngleColor, Color aboveAngleColor, double maxAngle) {
        super(buffer);
        m_underAngleColor = new Color8Bit(underAngleColor);
        m_aboveAngleColor = new Color8Bit(aboveAngleColor);
        m_numLedsPerSide = (maxIndex - minIndex) / 2;
        m_middleLED = m_numLedsPerSide + minIndex;
        m_maxAngle = maxAngle;
    }

    public void angleToTarget(double angleError) {
        m_error = angleError;
    }

    public void setAngleAndWrite(double angleError) {
        angleToTarget(angleError);
        writeLeds();
    }

    @Override
    public void writeLeds() {
        double ledProportion = Math.abs(m_error / m_maxAngle);
        int ledOn = (int) (ledProportion * m_numLedsPerSide);
        if (ledOn > m_numLedsPerSide) {
            ledOn = m_numLedsPerSide;
        }

        int startLed;
        int endLed;
        Color8Bit color;

        if (m_error < 0) {
            startLed = m_middleLED - ledOn;
            endLed = m_middleLED;
            color = m_underAngleColor;
        } else {
            startLed = m_middleLED;
            endLed = m_middleLED + ledOn;
            color = m_aboveAngleColor;
        }

        setLEDs(startLed, endLed, color);
    }
}
