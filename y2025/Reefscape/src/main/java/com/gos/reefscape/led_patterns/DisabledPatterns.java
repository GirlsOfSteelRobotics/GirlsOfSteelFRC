package com.gos.reefscape.led_patterns;

import com.gos.reefscape.Constants;
import com.gos.lib.led.LEDAlertPatterns;
import com.gos.lib.led.LEDBoolean;
import com.gos.lib.properties.GosBooleanProperty;
import com.gos.reefscape.commands.Autos;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

public class DisabledPatterns {
    private static final GosBooleanProperty ALWAYS_SHOW_LIGHTS = new GosBooleanProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Led: Always Run Disabled Pattern", true);
    private final ElevatorSubsystem m_elevator;
    private final LEDBoolean m_elevatorGood;
    private final LEDAlertPatterns m_alert;

    private final Autos m_autoModeFactory;
    private final AutoModePattern m_autoModePattern;


    public DisabledPatterns(AddressableLEDBuffer buffer, int numberOfLEDs, Autos autoModeFactory, ElevatorSubsystem elevatorSubsystem) {
        m_elevator = elevatorSubsystem;
        m_autoModeFactory = autoModeFactory;

        m_elevatorGood = new LEDBoolean(buffer, 24, 34, Color.kGreen, Color.kRed);
        //m_alert = new LEDAlertPatterns(buffer, numberOfLEDs / 2, numberOfLEDs / 2);

        int autoStart = 0;
        int autoCount = 24;

        int alertStart = 34;
        int alertCount = numberOfLEDs - alertStart;

        m_autoModePattern = new AutoModePattern(buffer, autoStart, autoCount);
        m_alert = new LEDAlertPatterns(buffer, alertStart, alertCount);





    }

    public void ledUpdates() {
        if (DriverStation.isFMSAttached() || ALWAYS_SHOW_LIGHTS.getValue()) {
            m_alert.writeLEDs();
            m_elevatorGood.setStateAndWrite(m_elevator.isAtBottom());
            m_autoModePattern.writeAutoModePattern(m_autoModeFactory.getSelectedAuto());
        }

    }

}
