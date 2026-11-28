package com.gos.rebuilt;

import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.power.PowerDistribution;
import org.junit.jupiter.api.Test;

public class RobotContainerTest {
    @Test
    public void testStartup() {
        new RobotContainer(new PowerDistribution(CANPort.CAN_S0));
    }
}
