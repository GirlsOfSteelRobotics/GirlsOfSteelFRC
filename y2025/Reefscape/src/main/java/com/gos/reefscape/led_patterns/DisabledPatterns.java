package com.gos.reefscape.led_patterns;

import com.gos.lib.led.mirrored.MirroredLEDAlertPattern;
import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.reefscape.Constants;
import com.gos.lib.properties.GosBooleanProperty;
import com.gos.reefscape.commands.Autos;
import com.gos.reefscape.led_patterns.sub_patterns.AutoModePattern;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

public class DisabledPatterns {
    private static final GosBooleanProperty ALWAYS_SHOW_LIGHTS = new GosBooleanProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Led: Always Run Disabled Pattern", false);
    private final ElevatorSubsystem m_elevator;
    private final MirroredLEDBoolean m_elevatorGood;
    private final MirroredLEDAlertPattern m_alert;

    private final Autos m_autoModeFactory;
    private final AutoModePattern m_autoModePattern;


    public DisabledPatterns(AddressableLEDBuffer buffer, Autos autoModeFactory, ElevatorSubsystem elevatorSubsystem) {
        m_elevator = elevatorSubsystem;
        m_autoModeFactory = autoModeFactory;

        m_elevatorGood = new MirroredLEDBoolean(buffer, 0, LEDSubsystem.SWIRL_START / 2, Color.kPink, Color.kRed);
        m_alert = new MirroredLEDAlertPattern(buffer, LEDSubsystem.SWIRL_START / 2, LEDSubsystem.SWIRL_START / 2);
        //m_alert = new LEDAlertPatterns(buffer, numberOfLEDs / 2, numberOfLEDs / 2);

        m_autoModePattern = new AutoModePattern(buffer, LEDSubsystem.SWIRL_START, LEDSubsystem.SWIRL_COUNT);

    }

    public void ledUpdates() {
        if (DriverStation.isFMSAttached() || ALWAYS_SHOW_LIGHTS.getValue()) {
            m_alert.writeLEDs();
            m_elevatorGood.setStateAndWrite(m_elevator.isAtBottom());
            m_autoModePattern.writeAutoModePattern(m_autoModeFactory.getSelectedAuto());
        }

    }

}
