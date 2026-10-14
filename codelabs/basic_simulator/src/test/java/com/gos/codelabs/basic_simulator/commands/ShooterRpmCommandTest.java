package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShooterRpmCommandTest extends BaseTestFixture {

    @Test
    public void testSpinUpAndStop() {
        final double goal = 2500;

        try (ShooterSubsystem shooter = new ShooterSubsystem()) {
            ShooterRpmCommand command = new ShooterRpmCommand(shooter, goal);
            CommandScheduler.getInstance().schedule(command);

            // Give it two seconds to get up to speed
            runCycles(100);
            assertTrue(command.isScheduled());
            assertEquals(goal, shooter.getRpm(), ShooterSubsystem.ALLOWABLE_RPM_ERROR);

            // Once the command is cancelled, the wheel should coast down
            command.cancel();
            runCycles(50);
            assertTrue(shooter.getRpm() < goal / 2);
        }
    }
}
