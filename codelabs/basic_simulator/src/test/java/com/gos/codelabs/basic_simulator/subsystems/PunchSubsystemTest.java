package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.BaseTestFixture;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PunchSubsystemTest extends BaseTestFixture {

    @Test
    public void testExtensionAndRetraction() {

        PunchSubsystem punch = new PunchSubsystem();

        assertFalse(punch.isExtended());

        punch.extend();
        runCycles(3);
        assertTrue(punch.isExtended());

        punch.retract();
        runCycles(3);
        assertFalse(punch.isExtended());


    }
}
