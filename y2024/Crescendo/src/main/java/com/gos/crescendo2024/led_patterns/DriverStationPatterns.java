package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.DriverStationLedDriver;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;

public class DriverStationPatterns {
    private static final DriverStationLedDriver.BitField IS_CONNECTED_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_0;
    private static final DriverStationLedDriver.BitField HAS_NOTE_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_3;
    private static final DriverStationLedDriver.BitField SEES_APRIL_TAGS_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_4;
    private static final DriverStationLedDriver.BitField IN_SHOOTING_POLYGON = DriverStationLedDriver.BitField.ARDUINO_BIT_5;

    private final DriverStationLedDriver m_ledDriver;
    private final IntakeSubsystem m_intake;
    private final ChassisSubsystem m_chassis;

    public DriverStationPatterns(DriverStationLedDriver ledDriver, IntakeSubsystem intake, ChassisSubsystem chassis) {
        m_ledDriver = ledDriver;
        m_intake = intake;
        m_chassis = chassis;
    }

    public void writeLeds() {
        m_ledDriver.setBit(IS_CONNECTED_BIT, true);

        m_ledDriver.setBit(HAS_NOTE_BIT, m_intake.hasGamePiece());
        m_ledDriver.setBit(SEES_APRIL_TAGS_BIT, m_chassis.numAprilTagsSeen() > 0);
        m_ledDriver.setBit(IN_SHOOTING_POLYGON, m_chassis.inShootingPolygon());
        m_ledDriver.write();
    }
}
