package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.util.Map;

public class AutoModePattern {
    private final LEDPatternLookup<Autos.AutoModes> m_autoModePattern;

    public AutoModePattern(Map<Autos.AutoModes, LEDPattern> autonColorMap, AddressableLEDBuffer buffer) {
        m_autoModePattern = new LEDPatternLookup<>(buffer, autonColorMap);
    }
    public void writeAutoModePattern(Autos.AutoModes autoMode) {
        m_autoModePattern.setKey(autoMode);
        if (m_autoModePattern.hasKey(autoMode)) {
            m_autoModePattern.writeLeds();
        }

    }

}
