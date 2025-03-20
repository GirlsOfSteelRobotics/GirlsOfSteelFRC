package com.gos.reefscape.led_patterns;

import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.lib.led.mirrored.MirroredLEDMovingPixel;
import com.gos.lib.led.mirrored.MirroredLEDRainbow;
import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.led_patterns.sub_patterns.KeepOutZoneStatePattern;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatterns {

    private final CoralSubsystem m_coralSubsystem;


    private final MirroredLEDBoolean m_hasCoral;
    private final MirroredLEDBoolean m_hasAlgae;
    private final KeepOutZoneStatePattern m_keepOutPattern;
    private final ChassisSubsystem m_chassis;
    private final MirroredLEDRainbow m_relative;
    private final MirroredLEDMovingPixel m_driveToPose;

    public EnabledPatterns(AddressableLEDBuffer buffer, CoralSubsystem coral, ChassisSubsystem driveToPose) {
        m_coralSubsystem = coral;


        //change this
        m_hasAlgae = new MirroredLEDBoolean(buffer, 0, LEDSubsystem.SWIRL_START / 2, Color.kPink, Color.kRed);

        m_hasCoral = new MirroredLEDBoolean(buffer, LEDSubsystem.SWIRL_START / 2, LEDSubsystem.SWIRL_START / 2, Color.kGreen, Color.kRed);
        m_keepOutPattern = new KeepOutZoneStatePattern(buffer, LEDSubsystem.SWIRL_START, LEDSubsystem.SWIRL_COUNT);

        m_relative = new MirroredLEDRainbow(buffer, 0, LEDSubsystem.SWIRL_START);
        m_driveToPose = new MirroredLEDMovingPixel(buffer, 0, LEDSubsystem.SWIRL_START, Color.kBlue);


        m_chassis = driveToPose;

    }

    public void ledUpdates() {
        if(m_chassis.getRelative()) {
            m_relative.writeLeds();
        } else if (m_chassis.getDriveToPose()){
            m_driveToPose.writeLeds();
        } else {
            m_hasAlgae.setStateAndWrite(m_coralSubsystem.hasAlgae());
            m_hasCoral.setStateAndWrite(m_coralSubsystem.hasCoral());
        }
        m_keepOutPattern.writeLeds();
    }

    public void setKeepOutZoneState(KeepOutZoneEnum state) {
        m_keepOutPattern.setState(state);
    }
}


