package com.gos.reefscape.led_patterns;

import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.LEDRainbow;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.CoralSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatterns {

    private final CoralSubsystem m_coralSubsystem;
    private final CombinedCommands m_combinedCommands;
    private final LEDRainbow m_rainbow;

    private final LEDBoolean m_hasCoral;
    private final LEDBoolean m_hasAlgae;
    private final LEDBoolean m_heightAngleAtGoal;

    public EnabledPatterns(AddressableLEDBuffer buffer, int numberOfLeds, CoralSubsystem coral, CombinedCommands combinedCommands) {
        m_coralSubsystem = coral;
        m_combinedCommands = combinedCommands;


        //change this
        m_hasAlgae = new LEDBoolean(buffer, 0, numberOfLeds / 2, Color.kGreen, Color.kRed);

        m_rainbow = new LEDRainbow(buffer, 0, numberOfLeds);
        m_hasCoral = new LEDBoolean(buffer, 0, numberOfLeds / 2, Color.kGreen, Color.kRed);
        m_heightAngleAtGoal = new LEDBoolean(buffer, numberOfLeds / 2, numberOfLeds, Color.kGreen, Color.kRed);

    }

    public void ledUpdates() {
        m_rainbow.writeLeds();
        m_hasAlgae.setStateAndWrite(m_coralSubsystem.hasAlgae());
        m_hasCoral.setStateAndWrite(m_coralSubsystem.hasCoral());
        m_heightAngleAtGoal.setStateAndWrite(m_combinedCommands.isAtGoalHeightAngle());
    }
}


