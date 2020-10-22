package frc.robot.commands;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.BaseTestFixture;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ChassisSubsystem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DriveChassisWithJoystickCommandTest extends BaseTestFixture {

    @Test
    public void testDriveForward() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            float[] axisInfo = new float[6];
            axisInfo[XboxController.Axis.kLeftY.value] = -.7f; // Negated because of pushing up gives negative numbers
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(0, axisInfo, new short[]{}, 0, 0);
            DriverStationDataJNI.notifyNewData();
        });

        assertTrue(chassis.getLeftDistance() > 0);
        assertTrue(chassis.getRightDistance() > 0);

    }

    @Test
    public void testDriveBackwards() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            float[] axisInfo = new float[6];
            axisInfo[XboxController.Axis.kLeftY.value] = .7f; // Positive because of pushing up gives negative numbers
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(0, axisInfo, new short[]{}, 0, 0);
        });

        assertTrue(chassis.getLeftDistance() < 0);
        assertTrue(chassis.getRightDistance() < 0);

    }

    @Test
    public void testTurnClockwise() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            float[] axisInfo = new float[6];
            axisInfo[XboxController.Axis.kRightX.value] = .7f; // Positive because of pushing up gives negative numbers
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(0, axisInfo, new short[]{}, 0, 0);
            DriverStationDataJNI.notifyNewData();
        });

        assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
        assertTrue(chassis.getHeading() > 0);

    }
}
