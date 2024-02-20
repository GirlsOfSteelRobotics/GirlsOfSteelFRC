package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.lib.led.LEDFlash;
import com.gos.lib.led.LEDMovingPixel;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.HashMap;
import java.util.Map;

public class AutoModePattern {
    private final LEDPatternLookup<Autos.AutoModes> m_autoModePattern;

    public AutoModePattern(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        m_autoModePattern = new LEDPatternLookup<>(buffer, createAutonMap(buffer, startIndex, numLeds));
    }

    private Map<Autos.AutoModes, LEDPattern> createAutonMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<Autos.AutoModes, LEDPattern> autonMap = new HashMap<>();
        autonMap.put(Autos.AutoModes.LEAVE_WING, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kDenim));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_LEAVE_WING, new LEDFlash(buffer, startIndex, startIndex +  numLeds, 1.0, Color.kGreen));
        autonMap.put(Autos.AutoModes.FOUR_NOTE, new LEDFlash(buffer, startIndex, startIndex +   numLeds, 1.0, Color.kRed));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_SHOOT, new LEDMovingPixel(buffer, startIndex, startIndex +  numLeds, Color.kWhite));
        return autonMap;
    }

    public void writeAutoModePattern(Autos.AutoModes autoMode) {
        m_autoModePattern.setKey(autoMode);
        if (m_autoModePattern.hasKey(autoMode)) {
            m_autoModePattern.writeLeds();
        }

    }

}
