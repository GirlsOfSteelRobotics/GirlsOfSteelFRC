package com.gos.reefscape.subsystems;

import com.gos.reefscape.Constants;
import com.gos.reefscape.commands.Autos;
import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.led_patterns.DisabledPatterns;
import com.gos.reefscape.led_patterns.EnabledPatterns;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LEDSubsystem extends SubsystemBase {
    // 25 x2 Across the funnel
    // 10 x2 Down the funnel
    // ?     in the spiral
    private static final int MAX_INDEX_LED = 90 + 22 + 2;

    public static final int SWIRL_START = 38;
    public static final int SWIRL_COUNT = 20 + 22 + 2;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    private final EnabledPatterns m_enabledPatterns;
    private final DisabledPatterns m_disabledPatterns;

    public LEDSubsystem(CoralSubsystem coral, ElevatorSubsystem elevator, Autos autoModeFactory, ChassisSubsystem chassis) {
        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT_ID);
        m_led.setLength(m_buffer.getLength());
        m_led.setData((m_buffer));
        m_led.start();

        m_enabledPatterns = new EnabledPatterns(m_buffer, coral, chassis);
        m_disabledPatterns = new DisabledPatterns(m_buffer, autoModeFactory, elevator);
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

    public void setKeepOutZoneState(KeepOutZoneEnum state) {
        m_enabledPatterns.setKeepOutZoneState(state);
    }
}


