package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import com.gos.codelabs.basic_simulator.BaseTestFixture;
import com.gos.codelabs.basic_simulator.RobotContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriveChassisWithJoystickCommandTest extends BaseTestFixture {

    @Test
    public void testDriveForward() {
        try (RobotContainer container = new RobotContainer(); ChassisSubsystem chassis = container.getChassis()) {

            runCycles(5, () -> {
                DriverStationSim.setJoystickAxis(0, XboxController.Axis.kLeftY.value, -.7); // Negated because of pushing up gives negative numbers
                DriverStationSim.notifyNewData();
            });

            assertTrue(chassis.getLeftDistance() > 0);
            assertTrue(chassis.getRightDistance() > 0);
        }
    }

    @Test
    public void testDriveBackwards() {
        try (RobotContainer container = new RobotContainer(); ChassisSubsystem chassis = container.getChassis()) {

            runCycles(5, () -> {
                DriverStationSim.setJoystickAxis(0, XboxController.Axis.kLeftY.value, .7); // Positive because of pushing up gives negative numbers
                DriverStationSim.notifyNewData();
            });

            assertTrue(chassis.getLeftDistance() < 0);
            assertTrue(chassis.getRightDistance() < 0);
        }
    }

    @Test
    public void testTurnClockwise() {
        try (RobotContainer container = new RobotContainer(); ChassisSubsystem chassis = container.getChassis()) {

            runCycles(5, () -> {
                DriverStationSim.setJoystickAxis(0, XboxController.Axis.kRightX.value, .7);
                DriverStationSim.notifyNewData();
            });

            assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
            assertTrue(chassis.getHeading() > 0);
        }
    }

    @Test
    public void testTurnCounterClockwise() {
        try (RobotContainer container = new RobotContainer(); ChassisSubsystem chassis = container.getChassis()) {

            runCycles(5, () -> {
                DriverStationSim.setJoystickAxis(0, XboxController.Axis.kRightX.value, -.7);
                DriverStationSim.notifyNewData();
            });

            assertEquals(0, chassis.getAverageDistance(), DOUBLE_EPSILON);
            assertTrue(chassis.getHeading() < 0);
        }
    }
}
