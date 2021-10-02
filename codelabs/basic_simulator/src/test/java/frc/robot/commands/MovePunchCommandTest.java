package frc.robot.commands;

import frc.robot.BaseTestFixture;
import frc.robot.subsystems.PunchSubsystem;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovePunchCommandTest  extends BaseTestFixture {

    @Test
    public void testExtension() {
        PunchSubsystem punch = new PunchSubsystem();
        MovePunchCommand command = new MovePunchCommand(punch, true);

        command.schedule();

        runCycles(5);
        assertTrue(punch.isExtended());
    }

    @Test
    public void testRetraction() {
        PunchSubsystem punch = new PunchSubsystem();
        MovePunchCommand command = new MovePunchCommand(punch, false);

        command.schedule();

        runCycles(5);
        assertFalse(punch.isExtended());
    }
}
