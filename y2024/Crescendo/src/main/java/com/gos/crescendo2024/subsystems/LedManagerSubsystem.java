package com.gos.crescendo2024.subsystems;


import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.led_patterns.DisabledPatterns;
import com.gos.crescendo2024.led_patterns.EnabledPatterns;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedManagerSubsystem extends SubsystemBase {

    private static final int MAX_INDEX_LED = 30;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final EnabledPatterns m_enabledPatterns;
    private final DisabledPatterns m_disabledPatterns;


    public LedManagerSubsystem(IntakeSubsystem intakeSubsystem, Autos autoModeFactory) {
        // Setup LED's
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);
        m_led.setLength(m_buffer.getLength());
        m_led.setData(m_buffer);
        m_led.start();

        // Patterns
        m_enabledPatterns = new EnabledPatterns(m_buffer, MAX_INDEX_LED, intakeSubsystem);
        m_disabledPatterns = new DisabledPatterns(m_buffer, MAX_INDEX_LED, autoModeFactory);
    }


    @Override
    public void periodic() {
        clear();

        if (DriverStation.isEnabled()) {
            m_enabledPatterns.writeLED();
        } else {
            m_disabledPatterns.writeAutoPattern();
        }

        m_led.setData(m_buffer);
    }

    private void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }
}

