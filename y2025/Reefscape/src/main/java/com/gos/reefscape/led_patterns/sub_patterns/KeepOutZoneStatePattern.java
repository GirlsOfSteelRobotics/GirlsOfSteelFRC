package com.gos.reefscape.led_patterns.sub_patterns;

import com.gos.lib.led.LEDFlash;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import com.gos.reefscape.enums.KeepOutZoneEnum;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.Map;

public class KeepOutZoneStatePattern {
    private final LEDPatternLookup<KeepOutZoneEnum> m_patterns;

    public KeepOutZoneStatePattern(AddressableLEDBuffer buffer, int startIndex, int numLeds) {
        Map<KeepOutZoneEnum, LEDPattern> lookup = Map.of(
            KeepOutZoneEnum.IS_AT_POSITION, new LEDFlash(buffer, startIndex, startIndex + numLeds, 0.5, Color.kGreen),
            KeepOutZoneEnum.CAN_MOVE_BOTH, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kGreen),
            KeepOutZoneEnum.ONLY_MOVE_ELEVATOR, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kYellow),
            KeepOutZoneEnum.CAN_MOVE_BOTH_WITH_MODIFIED_ELEVATOR_GOAL, new LEDFlash(buffer, startIndex, startIndex + numLeds, 0.25, Color.kYellow),
            KeepOutZoneEnum.ONLY_MOVE_PIVOT, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kBlue),
            KeepOutZoneEnum.CAN_MOVE_BOTH_WITH_MODIFIED_PIVOT_GOAL, new LEDFlash(buffer, startIndex, startIndex + numLeds, 0.25, Color.kBlue),
            KeepOutZoneEnum.NOT_RUNNING, new LEDSolidColor(buffer, startIndex, startIndex + numLeds, Color.kBlack)
        );
        m_patterns = new LEDPatternLookup<>(buffer, lookup);
    }

    public void setState(KeepOutZoneEnum state) {
        m_patterns.setKey(state);
    }

    public void writeLeds() {
        m_patterns.writeLeds();
    }
}
