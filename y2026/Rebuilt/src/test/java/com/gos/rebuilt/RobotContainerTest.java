package com.gos.rebuilt;

import edu.wpi.first.wpilibj.PowerDistribution;
import org.junit.jupiter.api.Test;

public class RobotContainerTest {
    @Test
    public void testStartup() {
        new RobotContainer(new PowerDistribution());
    }
}
