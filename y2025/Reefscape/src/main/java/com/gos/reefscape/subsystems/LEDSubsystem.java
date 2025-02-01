package com.gos.reefscape.subsystems;

import com.gos.reefscape.Constants;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.led_patterns.DisabledPatterns;
import com.gos.reefscape.led_patterns.EnabledPatterns;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LEDSubsystem extends SubsystemBase {
    private static final int MAX_INDEX_LED = 70;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final EnabledPatterns m_enabledPatterns;
    private final DisabledPatterns m_disabledPatterns;

    public LEDSubsystem(AlgaeSubsystem algae, CoralSubsystem coral, ElevatorSubsystem elevator, CombinedCommands combinedCommands) {
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT_ID);
        m_led.setLength(m_buffer.getLength());
        m_led.setData((m_buffer));
        m_led.start();

        m_enabledPatterns = new EnabledPatterns(m_buffer, MAX_INDEX_LED, coral, combinedCommands, algae);
        m_disabledPatterns = new DisabledPatterns(m_buffer, MAX_INDEX_LED, elevator);
    }

    @Override
    public void periodic() {
        clear();
        if (DriverStation.isEnabled()) {
            m_enabledPatterns.ledUpdates();
        }
        else {
            m_disabledPatterns.ledUpdates();
        }
        m_led.setData(m_buffer);


    }

    private void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }

    }

}


