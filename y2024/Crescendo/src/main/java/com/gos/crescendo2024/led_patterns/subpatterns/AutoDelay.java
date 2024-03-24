package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.lib.led.LEDPercentScale;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AutoDelay {
    private final LEDPercentScale m_autoDelayLightUpLED;


    public AutoDelay(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        m_autoDelayLightUpLED = new LEDPercentScale(buffer, startIndex, startIndex + numLeds, Color.kPink, 5);

    }

    public void writeLeds() {
        m_autoDelayLightUpLED.distanceToTarget(Autos.AUTON_TIMEOUT.getValue());
        System.out.println(Autos.AUTON_TIMEOUT.getValue());
        m_autoDelayLightUpLED.writeLeds();
    }




}
