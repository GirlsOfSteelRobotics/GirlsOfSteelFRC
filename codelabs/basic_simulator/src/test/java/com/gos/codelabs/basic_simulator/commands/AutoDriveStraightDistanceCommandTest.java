package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AutoDriveStraightDistanceCommandTest extends BaseTestFixture {

    private void runTest(double goal) {
        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            AutoDriveStraightDistanceCommand command = new AutoDriveStraightDistanceCommand(chassis, goal);
            CommandScheduler.getInstance().schedule(command);

            runCycles(400, null, () -> !command.isScheduled());
            assertFalse(command.isScheduled());

            // Give the robot a little bit of time to come to a stop
            runCycles(25);
            assertEquals(goal, chassis.getAverageDistance(), AutoDriveStraightDistanceCommand.ALLOWABLE_ERROR);
        }
    }

    @Test
    public void testDriveForwards() {
        runTest(Units.feetToMeters(5));
    }

    @Test
    public void testDriveBackwards() {
        runTest(Units.feetToMeters(-3));
    }
}
