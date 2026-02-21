package com.gos.rebuilt.ledpats;

import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import com.gos.rebuilt.autos.GosAuto;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.GainBalls;
import com.gos.rebuilt.enums.StartingPositions;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.HashMap;
import java.util.Map;

public class AutoModePattern {

    private final LEDPatternLookup<StartingPositions> m_startPositionPattern;
    private final LEDPatternLookup<AutoActions> m_autoActionPattern;
    private final LEDPatternLookup<GainBalls> m_gainBallsPattern;

    public AutoModePattern (AddressableLEDBuffer buffer, int startIndex, int numLeds) {


        m_startPositionPattern = new LEDPatternLookup<>(buffer, createStartPosMap(buffer,startIndex, 2));
        m_gainBallsPattern = new LEDPatternLookup<>(buffer, creategainBallsMap(buffer, 54, 2));
        m_autoActionPattern = new LEDPatternLookup<>(buffer, createAutoActions(buffer, 57,2));

    }

    private Map<StartingPositions, LEDPattern> createStartPosMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<StartingPositions, LEDPattern> posMap = new HashMap<>();
        posMap.put(StartingPositions.CENTER, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kLawnGreen));
        posMap.put(StartingPositions.LEFT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kIndianRed));
        posMap.put(StartingPositions.RIGHT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kBlueViolet));
        //rainbow order from L to r! 🌈
        return posMap;
    }

    private Map<GainBalls, LEDPattern> creategainBallsMap(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<GainBalls, LEDPattern> posMap = new HashMap<>();
        posMap.put(GainBalls.Depot, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kDarkBlue));
        posMap.put(GainBalls.Outpost, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kOrange));
        posMap.put(GainBalls.Preload, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kPink));
        //(D)ark blue for (D)epot
        // (O)range for (O)utpost
        //(P)ink for (P)reload
        return posMap;
    }

    private Map<AutoActions, LEDPattern> createAutoActions(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<AutoActions, LEDPattern> posMap = new HashMap<>();
        posMap.put(AutoActions.Shoot, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kSilver));
        posMap.put(AutoActions.ShootClimb, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kGold));
        //silver for shoot, gold if we climb as well!!!
        return posMap;
    }

    public void writeAutoModePattern(GosAuto autoMode) {
        if (autoMode == null) {
            return;
        }
        m_startPositionPattern.setKey(autoMode.getStartingLocation());
        if (m_startPositionPattern.hasKey(autoMode.getStartingLocation())) {
            m_startPositionPattern.writeLeds();
        }

        m_autoActionPattern.setKey(autoMode.getAutoActions());
        if (m_autoActionPattern.hasKey(autoMode.getAutoActions())) {
            m_autoActionPattern.writeLeds();
        }

        m_gainBallsPattern.setKey(autoMode.getGainBalls());
        if (m_gainBallsPattern.hasKey(autoMode.getGainBalls())) {
            m_gainBallsPattern.writeLeds();
        }
    }
}
