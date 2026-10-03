package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutoDriveStraightTimedCommandTest  extends BaseTestFixture {

    @Test
    public void testDriveForwards() {
        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            AutoDriveStraightTimedCommand command = new AutoDriveStraightTimedCommand(chassis, .7, 1.5);
            CommandScheduler.getInstance().schedule(command);

            runCycles(100);
            assertTrue(chassis.getAverageDistance() > 0);
            assertFalse(command.isScheduled());
        }
    }

    @Test
    public void testDriveReverse() {
        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            AutoDriveStraightTimedCommand command = new AutoDriveStraightTimedCommand(chassis, -.5, 1.5);
            CommandScheduler.getInstance().schedule(command);

            runCycles(100);
            assertTrue(chassis.getAverageDistance() < 0);
            assertFalse(command.isScheduled());
        }
    }
}
