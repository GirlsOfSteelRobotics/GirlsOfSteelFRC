package com.gos.reefscape.led_patterns;

import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatterns {

    private final CoralSubsystem m_coralSubsystem;
    private final CombinedCommands m_combinedCommands;


    private final MirroredLEDBoolean m_hasCoral;
    private final MirroredLEDBoolean m_hasAlgae;
    private final LEDBoolean m_heightAngleAtGoal;

    public EnabledPatterns(AddressableLEDBuffer buffer, CoralSubsystem coral, CombinedCommands combinedCommands) {
        m_coralSubsystem = coral;
        m_combinedCommands = combinedCommands;


        //change this
        m_hasAlgae = new MirroredLEDBoolean(buffer, 0, LEDSubsystem.SWIRL_START / 2, Color.kPink, Color.kRed);

        m_hasCoral = new MirroredLEDBoolean(buffer, LEDSubsystem.SWIRL_START / 2, LEDSubsystem.SWIRL_START / 2, Color.kGreen, Color.kRed);
        m_heightAngleAtGoal = new LEDBoolean(buffer, LEDSubsystem.SWIRL_START, LEDSubsystem.SWIRL_START + LEDSubsystem.SWIRL_COUNT, Color.kGreen, Color.kRed);

    }

    public void ledUpdates() {
        m_hasAlgae.setStateAndWrite(m_coralSubsystem.hasAlgae());
        m_hasCoral.setStateAndWrite(m_coralSubsystem.hasCoral());
        m_heightAngleAtGoal.setStateAndWrite(m_combinedCommands.isAtGoalHeightAngle());
    }
}


