package com.girlsofsteel.simulator;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

public class BallSensorSimulator {


    private final NetworkTable m_networkTable;
    private final IDigitalIoWrapper m_handoffWrapper;
    private final IDigitalIoWrapper m_secondaryWrapper;
    private final IDigitalIoWrapper m_topWrapper; 

    public BallSensorSimulator()
    {
        m_networkTable = NetworkTableInstance.getDefault().getTable("BallSensorSim");
        m_networkTable.getEntry("Handoff").setBoolean(false);
        m_networkTable.getEntry("Secondary").setBoolean(false);
        m_networkTable.getEntry("Top").setBoolean(false);

        m_handoffWrapper = SensorActuatorRegistry.get().getDigitalSources().get(Constants.DIGITAL_INPUT_SENSOR_HANDOFF);
        m_secondaryWrapper = SensorActuatorRegistry.get().getDigitalSources().get(Constants.DIGITAL_INPUT_SENSOR_SECONDARY); 
        m_topWrapper = SensorActuatorRegistry.get().getDigitalSources().get(Constants.DIGITAL_INPUT_SENSOR_TOP); 

    }

    public void update() {
        boolean handOffSensor = m_networkTable.getEntry("Handoff").getBoolean(false);
        boolean secondarySensor = m_networkTable.getEntry("Secondary").getBoolean(false);
        boolean topSensor = m_networkTable.getEntry("Top").getBoolean(false);
        // System.out.println(handOffSensor + ", " + secondarySensor + ", " + topSensor);

        m_handoffWrapper.set(handOffSensor);
        m_secondaryWrapper.set(secondarySensor); 
        m_topWrapper.set(topSensor); 
    }
}
