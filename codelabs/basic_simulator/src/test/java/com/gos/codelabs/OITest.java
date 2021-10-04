package com.gos.codelabs;

import com.gos.codelabs.basic_simulator.RobotContainer;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
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
            DriverStationSim.setJoystickButton(1, XboxController.Button.kA.value, true);
        });
        assertTrue(punch.isExtended());

        // Release the button and make sure it goes back
        runCycles(2, () -> {
            DriverStationSim.setJoystickButton(1, XboxController.Button.kA.value, false);
        });
        assertFalse(punch.isExtended());
    }

    @Test
    public void testElevatorButtons() {

        final int loopsToRun = 400; // Give more than enough time for it to get where it needs to go

        RobotContainer container = new RobotContainer();
        ElevatorSubsystem lift = container.getElevator();

        assertEquals(0, lift.getHeight(), DOUBLE_EPSILON);

        // Press the button, check that it goes to the mid height
        runCycles(loopsToRun, () -> {
            DriverStationSim.setJoystickButton(1, XboxController.Button.kY.value, true);
        });
        assertEquals(40, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 2);
        resetJoysticks();

        // Press the button, check that it goes to the low height
        runCycles(loopsToRun, () -> {
            DriverStationSim.setJoystickButton(1, XboxController.Button.kB.value, true);
        });
        assertEquals(0, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 2);
        resetJoysticks();

        // Press the button, check that it goes to the high height
        runCycles(loopsToRun, () -> {
            DriverStationSim.setJoystickButton(1, XboxController.Button.kX.value, true);
        });
        assertEquals(60, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 2);
    }
}
