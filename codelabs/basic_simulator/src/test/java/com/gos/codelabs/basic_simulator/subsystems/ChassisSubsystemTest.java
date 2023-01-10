package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChassisSubsystemTest extends BaseTestFixture {

    private static final int LOOPS_TO_RUN = 50; // one second

    @Test
    public void testDriveForwards() {

        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            chassis.arcadeDrive(1, 0);

            // Run for one second
            runCycles(LOOPS_TO_RUN);

            assertTrue(chassis.getLeftDistance() > 0);
            assertTrue(chassis.getRightDistance() > 0);
            assertTrue(chassis.getAverageDistance() > 0);
            assertEquals(chassis.getLeftDistance(), chassis.getRightDistance(), DOUBLE_EPSILON);
            assertEquals(0, chassis.getHeading(), DOUBLE_EPSILON);
        }
    }

    @Test
    public void testDriveBackwards() {

        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            chassis.arcadeDrive(-1, 0);

            // Run for one second
            runCycles(LOOPS_TO_RUN);

            assertTrue(chassis.getLeftDistance() < 0);
            assertTrue(chassis.getRightDistance() < 0);
            assertTrue(chassis.getAverageDistance() < 0);
            assertEquals(chassis.getLeftDistance(), chassis.getRightDistance(), DOUBLE_EPSILON);
            assertEquals(0, chassis.getHeading(), DOUBLE_EPSILON);
        }
    }


    @Test
    public void testTurnClockwise() {

        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            chassis.arcadeDrive(0, 1);

            // Run for one second
            runCycles(LOOPS_TO_RUN);

            // Clockwise means the right side goes backwards, left goes forwards
            assertTrue(chassis.getLeftDistance() > 0);
            assertTrue(chassis.getRightDistance() < 0);
            assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
            assertEquals(-chassis.getLeftDistance(), chassis.getRightDistance(), DOUBLE_EPSILON);
            assertTrue(chassis.getHeading() > 0);
        }
    }

    @Test
    public void testTurnCounterClockwise() {

        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            chassis.arcadeDrive(0, -1);

            // Run for one second
            runCycles(LOOPS_TO_RUN);

            // Clockwise means the left side goes backwards, right goes forwards
            assertTrue(chassis.getLeftDistance() < 0);
            assertTrue(chassis.getRightDistance() > 0);
            assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
            assertEquals(-chassis.getLeftDistance(), chassis.getRightDistance(), DOUBLE_EPSILON);
            assertTrue(chassis.getHeading() < 0);
        }
    }

}
