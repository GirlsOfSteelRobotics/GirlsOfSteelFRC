package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TurnToAngleCommandTest extends BaseTestFixture {

    private void runTest(double goal) {
        try (ChassisSubsystem chassis = new ChassisSubsystem()) {
            TurnToAngleCommand command = new TurnToAngleCommand(chassis, goal);
            CommandScheduler.getInstance().schedule(command);

            runCycles(400, null, () -> !command.isScheduled());

            // The command should have finished when the robot was facing the goal
            assertFalse(command.isScheduled());
            assertEquals(goal, chassis.getHeading(), TurnToAngleCommand.ALLOWABLE_ERROR);

            // Turning in place shouldn't move the robot forwards or backwards
            assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);

            // The robot will coast a little bit past the goal, but it should be slowing down to a stop.
            // If the motors were still running, it would turn many degrees in this amount of time
            runCycles(50);
            double settledHeading = chassis.getHeading();
            runCycles(10);
            assertEquals(settledHeading, chassis.getHeading(), TurnToAngleCommand.ALLOWABLE_ERROR);
        }
    }

    @Test
    public void testTurnCounterClockwise() {
        runTest(90);
    }

    @Test
    public void testTurnClockwise() {
        runTest(-45);
    }
}
