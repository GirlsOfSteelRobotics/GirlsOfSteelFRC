package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.RobotContainer;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorWithJoystickCommandTest extends BaseTestFixture {

    @Test
    public void testManualMoveUp() {
        try (RobotContainer container = new RobotContainer()) {
            ElevatorSubsystem elevator = container.getElevator(); // NOPMD(CloseResource)
            runCycles(5, () -> {
                DriverStationSim.setJoystickAxis(1, XboxController.Axis.kRightY.value, -.7);
                DriverStationSim.notifyNewData();
            });

            assertTrue(elevator.getHeight() > 0);
        }

    }

    @Test
    public void testManualMoveDown() {
        try (RobotContainer container = new RobotContainer()) {
            ElevatorSubsystem elevator = container.getElevator(); // NOPMD(CloseResource)

            // The elevator starts at the bottom, so raise it up first
            runCycles(50, () -> {
                DriverStationSim.setJoystickAxis(1, XboxController.Axis.kRightY.value, -.7);
                DriverStationSim.notifyNewData();
            });
            double startingHeight = elevator.getHeight();

            runCycles(25, () -> {
                DriverStationSim.setJoystickAxis(1, XboxController.Axis.kRightY.value, .7);
                DriverStationSim.notifyNewData();
            });

            assertTrue(elevator.getHeight() < startingHeight);
        }
    }
}
