package frc.robot;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PunchSubsystem;
import org.junit.Test;

import static org.junit.Assert.*;

public class OITest extends BaseTestFixture {

    @Test
    public void testPunchButtons() {
        RobotContainer container = new RobotContainer();
        PunchSubsystem punch = container.getPunch();

        assertFalse(punch.isExtended());

        // Press the button, check that it extends
        runCycles(2, () -> {
            int buttonMask = new ButtonMaskBuilder().addButtonPressed(XboxController.Button.kA.value).build();

            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, new float[5], new short[1], 10, buttonMask);
        });
        assertTrue(punch.isExtended());

        // Release the button and make sure it goes back
        runCycles(2, () -> {
            int buttonMask = 0;

            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, new float[5], new short[1], 10, buttonMask);
        });
        assertFalse(punch.isExtended());
    }

    @Test
    public void testElevatorButtons() {

        final int LOOPS_TO_RUN = 400; // Give more than enough time for it to get where it needs to go

        RobotContainer container = new RobotContainer();
        ElevatorSubsystem lift = container.getElevator();

        assertEquals(0, lift.getHeight(), DOUBLE_EPSILON);

        // Press the button, check that it goes to the mid height
        runCycles(LOOPS_TO_RUN, () -> {
            int buttonMask = new ButtonMaskBuilder().addButtonPressed(XboxController.Button.kY.value).build();

            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, new float[5], new short[1], 10, buttonMask);
        });
        assertEquals(40, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR);

        // Press the button, check that it goes to the low height
        runCycles(LOOPS_TO_RUN, () -> {
            int buttonMask = new ButtonMaskBuilder().addButtonPressed(XboxController.Button.kB.value).build();

            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, new float[5], new short[1], 10, buttonMask);
        });
        assertEquals(0, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR);

        // Press the button, check that it goes to the high height
        runCycles(LOOPS_TO_RUN, () -> {
            int buttonMask = new ButtonMaskBuilder().addButtonPressed(XboxController.Button.kX.value).build();

            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, new float[5], new short[1], 10, buttonMask);
        });
        assertEquals(60, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR);
    }
}
