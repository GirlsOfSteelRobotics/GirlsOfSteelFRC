package com.gos.codelabs.basic_simulator;

import org.wpilib.hardware.hal.HAL;
import org.wpilib.simulation.DriverStationSim;
import org.wpilib.simulation.SimDeviceSim;
import org.wpilib.simulation.SimHooks;
import org.wpilib.command2.CommandScheduler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.fail;

public class BaseTestFixture {

    protected static final double DOUBLE_EPSILON = 1e-6;

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @BeforeEach
    public void setupSimulator() {
        try {
            HAL.initialize();

            Field privateStringField = CommandScheduler.class.getDeclaredField("instance");
            privateStringField.setAccessible(true);
            privateStringField.set(CommandScheduler.getInstance(), null);
            CommandScheduler.getInstance().enable();
            DriverStationSim.setEnabled(true);

            DriverStationSim.setJoystickAxesMaximumIndex(0, 6);
            DriverStationSim.setJoystickAxesMaximumIndex(1, 6);

            DriverStationSim.setJoystickButtonsMaximumIndex(0, 10);
            DriverStationSim.setJoystickButtonsMaximumIndex(1, 10);

            // Make sure the robot sees that it is enabled before the test starts, otherwise commands won't get scheduled
            DriverStationSim.notifyNewData();

            SimHooks.pauseTiming();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

    }

    @AfterEach
    public void teardown() {
        CommandScheduler.getInstance().disable();
        DriverStationSim.setEnabled(false);
        DriverStationSim.resetData();
        // Every test creates brand new motor controllers that re-use the same CAN IDs. Clear out the simulated devices
        // from the last test, otherwise the simulator would keep updating the old (closed) devices instead of the new ones
        SimDeviceSim.resetData();
    }


    protected void resetJoysticks() {
        for (int i = 0; i < 6; ++i) {
            DriverStationSim.setJoystickAxis(0, i, 0);
            DriverStationSim.setJoystickAxis(1, i, 0);
        }

        for (int i = 0; i < 10; ++i) {
            DriverStationSim.setJoystickButton(0, i, false);
            DriverStationSim.setJoystickButton(1, i, false);
        }
    }

    protected void runCycles(int numCycles) {
        runCycles(numCycles, null, null);
    }

    protected void runCycles(int numCycles, Runnable runnable) {
        runCycles(numCycles, runnable, null);
    }

    protected void runCycles(int numCycles, Runnable runnable, BooleanSupplier exitEarly) {
        for (int i = 0; i < numCycles; ++i) {
            runCycle(runnable);

            if (exitEarly != null && exitEarly.getAsBoolean()) {
                System.out.println("Breaking after " + i + " loops"); // NOPMD
                break;
            }
        }
    }

    protected void runCycle() {
        runCycle(null);
    }

    protected void runCycle(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }

        SimHooks.stepTiming(.02);
        CommandScheduler.getInstance().run();
        DriverStationSim.notifyNewData();
    }
}
