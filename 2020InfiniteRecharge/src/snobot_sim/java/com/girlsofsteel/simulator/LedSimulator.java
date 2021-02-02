package com.girlsofsteel.simulator;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IAddressableLedWrapper;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LedSimulator {
    private final NetworkTable m_networkTable;
    private final IAddressableLedWrapper m_ledWrapper;
    private final boolean m_exists;

    public LedSimulator() {
        m_networkTable = NetworkTableInstance.getDefault().getTable("LedSimulator");
        m_networkTable.getEntry(".type").setString("LedSimulator");
        m_exists = !SensorActuatorRegistry.get().getLeds().isEmpty();
        if (m_exists) {
            m_ledWrapper = SensorActuatorRegistry.get().getLeds().get(0);
        }
        else {
            m_ledWrapper = null;
        }
    }

    public void update() {
        if (!m_exists) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (byte b : m_ledWrapper.getData()) {
            builder.append(b + ",");
        }

        m_networkTable.getEntry("Values").setString(builder.toString());
        System.out.println(builder);
    }
}
