package com.gos.reefscape.led_patterns;

import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.LEDRainbow;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatterns {

    private final IntakeSubsystem m_intake;
    private final CombinedCommands m_combinedCommands;
    private final LEDRainbow m_rainbow;

    private final LEDBoolean m_hasCoral;
    private final LEDBoolean m_heightAngleAtGoal;

    public EnabledPatterns(AddressableLEDBuffer buffer, int numberOfLeds, IntakeSubsystem intake, CombinedCommands combinedCommands) {
        m_intake = intake;
        m_combinedCommands = combinedCommands;


        m_rainbow = new LEDRainbow(buffer, 0, numberOfLeds);
        m_hasCoral = new LEDBoolean(buffer, 0, numberOfLeds / 2, Color.kGreen, Color.kRed);
        m_heightAngleAtGoal = new LEDBoolean(buffer, numberOfLeds / 2, numberOfLeds, Color.kGreen, Color.kRed);

    }

    public void ledUpdates() {
        m_rainbow.writeLeds();

        m_hasCoral.setStateAndWrite(m_intake.hasCoral());
        m_heightAngleAtGoal.setStateAndWrite(m_combinedCommands.isAtGoalHeightAngle());
    }
}


