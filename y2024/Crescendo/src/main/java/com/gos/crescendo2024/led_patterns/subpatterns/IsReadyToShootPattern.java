package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.lib.led.LEDPercentScale;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class IsReadyToShootPattern {
    private final LEDPercentScale m_isReadyToShootPattern;

    public IsReadyToShootPattern(AddressableLEDBuffer buffer, int minIndex, int maxIndex, Color color, double maxDistance){
       m_isReadyToShootPattern = new LEDPercentScale (buffer, minIndex, maxIndex, color, maxDistance);
    }
}
