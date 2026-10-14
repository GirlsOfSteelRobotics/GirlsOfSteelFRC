package com.gos.codelabs.basic_simulator.auton_modes;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.commands.AutoDriveStraightDistanceCommand;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriveElevatePunchCommandGroupTest extends BaseTestFixture {

    @Test
    public void testAutonomousMode() {
        try (ChassisSubsystem chassis = new ChassisSubsystem();
             ElevatorSubsystem elevator = new ElevatorSubsystem();
             PunchSubsystem punch = new PunchSubsystem()) {
            DriveElevatePunchCommandGroup command = new DriveElevatePunchCommandGroup(chassis, elevator, punch);
            CommandScheduler.getInstance().schedule(command);

            // Give it 10 seconds to do everything
            runCycles(500, null, () -> !command.isScheduled());
            assertFalse(command.isScheduled());

            // It should have driven 5 feet, raised the elevator to the high position, and extended the punch
            assertEquals(Units.feetToMeters(5), chassis.getAverageDistance(), AutoDriveStraightDistanceCommand.ALLOWABLE_ERROR);
            assertEquals(ElevatorSubsystem.Positions.HIGH.m_heightMeters, elevator.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 2);
            assertTrue(punch.isExtended());
        }
    }
}
