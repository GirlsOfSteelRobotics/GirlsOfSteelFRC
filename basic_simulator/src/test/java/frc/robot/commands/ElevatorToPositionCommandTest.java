package frc.robot.commands;

import frc.robot.BaseTestFixture;
import frc.robot.subsystems.ElevatorSubsystem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ElevatorToPositionCommandTest extends BaseTestFixture {

    @Test
    public void testExecution() {
        ElevatorSubsystem elevator = new ElevatorSubsystem();
        ElevatorToPositionCommand command = new ElevatorToPositionCommand(elevator, 20, false);
        command.schedule();

        runCycles(100, null, () -> !command.isScheduled());

        assertFalse(command.isScheduled());
        assertEquals(20, elevator.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 2);
    }
}
