package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.Constants;
import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.led_patterns.subpatterns.AlertPatterns;
import com.gos.crescendo2024.led_patterns.subpatterns.AutoModePattern;
import com.gos.crescendo2024.led_patterns.subpatterns.DavidDrivePattern;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosBooleanProperty;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;

public class DisabledPatterns {
    private static final GosBooleanProperty ALWAYS_SHOW_LIGHTS = new GosBooleanProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Led: Always Run Disabled Pattern", true);

    private final Autos m_autoModeFactory;
    private final AlertPatterns m_alert;
    private final DavidDrivePattern m_davidDriveOn;
    private final AutoModePattern m_autoModePattern;

    public DisabledPatterns(AddressableLEDBuffer buffer, int numberOfLEDs, Autos autoModeFactory, ChassisSubsystem chassis) {
        m_autoModeFactory = autoModeFactory;

        int davidDriveCount = 10;
        int autoCount = (int) (numberOfLEDs * 0.70 - davidDriveCount) + 1;
        int alertCount = numberOfLEDs - autoCount - davidDriveCount;

        int autoStart = davidDriveCount;
        int alertStart = autoStart + autoCount;

        m_davidDriveOn = new DavidDrivePattern(buffer, 0, davidDriveCount, chassis);
        m_autoModePattern = new AutoModePattern(buffer, autoStart, autoCount);
        m_alert = new AlertPatterns(buffer, alertStart, alertCount);
    }

    public void writeAutoPattern() {
        // Only write the lights when we are connected the FMS, or if we have the override set for home testing
        if (DriverStation.isFMSAttached() || ALWAYS_SHOW_LIGHTS.getValue()) {
            Autos.AutoModes autoMode = m_autoModeFactory.autoModeLightSignal();
            m_autoModePattern.writeAutoModePattern(autoMode);
            m_alert.writeLEDs();
            m_davidDriveOn.writeLED();
        }
    }

}
