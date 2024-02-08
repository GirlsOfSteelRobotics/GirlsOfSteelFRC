package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.HashMap;
import java.util.Map;

public class AutoModePattern {
    private final LEDPatternLookup<Autos.AutoModes> m_autoModePattern;

    public AutoModePattern(AddressableLEDBuffer buffer, int maxIndexLED) {
        m_autoModePattern = new LEDPatternLookup<>(buffer, createAutonMap(buffer, maxIndexLED));
    }

    private Map<Autos.AutoModes, LEDPattern> createAutonMap(AddressableLEDBuffer buffer, int maxIndexLED) {
        Map<Autos.AutoModes, LEDPattern> autonMap = new HashMap<>();
        autonMap.put(Autos.AutoModes.LEAVE_WING, new MirroredLEDSolidColor(buffer, 0,  maxIndexLED, Color.kDenim));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_LEAVE_WING, new MirroredLEDFlash(buffer, 0,  maxIndexLED, 1.0, Color.kGreen));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_CENTER_LINE, new MirroredLEDFlash(buffer, 0,  maxIndexLED, 1.0, Color.kYellow));
        autonMap.put(Autos.AutoModes.FOUR_NOTE, new MirroredLEDFlash(buffer, 0,  maxIndexLED, 1.0, Color.kRed));
        return autonMap;
    }

    public void writeAutoModePattern(Autos.AutoModes autoMode) {
        m_autoModePattern.setKey(autoMode);
        if (m_autoModePattern.hasKey(autoMode)) {
            m_autoModePattern.writeLeds();
        }

    }

}
