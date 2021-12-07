package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.BaseTestFixture;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class ElevatorSubsystemTest extends BaseTestFixture {

    @Test
    public void testManuallyMoveUp() {

        ElevatorSubsystem elevator = new ElevatorSubsystem();

        runCycles(50, () -> elevator.setSpeed(1));

        assertTrue(elevator.getHeight() > 0);
    }

    @Test
    public void testManuallyMoveDown() {

        ElevatorSubsystem elevator = new ElevatorSubsystem();

        elevator.setSpeed(-1);
        runCycles(50); // Run for one second

        assertTrue(elevator.getHeight() < 0);
    }

    @Test
    public void testGoToPosition() {
        ElevatorSubsystem elevator = new ElevatorSubsystem();

        // When we start out at 0, we should not be finished
        assertFalse(elevator.goToPosition(20));

        // Run it for two seconds to get it in place
        runCycles(100, () -> elevator.goToPosition(20));

        // Check that we are at the correct height, and the subsystem says we are close enough to be considered finished
        assertEquals(20, elevator.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR);
        assertTrue(elevator.goToPosition(20));
    }
}
