package com.gos.reefscape.led_patterns;

import com.gos.lib.led.LEDRainbow;
import com.gos.reefscape.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class EnabledPatterns {

    private final IntakeSubsystem m_intake;

    //patternssss
    private final LEDRainbow m_rainbow;

    public EnabledPatterns(AddressableLEDBuffer buffer, int numberOfLeds, IntakeSubsystem intake) {
        m_intake = intake;

        m_rainbow = new LEDRainbow(buffer, 0, numberOfLeds);
    }

    public void writeLED() {
        m_rainbow.writeLeds();
        if (m_intake.hasCoral()) {
            System.out.println("Has coral");
        }
    }
}
