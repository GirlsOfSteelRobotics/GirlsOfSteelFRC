package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.BaseTestFixture;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AutoDriveStraightTimedCommandTest  extends BaseTestFixture {

    @Test
    public void testDriveForwards() {
        ChassisSubsystem chassis = new ChassisSubsystem();
        AutoDriveStraightTimedCommand command = new AutoDriveStraightTimedCommand(chassis, .7, 1.5);
        command.schedule();

        runCycles(100);
        assertTrue(chassis.getAverageDistance() > 0);
        assertFalse(command.isScheduled());
    }

    @Test
    public void testDriveReverse() {
        ChassisSubsystem chassis = new ChassisSubsystem();
        AutoDriveStraightTimedCommand command = new AutoDriveStraightTimedCommand(chassis, -.5, 1.5);
        command.schedule();

        runCycles(100);
        assertTrue(chassis.getAverageDistance() < 0);
        assertFalse(command.isScheduled());
    }
}
