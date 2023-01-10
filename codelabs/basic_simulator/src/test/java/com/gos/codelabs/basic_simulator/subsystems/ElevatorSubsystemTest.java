package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import edu.wpi.first.math.util.Units;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorSubsystemTest extends BaseTestFixture {

    @Test
    public void testManuallyMoveUp() {

        try (ElevatorSubsystem elevator = new ElevatorSubsystem()) {

            runCycles(50, () -> elevator.setSpeed(1));

            assertTrue(elevator.getHeight() > 0);
        }
    }

    @Test
    public void testManuallyMoveDown() {

        try (ElevatorSubsystem elevator = new ElevatorSubsystem()) {

            elevator.setSpeed(-1);
            runCycles(50); // Run for one second

            assertTrue(elevator.getHeight() < 0);
        }
    }

    @Test
    public void testGoToPosition() {
        try (ElevatorSubsystem elevator = new ElevatorSubsystem()) {
            double goal = Units.inchesToMeters(20);

            // When we start out at 0, we should not be finished
            assertFalse(elevator.goToPosition(goal));

            // Run it for two seconds to get it in place
            runCycles(100, () -> elevator.goToPosition(goal));

            // Check that we are at the correct height, and the subsystem says we are close enough to be considered finished
            assertEquals(goal, elevator.getHeight(), 2 * ElevatorSubsystem.ALLOWABLE_POSITION_ERROR);
            assertTrue(elevator.goToPosition(goal));
        }
    }
}
