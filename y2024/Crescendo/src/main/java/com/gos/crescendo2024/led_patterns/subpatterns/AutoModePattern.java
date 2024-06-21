package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.HashMap;
import java.util.Map;

public class AutoModePattern {
    private final LEDPatternLookup<Autos.StartPosition> m_startPositionPattern;
    private final Map<Integer, LEDPattern> m_notesBeingAcquiredPattern;

    public AutoModePattern(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        m_notesBeingAcquiredPattern = createAquiredNotesMap(buffer, startIndex, numLeds / 2);
        m_startPositionPattern = new LEDPatternLookup<>(buffer, createStartPosMap(buffer, startIndex + numLeds / 2, numLeds / 2));
    }

    private Map<Autos.StartPosition, LEDPattern> createStartPosMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<Autos.StartPosition, LEDPattern> posMap = new HashMap<>();
        posMap.put(Autos.StartPosition.STARTING_LOCATION_SOURCE_SIDE, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kPurple));
        posMap.put(Autos.StartPosition.STARTING_LOCATION_MIDDLE, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kCyan));
        posMap.put(Autos.StartPosition.STARTING_LOCATION_AMP_SIDE, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kLightPink));
        posMap.put(Autos.StartPosition.CURRENT_LOCATION, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kDarkTurquoise));
        return posMap;
    }

    private Map<Integer, LEDPattern> createAquiredNotesMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        int ledsPerPosition = numLeds / 8;
        Map<Integer, LEDPattern> autonMap = new HashMap<>();
        autonMap.put(0, new LEDSolidColor(buffer, startIndex + 0 * ledsPerPosition, startIndex + 0 * ledsPerPosition + ledsPerPosition, Color.kViolet));
        autonMap.put(1, new LEDSolidColor(buffer, startIndex + 1 * ledsPerPosition, startIndex + 1 * ledsPerPosition + ledsPerPosition, Color.kBlue));
        autonMap.put(2, new LEDSolidColor(buffer, startIndex + 2 * ledsPerPosition, startIndex + 2 * ledsPerPosition + ledsPerPosition, Color.kGreen));
        autonMap.put(3, new LEDSolidColor(buffer, startIndex + 3 * ledsPerPosition, startIndex + 3 * ledsPerPosition + ledsPerPosition, Color.kYellow));
        autonMap.put(4, new LEDSolidColor(buffer, startIndex + 4 * ledsPerPosition, startIndex + 4 * ledsPerPosition + ledsPerPosition, Color.kOrange));
        autonMap.put(5, new LEDSolidColor(buffer, startIndex + 5 * ledsPerPosition, startIndex + 5 * ledsPerPosition + ledsPerPosition, Color.kRed));
        autonMap.put(6, new LEDSolidColor(buffer, startIndex + 6 * ledsPerPosition, startIndex + 6 * ledsPerPosition + ledsPerPosition, Color.kPink));
        autonMap.put(7, new LEDSolidColor(buffer, startIndex + 7 * ledsPerPosition, startIndex + 7 * ledsPerPosition + ledsPerPosition, Color.kAliceBlue));

        return autonMap;
    }

    public void writeAutoModePattern(Autos.AutoModes autoMode) {
        if (autoMode == null) {
            return;
        }
        m_startPositionPattern.setKey(autoMode.m_location);
        if (m_startPositionPattern.hasKey(autoMode.m_location)) {
            m_startPositionPattern.writeLeds();
        }

        for (int notePosition : autoMode.m_notes) {
            if (m_notesBeingAcquiredPattern.containsKey(notePosition)) {
                m_notesBeingAcquiredPattern.get(notePosition).writeLeds();
            }
        }
    }

}
