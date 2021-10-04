package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import com.gos.codelabs.BaseTestFixture;
import com.gos.codelabs.basic_simulator.RobotContainer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DriveChassisWithJoystickCommandTest extends BaseTestFixture {

    @Test
    public void testDriveForward() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            DriverStationSim.setJoystickAxis(0, XboxController.Axis.kLeftY.value, -.7); // Negated because of pushing up gives negative numbers
            DriverStationSim.notifyNewData();
        });

        assertTrue(chassis.getLeftDistance() > 0);
        assertTrue(chassis.getRightDistance() > 0);

    }

    @Test
    public void testDriveBackwards() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            DriverStationSim.setJoystickAxis(0, XboxController.Axis.kLeftY.value, .7); // Positive because of pushing up gives negative numbers
            DriverStationSim.notifyNewData();
        });

        assertTrue(chassis.getLeftDistance() < 0);
        assertTrue(chassis.getRightDistance() < 0);

    }

    @Test
    public void testTurnClockwise() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            DriverStationSim.setJoystickAxis(0, XboxController.Axis.kRightX.value, .7);
            DriverStationSim.notifyNewData();
        });

        assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
        assertTrue(chassis.getHeading() > 0);

    }

    @Test
    public void testTurnCounterClockwise() {
        RobotContainer container = new RobotContainer();
        ChassisSubsystem chassis = container.getChassis();

        runCycles(5, () -> {
            DriverStationSim.setJoystickAxis(0, XboxController.Axis.kRightX.value, -.7);
            DriverStationSim.notifyNewData();
        });

        assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
        assertTrue(chassis.getHeading() < 0);

    }
}
