package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.BaseTestFixture;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AutoDriveStraightDistanceCommandTest  extends BaseTestFixture {

    @Test
    public void testExecution() {
        ChassisSubsystem chassis = new ChassisSubsystem();
        AutoDriveStraightDistanceCommand command = new AutoDriveStraightDistanceCommand(chassis, 20);
        command.schedule();

        runCycles(400);

        assertFalse(command.isScheduled());
        assertEquals(20, chassis.getAverageDistance(), AutoDriveStraightDistanceCommand.ALLOWABLE_ERROR);

    }
}
