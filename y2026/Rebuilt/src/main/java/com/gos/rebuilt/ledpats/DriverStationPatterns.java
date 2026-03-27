package com.gos.rebuilt.ledpats;

import com.gos.lib.led.driverstation.DriverStationLedDriver;
import com.gos.rebuilt.MatchTime;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;

public class DriverStationPatterns {
    private static final DriverStationLedDriver.BitField IS_CONNECTED_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_0;
    private static final DriverStationLedDriver.BitField OUR_TURN_BIT = DriverStationLedDriver.BitField.ARDUINO_BIT_1;
    private static final DriverStationLedDriver.BitField GOOD_SHOOTING_PLACE = DriverStationLedDriver.BitField.ARDUINO_BIT_2;

    private final DriverStationLedDriver m_ledDriver;
    private final ChassisSubsystem m_chassis;
    private final ShooterSubsystem m_shooter;

    public DriverStationPatterns(DriverStationLedDriver ledDriver, ChassisSubsystem chassis, ShooterSubsystem shoota) {
        m_ledDriver = ledDriver;
        m_chassis = chassis;
        m_shooter = shoota;
    }

    public void writeLeds() {
        m_ledDriver.setBit(IS_CONNECTED_BIT, true);

        m_ledDriver.setBit(GOOD_SHOOTING_PLACE, m_chassis.getDistanceFromHub() > m_shooter.getMinDistance());
        m_ledDriver.setBit(OUR_TURN_BIT, MatchTime.shouldIShoot());
        m_ledDriver.setInt((int) MatchTime.timeLeft(), 10, 6);
        m_ledDriver.write();
    }
}
