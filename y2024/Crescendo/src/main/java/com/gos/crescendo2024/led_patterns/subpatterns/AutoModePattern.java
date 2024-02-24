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
    private final LEDPatternLookup<Autos.StartPosition> m_startPositionPattern;

    public AutoModePattern(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        m_autoModePattern = new LEDPatternLookup<>(buffer, createAutonMap(buffer, startIndex, numLeds / 2));
        m_startPositionPattern = new LEDPatternLookup<>(buffer, createStartPosMap(buffer, startIndex + numLeds / 2, numLeds / 2));
    }

    private Map<Autos.StartPosition, LEDPattern> createStartPosMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<Autos.StartPosition, LEDPattern> posMap = new HashMap<>();
        posMap.put(Autos.StartPosition.STARTING_LOCATION_BACK, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kPurple));
        posMap.put(Autos.StartPosition.STARTING_LOCATION_MIDDLE, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kCyan));
        posMap.put(Autos.StartPosition.STARTING_LOCATION_TOP, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kLightPink));
        posMap.put(Autos.StartPosition.CURRENT_LOCATION, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kDarkTurquoise));
        return posMap;
    }

    private Map<Autos.AutoModes, LEDPattern> createAutonMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<Autos.AutoModes, LEDPattern> autonMap = new HashMap<>();
        autonMap.put(Autos.AutoModes.LEAVE_WING, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kDenim));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_LEAVE_WING, new LEDFlash(buffer, startIndex, startIndex +  numLeds, 1.0, Color.kGreen));
        autonMap.put(Autos.AutoModes.FOUR_NOTE, new LEDFlash(buffer, startIndex, startIndex +   numLeds, 1.0, Color.kRed));
        autonMap.put(Autos.AutoModes.PRELOAD_AND_SHOOT, new LEDMovingPixel(buffer, startIndex, startIndex +  numLeds, Color.kWhite));
        autonMap.put(Autos.AutoModes.TWO_NOTE_MIDDLE_1, new LEDFlash(buffer, startIndex, startIndex + numLeds, 2.0, Color.kBlanchedAlmond));
        autonMap.put(Autos.AutoModes.TWO_NOTE_MIDDLE_2, new LEDFlash(buffer, startIndex, startIndex + numLeds, 2.0, Color.kPaleVioletRed));
        autonMap.put(Autos.AutoModes.TWO_NOTE_RIGHT_6, new LEDFlash(buffer, startIndex, startIndex + numLeds, 2.0, Color.kBrown));
        autonMap.put(Autos.AutoModes.TWO_NOTE_RIGHT_7, new LEDFlash(buffer, startIndex, startIndex + numLeds, 2.0, Color.kDarkGray));

        return autonMap;
    }

    public void writeAutoModePattern(Autos.AutoModes autoMode) {
        m_autoModePattern.setKey(autoMode);
        m_startPositionPattern.setKey(autoMode.m_location);
        if (m_autoModePattern.hasKey(autoMode)) {
            m_autoModePattern.writeLeds();
        }
        if (m_startPositionPattern.hasKey(autoMode.m_location)) {
            m_startPositionPattern.writeLeds();
        }
    }

}
