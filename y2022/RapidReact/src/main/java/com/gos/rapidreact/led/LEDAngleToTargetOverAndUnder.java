package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDAngleToTargetOverAndUnder extends LEDBase {
    private final Color8Bit m_underAngleColor;
    private final Color8Bit m_aboveAngleColor;

    private final int m_minIndex;
    private final int m_maxIndex;
    private final int m_middleLED;
    private final int m_maxAngle;

    public LEDAngleToTargetOverAndUnder(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Color underAngleColor, Color aboveAngleColor, int maxAngle) {
        super(buffer);
        m_underAngleColor = new Color8Bit(underAngleColor);
        m_aboveAngleColor = new Color8Bit(aboveAngleColor);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_middleLED = (m_maxIndex - m_minIndex) / 2 + m_minIndex;
        m_maxAngle = maxAngle;
    }

    public void angleToTarget(double angleError) {
        double ledProportion = Math.abs(angleError / m_maxAngle);
        int ledOn = (int) (ledProportion * m_middleLED);
        if (ledOn > m_middleLED) {
            ledOn = m_middleLED;
        }
        int differenceInLED = m_middleLED;
        if (angleError < 0) {
            differenceInLED = m_middleLED - ledOn;
        }
        if (angleError > 0) {
            differenceInLED = m_middleLED + ledOn;
        }
        if (differenceInLED < m_middleLED) {
            setLEDs(differenceInLED, m_middleLED, m_underAngleColor.red, m_underAngleColor.green, m_underAngleColor.blue);
        }
        if (differenceInLED > m_middleLED) {
            setLEDs(m_middleLED, differenceInLED, m_aboveAngleColor.red, m_aboveAngleColor.green, m_aboveAngleColor.blue);
        }
    }
}
