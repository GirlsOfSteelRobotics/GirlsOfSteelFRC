package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.crescendo2024.commands.DavidDriveSwerve;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.led.LEDBoolean;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class DavidDrivePattern {
    private final LEDBoolean m_davidDriveOn;
    private final boolean m_isDavidDrive;

    public DavidDrivePattern(AddressableLEDBuffer buffer, int startIndex, int numLEDs, ChassisSubsystem chassis) {
        m_davidDriveOn = new LEDBoolean(buffer, startIndex, startIndex + numLEDs, Color.kGreen, Color.kRed);
        m_isDavidDrive = chassis.getDefaultCommand() instanceof DavidDriveSwerve;
    }

    public void writeLED() {
        m_davidDriveOn.setStateAndWrite(m_isDavidDrive);
    }
}
