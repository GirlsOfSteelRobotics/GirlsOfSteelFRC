package com.gos.reefscape.led_patterns;

import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.led_patterns.sub_patterns.KeepOutZoneStatePattern;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatterns {

    private final CoralSubsystem m_coralSubsystem;


    private final MirroredLEDBoolean m_hasCoral;
    private final MirroredLEDBoolean m_hasAlgae;
    private final KeepOutZoneStatePattern m_keepOutPattern;

    public EnabledPatterns(AddressableLEDBuffer buffer, CoralSubsystem coral) {
        m_coralSubsystem = coral;


        //change this
        m_hasAlgae = new MirroredLEDBoolean(buffer, 0, LEDSubsystem.SWIRL_START / 2, Color.kPink, Color.kRed);

        m_hasCoral = new MirroredLEDBoolean(buffer, LEDSubsystem.SWIRL_START / 2, LEDSubsystem.SWIRL_START / 2, Color.kGreen, Color.kRed);
        m_keepOutPattern = new KeepOutZoneStatePattern(buffer, LEDSubsystem.SWIRL_START, LEDSubsystem.SWIRL_COUNT);
    }

    public void ledUpdates() {
        m_hasAlgae.setStateAndWrite(m_coralSubsystem.hasAlgae());
        m_hasCoral.setStateAndWrite(m_coralSubsystem.hasCoral());
        m_keepOutPattern.writeLeds();
    }

    public void setKeepOutZoneState(KeepOutZoneEnum state) {
        m_keepOutPattern.setState(state);
    }
}


