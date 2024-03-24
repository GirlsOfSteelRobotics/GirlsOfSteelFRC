package com.gos.crescendo2024.led_patterns.subpatterns;


import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.lib.led.LEDBoolean;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AreEncodersGoodPattern {
    private final ArmPivotSubsystem m_arm;

    private final LEDBoolean m_areEncodersGoodPattern;

    public AreEncodersGoodPattern(AddressableLEDBuffer buffer, int startIndex, int numLeds, ArmPivotSubsystem arm) {
        m_arm = arm;
        m_areEncodersGoodPattern = new LEDBoolean(buffer, startIndex, startIndex + numLeds, Color.kAliceBlue, Color.kRed);
    }

    public void writeLeds() {
        double relativePosition = m_arm.getRelativeEncoderAngle();
        double absolutePosition = m_arm.getAbsoluteEncoderAngle();

        boolean isGood = Math.abs(relativePosition - absolutePosition) < 1;
        m_areEncodersGoodPattern.setStateAndWrite(isGood);
    }
}
