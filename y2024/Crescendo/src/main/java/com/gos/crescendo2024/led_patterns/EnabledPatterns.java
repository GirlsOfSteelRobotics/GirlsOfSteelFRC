package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.led_patterns.subpatterns.HasPiecePattern;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.lib.led.LEDRainbow;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class EnabledPatterns {
    private final IntakeSubsystem m_intake;

    private final HasPiecePattern m_hasPiecePattern;

    public EnabledPatterns(AddressableLEDBuffer buffer, int numberOfLeds, IntakeSubsystem intake) {
        m_intake = intake;

        m_hasPiecePattern = new HasPiecePattern(buffer, numberOfLeds);
    }

    public void writeLED() {
        m_hasPiecePattern.update(m_intake.hasGamePiece());
        m_hasPiecePattern.writeLeds();
    }
}
