package com.gos.crescendo2024;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverStationLedDriverTest {

    @Test
    public void testStuff() {
        HAL.initialize(0, 0);

        int port = 2;
        DriverStationLedDriver driver = new DriverStationLedDriver(port);

        driver.clear();
        driver.setData((short) 0xFFFF);

//        driver.setBit(DriverStationLedDriver.BitField.BIT_0, true);
//        driver.setBit(DriverStationLedDriver.BitField.BIT_1, true);
//        driver.setBit(DriverStationLedDriver.BitField.BIT_2, true);
//        driver.setBit(DriverStationLedDriver.BitField.BIT_15, true);


//        driver.setBit(DriverStationLedDriver.BitField.BIT_0, false);
//        driver.setBit(DriverStationLedDriver.BitField.BIT_1, false);
//        driver.setBit(DriverStationLedDriver.BitField.BIT_2, false);
//        driver.setBit(DriverStationLedDriver.BitField.BIT_3, false);


        driver.write();

        int rumbleRight = DriverStationSim.getJoystickRumble(port, GenericHID.RumbleType.kRightRumble.ordinal());
        int rumbleLeft = DriverStationSim.getJoystickRumble(port, GenericHID.RumbleType.kLeftRumble.ordinal());

        System.out.println(String.format("0x%04X", rumbleRight) + " " + String.format("0x%04X", rumbleLeft));

        assertEquals(1, rumbleRight);
        assertEquals(2, rumbleLeft);

    }
}
