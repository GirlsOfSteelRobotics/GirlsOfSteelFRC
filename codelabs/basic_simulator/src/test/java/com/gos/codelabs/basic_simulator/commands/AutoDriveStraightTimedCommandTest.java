package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.BaseTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutoDriveStraightTimedCommandTest  extends BaseTestFixture {

    @Test
    public void testDriveForwards() {
        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            AutoDriveStraightTimedCommand command = new AutoDriveStraightTimedCommand(chassis, .7, 1.5);
            command.schedule();

            runCycles(100);
            assertTrue(chassis.getAverageDistance() > 0);
            assertFalse(command.isScheduled());
        }
    }

    @Test
    public void testDriveReverse() {
        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            AutoDriveStraightTimedCommand command = new AutoDriveStraightTimedCommand(chassis, -.5, 1.5);
            command.schedule();

            runCycles(100);
            assertTrue(chassis.getAverageDistance() < 0);
            assertFalse(command.isScheduled());
        }
    }
}
