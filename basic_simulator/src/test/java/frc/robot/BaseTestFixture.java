package frc.robot;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.DefaultDataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;
import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.junit.Before;

import java.lang.reflect.Field;

import static org.junit.Assert.fail;

public class BaseTestFixture {

    protected static final double DOUBLE_EPSILON = 1e-6;

    private final Simulator m_simulator;

    private static class Simulator extends ASimulator {

    }

    public static class ButtonMaskBuilder {
        private int m_buttonMask;

        public ButtonMaskBuilder addButtonPressed(int buttonNumber) {
            m_buttonMask |= 1 << (buttonNumber - 1);

            return this;
        }

        public int build() {
            return m_buttonMask;
        }
    }

    protected BaseTestFixture() {

        DefaultDataAccessorFactory.initalize();
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setLogLevel(SimulatorDataAccessor.SnobotLogLevel.DEBUG);

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(false);

        m_simulator = new Simulator();
    }

    @Before
    public void setupSimulator() {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        m_simulator.loadConfig("simulator_config/simulator_config.yml");

        try {
            Field privateStringField = CommandScheduler.class.getDeclaredField("instance");
            privateStringField.setAccessible(true);
            privateStringField.set(CommandScheduler.getInstance(), null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

    }

    protected void runCycles(int numCycles) {
        runCycles(numCycles, null);
    }

    protected void runCycles(int numCycles, Runnable runnable) {
        for (int i = 0; i < numCycles; ++i) {
            runCycle(runnable);
        }
    }

    protected void runCycle() {
        runCycle(null);
    }

    protected void runCycle(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }

        CommandScheduler.getInstance().run();
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().updateSimulatorComponents(.02);
        DriverStationDataJNI.notifyNewData();
    }
}
