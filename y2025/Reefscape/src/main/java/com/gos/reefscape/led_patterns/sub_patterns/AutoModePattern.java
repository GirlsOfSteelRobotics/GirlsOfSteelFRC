package com.gos.reefscape.led_patterns.sub_patterns;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import com.gos.reefscape.auto.modes.GosAuto;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.StartingPositions;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.HashMap;
import java.util.Map;

public class AutoModePattern {
    private final LEDPatternLookup<StartingPositions> m_startPositionPattern;
    private final Map<CoralPositions, LEDPattern> m_coralBeingAcquiredPattern;

    public AutoModePattern(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        m_startPositionPattern = new LEDPatternLookup<>(buffer, createStartPosMap(buffer, startIndex + numLeds / 2, numLeds / 2));
        m_coralBeingAcquiredPattern = createAquiredNotesMap(buffer, startIndex, 12);


    }

    private Map<StartingPositions, LEDPattern> createStartPosMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<StartingPositions, LEDPattern> posMap = new HashMap<>();
        posMap.put(StartingPositions.CENTER, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kGoldenrod));
        posMap.put(StartingPositions.LEFT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kForestGreen));
        posMap.put(StartingPositions.RIGHT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kBlueViolet));
        return posMap;
    }

    private Map<CoralPositions, LEDPattern> createAquiredNotesMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        int ledsPerPosition = numLeds / 8;
        Map<CoralPositions, LEDPattern> autonMap = new HashMap<>();
        autonMap.put(CoralPositions.A, new LEDSolidColor(buffer, startIndex + 0 * ledsPerPosition, startIndex + 0 * ledsPerPosition + ledsPerPosition, Color.kViolet));
        autonMap.put(CoralPositions.B, new LEDSolidColor(buffer, startIndex + 1 * ledsPerPosition, startIndex + 1 * ledsPerPosition + ledsPerPosition, Color.kBlue));
        autonMap.put(CoralPositions.C, new LEDSolidColor(buffer, startIndex + 2 * ledsPerPosition, startIndex + 2 * ledsPerPosition + ledsPerPosition, Color.kGreen));
        autonMap.put(CoralPositions.D, new LEDSolidColor(buffer, startIndex + 3 * ledsPerPosition, startIndex + 3 * ledsPerPosition + ledsPerPosition, Color.kYellow));
        autonMap.put(CoralPositions.E, new LEDSolidColor(buffer, startIndex + 4 * ledsPerPosition, startIndex + 4 * ledsPerPosition + ledsPerPosition, Color.kOrange));
        autonMap.put(CoralPositions.F, new LEDSolidColor(buffer, startIndex + 5 * ledsPerPosition, startIndex + 5 * ledsPerPosition + ledsPerPosition, Color.kRed));
        autonMap.put(CoralPositions.G, new LEDSolidColor(buffer, startIndex + 6 * ledsPerPosition, startIndex + 6 * ledsPerPosition + ledsPerPosition, Color.kPink));
        autonMap.put(CoralPositions.H, new LEDSolidColor(buffer, startIndex + 7 * ledsPerPosition, startIndex + 7 * ledsPerPosition + ledsPerPosition, Color.kAliceBlue));
        autonMap.put(CoralPositions.I, new LEDSolidColor(buffer, startIndex + 8 * ledsPerPosition, startIndex + 8 * ledsPerPosition + ledsPerPosition, Color.kIndianRed));
        autonMap.put(CoralPositions.J, new LEDSolidColor(buffer, startIndex + 9 * ledsPerPosition, startIndex + 9 * ledsPerPosition + ledsPerPosition, Color.kHotPink));
        autonMap.put(CoralPositions.K, new LEDSolidColor(buffer, startIndex + 10 * ledsPerPosition, startIndex + 10 * ledsPerPosition + ledsPerPosition, Color.kAqua));
        autonMap.put(CoralPositions.L, new LEDSolidColor(buffer, startIndex + 11 * ledsPerPosition, startIndex + 11 * ledsPerPosition + ledsPerPosition, Color.kWhite));

        return autonMap;
    }

    public void writeAutoModePattern(GosAuto autoMode) {
        if (autoMode == null) {
            return;
        }
        m_startPositionPattern.setKey(autoMode.getStartingLocation());
        if (m_startPositionPattern.hasKey(autoMode.getStartingLocation())) {
            m_startPositionPattern.writeLeds();
        }

        for (CoralPositions coralPositions : autoMode.getCoralPositions()) {
            if (m_coralBeingAcquiredPattern.containsKey(coralPositions)) {
                m_coralBeingAcquiredPattern.get(coralPositions).writeLeds();
            }
        }
    }


}
