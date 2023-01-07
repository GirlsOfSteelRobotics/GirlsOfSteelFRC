package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PunchSubsystemTest extends BaseTestFixture {

    @Test
    public void testExtensionAndRetraction() {

        try (PunchSubsystem punch = new PunchSubsystem()) {

            assertFalse(punch.isExtended());

            punch.extend();
            runCycles(3);
            assertTrue(punch.isExtended());

            punch.retract();
            runCycles(3);
            assertFalse(punch.isExtended());
        }
    }
}
