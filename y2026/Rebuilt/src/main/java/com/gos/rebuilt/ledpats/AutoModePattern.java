package com.gos.rebuilt.ledpats;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import com.gos.rebuilt.autos.GosAuto;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import org.wpilib.hardware.led.AddressableLEDBuffer;
import org.wpilib.util.Color;

import java.util.HashMap;
import java.util.Map;

public class AutoModePattern {

    private final LEDPatternLookup<StartingPositions> m_startPositionPattern;
    private final Map<AutoActions, LEDPattern> m_autoActionPattern;

    public AutoModePattern(AddressableLEDBuffer buffer, int startIndex, int numLeds) {


        m_startPositionPattern = new LEDPatternLookup<>(buffer, createStartPosMap(buffer, numLeds, numLeds / 2));
        m_autoActionPattern = createAutoActions(buffer, startIndex + numLeds / 2, numLeds / 2);

    }

    private Map<StartingPositions, LEDPattern> createStartPosMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<StartingPositions, LEDPattern> posMap = new HashMap<>();
        posMap.put(StartingPositions.CENTER, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.LAWN_GREEN));
        posMap.put(StartingPositions.LEFT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.INDIAN_RED));
        posMap.put(StartingPositions.RIGHT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.BLUE_VIOLET));
        //rainbow order from L to r! 🌈
        return posMap;
    }


    private Map<AutoActions, LEDPattern> createAutoActions(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        int ledsPerPosition = numLeds / 7;
        Map<AutoActions, LEDPattern> autonMap = new HashMap<>();

        autonMap.put(AutoActions.OUTPOST, new LEDSolidColor(buffer, startIndex + 0 * ledsPerPosition, startIndex + 0 * ledsPerPosition + ledsPerPosition, Color.RED));
        autonMap.put(AutoActions.DEPOT, new LEDSolidColor(buffer, startIndex + 1 * ledsPerPosition, startIndex + 1 * ledsPerPosition + ledsPerPosition, Color.ORANGE));
        autonMap.put(AutoActions.PRELOAD, new LEDSolidColor(buffer, startIndex + 2 * ledsPerPosition, startIndex + 2 * ledsPerPosition + ledsPerPosition, Color.YELLOW));
        autonMap.put(AutoActions.CROSSBUMP, new LEDSolidColor(buffer, startIndex + 3 * ledsPerPosition, startIndex + 3 * ledsPerPosition + ledsPerPosition, Color.GREEN));
        autonMap.put(AutoActions.CROSSTHETRENCH, new LEDSolidColor(buffer, startIndex + 4 * ledsPerPosition, startIndex + 4 * ledsPerPosition + ledsPerPosition, Color.ALICE_BLUE));
        autonMap.put(AutoActions.SHOOOT, new LEDSolidColor(buffer, startIndex + 5 * ledsPerPosition, startIndex + 5 * ledsPerPosition + ledsPerPosition, Color.DARK_BLUE));
        autonMap.put(AutoActions.CLIMB, new LEDSolidColor(buffer, startIndex + 6 * ledsPerPosition, startIndex + 6 * ledsPerPosition + ledsPerPosition, Color.PURPLE));

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
        for (AutoActions auto : autoMode.getAutoActions()) {
            if (m_autoActionPattern.containsKey(auto)) {
                m_autoActionPattern.get(auto).writeLeds();
            }
        }

    }
}
