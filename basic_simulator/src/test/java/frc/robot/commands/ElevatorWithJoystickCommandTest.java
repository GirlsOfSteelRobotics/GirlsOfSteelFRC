package frc.robot.commands;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.wpilibj.XboxController;
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
            float[] axisInfo = new float[6];
            axisInfo[XboxController.Axis.kRightY.value] = -.7f; // Negated because of pushing up gives negative numbers
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, axisInfo, new short[]{}, 0, 0);
            DriverStationDataJNI.notifyNewData();
        });

        assertTrue(elevator.getHeight() > 0);

    }

    @Test
    public void testManualMoveDown() {
        RobotContainer container = new RobotContainer();
        ElevatorSubsystem elevator = container.getElevator();

        runCycles(5, () -> {
            float[] axisInfo = new float[6];
            axisInfo[XboxController.Axis.kRightY.value] = .7f; // Positive because of pushing up gives negative numbers
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(1, axisInfo, new short[]{}, 0, 0);
            DriverStationDataJNI.notifyNewData();
        });

        assertTrue(elevator.getHeight() < 0);

    }
}
