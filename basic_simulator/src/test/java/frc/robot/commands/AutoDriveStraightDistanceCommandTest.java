package frc.robot.commands;

import frc.robot.BaseTestFixture;
import frc.robot.subsystems.ChassisSubsystem;
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
