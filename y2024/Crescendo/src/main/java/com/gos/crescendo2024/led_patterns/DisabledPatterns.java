package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.auton.Autos;
import com.gos.crescendo2024.led_patterns.subpatterns.AlertPatterns;
import com.gos.crescendo2024.led_patterns.subpatterns.AreEncodersGoodPattern;
import com.gos.crescendo2024.led_patterns.subpatterns.AutoDelay;
import com.gos.crescendo2024.led_patterns.subpatterns.AutoModePattern;
import com.gos.crescendo2024.led_patterns.subpatterns.DavidDrivePattern;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosBooleanProperty;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;

public class DisabledPatterns {
    private static final GosBooleanProperty ALWAYS_SHOW_LIGHTS = new GosBooleanProperty(false, "Led: Always Run Disabled Pattern", true);

    private final Autos m_autoModeFactory;
    private final AlertPatterns m_alert;
    private final DavidDrivePattern m_davidDriveOn;
    private final AutoModePattern m_autoModePattern;
    private final AreEncodersGoodPattern m_armEncodersPattern;
    private final AutoDelay m_autoDelayPattern;

    public DisabledPatterns(AddressableLEDBuffer buffer, Autos autoModeFactory, ChassisSubsystem chassis, ArmPivotSubsystem arm) {
        m_autoModeFactory = autoModeFactory;

        int davidDriveCount = 10;
        int autoCount = 30;
        int alertCount = 10;
        int encoderCount = 10;
        int autoDelayCount = 10;

        int autoStart = davidDriveCount;
        int alertStart = autoStart + autoCount;
        int encoderStart = alertStart + alertCount;
        int autoDelayStart = encoderStart + encoderCount;

        m_davidDriveOn = new DavidDrivePattern(buffer, 0, davidDriveCount, chassis);
        m_autoModePattern = new AutoModePattern(buffer, autoStart, autoCount);
        m_alert = new AlertPatterns(buffer, alertStart, alertCount);
        m_armEncodersPattern = new AreEncodersGoodPattern(buffer, encoderStart, encoderCount, arm);
        m_autoDelayPattern = new AutoDelay(buffer, autoDelayStart, autoDelayCount);
    }

    public void writeAutoPattern() {
        // Only write the lights when we are connected the FMS, or if we have the override set for home testing
        if (DriverStation.isFMSAttached() || ALWAYS_SHOW_LIGHTS.getValue()) {
            Autos.AutoModes autoMode = m_autoModeFactory.autoModeLightSignal();
            m_autoModePattern.writeAutoModePattern(autoMode);
            m_alert.writeLEDs();
            m_davidDriveOn.writeLED();
            m_armEncodersPattern.writeLeds();
            m_autoDelayPattern.writeLeds();
        }
    }

}
