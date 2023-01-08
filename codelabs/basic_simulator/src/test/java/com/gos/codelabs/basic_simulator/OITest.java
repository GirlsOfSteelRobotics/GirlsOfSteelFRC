package com.gos.codelabs.basic_simulator;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OITest extends BaseTestFixture {

    @Test
    public void testPunchButtons() {
        try (RobotContainer container = new RobotContainer()) {
            PunchSubsystem punch = container.getPunch(); // NOPMD(CloseResource)


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
    }

    @Test
    public void testElevatorGoMid() {

        final int loopsToRun = 96; // Give more than enough time for it to get where it needs to go

        try (RobotContainer container = new RobotContainer(); ElevatorSubsystem lift = container.getElevator()) {
            assertEquals(0, lift.getHeight(), DOUBLE_EPSILON);

            // Press the button, check that it goes to the mid height
            runCycles(loopsToRun, () -> {
                DriverStationSim.setJoystickButton(1, XboxController.Button.kY.value, true);
            });
            assertEquals(ElevatorSubsystem.Positions.MID.m_heightMeters, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 3);
            resetJoysticks();
        }
    }

    @Test
    public void testElevatorGoLow() {
        final int loopsToRun = 96; // Give more than enough time for it to get where it needs to go

        try (RobotContainer container = new RobotContainer(); ElevatorSubsystem lift = container.getElevator()) {
            assertEquals(0, lift.getHeight(), DOUBLE_EPSILON);

            // Press the button, check that it goes to the low height
            runCycles(loopsToRun, () -> {
                DriverStationSim.setJoystickButton(1, XboxController.Button.kB.value, true);
            });
            assertEquals(ElevatorSubsystem.Positions.LOW.m_heightMeters, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 2);
            resetJoysticks();
        }
    }

    @Test
    public void testElevatorGoHigh() {
        final int loopsToRun = 96;

        try (RobotContainer container = new RobotContainer(); ElevatorSubsystem lift = container.getElevator()) {
            assertEquals(0, lift.getHeight(), DOUBLE_EPSILON);

            // Press the button, check that it goes to the high height
            runCycles(loopsToRun, () -> {
                DriverStationSim.setJoystickButton(1, XboxController.Button.kX.value, true);
            });
            assertEquals(ElevatorSubsystem.Positions.HIGH.m_heightMeters, lift.getHeight(), ElevatorSubsystem.ALLOWABLE_POSITION_ERROR * 3);
        }
    }
}
