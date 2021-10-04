package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.BaseTestFixture;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import com.gos.codelabs.basic_simulator.RobotContainer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ElevatorWithJoystickCommandTest extends BaseTestFixture {

    @Test
    public void testManualMoveUp() {
        RobotContainer container = new RobotContainer();
        ElevatorSubsystem elevator = container.getElevator();

        runCycles(5, () -> {
            DriverStationSim.setJoystickAxis(1, XboxController.Axis.kRightY.value, -.7);
            DriverStationSim.notifyNewData();
        });

        assertTrue(elevator.getHeight() > 0);

    }

    @Test
    public void testManualMoveDown() {
        RobotContainer container = new RobotContainer();
        ElevatorSubsystem elevator = container.getElevator();

        runCycles(5, () -> {
            DriverStationSim.setJoystickAxis(1, XboxController.Axis.kRightY.value, .7);
            DriverStationSim.notifyNewData();
        });

        assertTrue(elevator.getHeight() < 0);

    }
}
