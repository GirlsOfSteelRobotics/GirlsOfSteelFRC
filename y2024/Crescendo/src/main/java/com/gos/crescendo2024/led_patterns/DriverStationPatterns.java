package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.DriverStationLedDriver;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;

public class DriverStationPatterns {
    private static final DriverStationLedDriver.BitField IS_CONNECTED_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_0;
    private static final DriverStationLedDriver.BitField HAS_NOTE_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_3;
    private static final DriverStationLedDriver.BitField SEES_APRIL_TAGS_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_4;
    private static final DriverStationLedDriver.BitField IN_SHOOTING_POLYGON = DriverStationLedDriver.BitField.ARDUINO_BIT_5;
    private static final DriverStationLedDriver.BitField CANT_GO_UNDER_CHAIN = DriverStationLedDriver.BitField.ARDUINO_BIT_6;

    private final DriverStationLedDriver m_ledDriver;
    private final IntakeSubsystem m_intake;
    private final ChassisSubsystem m_chassis;
    private final ArmPivotSubsystem m_arm;

    public DriverStationPatterns(DriverStationLedDriver ledDriver, IntakeSubsystem intake, ChassisSubsystem chassis, ArmPivotSubsystem arm) {
        m_ledDriver = ledDriver;
        m_intake = intake;
        m_chassis = chassis;
        m_arm = arm;
    }

    public void writeLeds() {
        m_ledDriver.setBit(IS_CONNECTED_BIT, true);

        m_ledDriver.setBit(HAS_NOTE_BIT, m_intake.hasGamePiece());
        m_ledDriver.setBit(SEES_APRIL_TAGS_BIT, m_chassis.numAprilTagsSeen() > 0);
        m_ledDriver.setBit(IN_SHOOTING_POLYGON, m_chassis.inShootingPolygon());
        m_ledDriver.setBit(CANT_GO_UNDER_CHAIN, !m_arm.canGoUnderChain());
        m_ledDriver.write();
    }
}
