package com.gos.rapidreact.led;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class LEDAngleToTargetOverAndUnder extends LEDBase {

    private final Color8Bit m_color;

    private final int m_minIndex;
    private final int m_maxIndex;
    private final int m_maxAngle;
    private final int m_numIndex;
    private final int m_middleLED;

    public LEDAngleToTargetOverOrUnder(AddressableLEDBuffer buffer, Color color, int minIndex, int maxIndex, int maxAngle) {
        super(buffer);
        m_color = new Color8Bit(color);
        m_minIndex = minIndex;
        m_maxIndex = maxIndex;
        m_maxAngle = maxAngle;
        m_numIndex = m_maxIndex - m_minIndex + 1;
    }

    public LEDAngleToTargetOverAndUnder(double angleError) {
        int ledProportion = (int) (Math.abs(angleError / m_maxAngle));
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
            setLEDs(differenceInLED, m_middleLED, m_color.red, m_color.green, m_color.blue);
        }
        if (differenceInLED > m_middleLED) {
            setLEDs(m_middleLED, differenceInLED, m_aboveAngleColor.red, m_aboveAngleColor.green, m_aboveAngleColor.blue);
        }

    }
}