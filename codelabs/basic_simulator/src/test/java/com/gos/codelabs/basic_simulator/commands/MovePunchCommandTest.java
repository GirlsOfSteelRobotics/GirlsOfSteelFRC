package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovePunchCommandTest  extends BaseTestFixture {

    @Test
    public void testExtension() {
        try (PunchSubsystem punch = new PunchSubsystem()) {
            MovePunchCommand command = new MovePunchCommand(punch, true);

            command.schedule();

            runCycles(5);
            assertTrue(punch.isExtended());
        }
    }

    @Test
    public void testRetraction() {
        try (PunchSubsystem punch = new PunchSubsystem()) {
            MovePunchCommand command = new MovePunchCommand(punch, false);

            command.schedule();

            runCycles(5);
            assertFalse(punch.isExtended());
        }
    }
}
