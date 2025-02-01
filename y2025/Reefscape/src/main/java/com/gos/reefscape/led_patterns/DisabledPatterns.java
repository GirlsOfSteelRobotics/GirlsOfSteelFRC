package com.gos.reefscape.led_patterns;

import com.gos.reefscape.Constants;
import com.gos.lib.led.LEDAlertPatterns;
import com.gos.lib.led.LEDBoolean;
import com.gos.lib.properties.GosBooleanProperty;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

public class DisabledPatterns {
    private static final GosBooleanProperty ALWAYS_SHOW_LIGHTS = new GosBooleanProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Led: Always Run Disabled Pattern", true);
    private final ElevatorSubsystem m_elevator;
    private final LEDBoolean m_elevatorGood;
    private final LEDAlertPatterns m_alert;


    public DisabledPatterns(AddressableLEDBuffer buffer, int numberOfLEDs, ElevatorSubsystem elevatorSubsystem) {
        m_elevator = elevatorSubsystem;

        m_elevatorGood = new LEDBoolean(buffer, 0, numberOfLEDs / 2, Color.kGreen, Color.kRed);
        m_alert = new LEDAlertPatterns(buffer, numberOfLEDs / 2, numberOfLEDs / 2);

    }

    public void ledUpdates() {
        if (DriverStation.isFMSAttached() || ALWAYS_SHOW_LIGHTS.getValue()) {
            m_alert.writeLEDs();
            m_elevatorGood.setStateAndWrite(m_elevator.isAtBottom());
        }

    }

}
