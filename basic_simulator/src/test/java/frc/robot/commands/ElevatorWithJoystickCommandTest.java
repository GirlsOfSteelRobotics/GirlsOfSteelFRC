package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import frc.robot.BaseTestFixture;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ElevatorSubsystem;
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
