package com.girlsofsteel.simulator;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IDigitalIoWrapper;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;

public class BallSensorSimulator {

    private final NetworkTableEntry m_handoffNetworkTableSim;
    private final NetworkTableEntry m_secondaryNetworkTableSim;
    private final NetworkTableEntry m_topNetworkTableSim;

    private final IDigitalIoWrapper m_handoffWrapper;
    private final IDigitalIoWrapper m_secondaryWrapper;
    private final IDigitalIoWrapper m_topWrapper; 

    public BallSensorSimulator()
    {
        ShuffleboardTab tab = Shuffleboard.getTab("Simulator");
        m_handoffNetworkTableSim = tab.add("Handoff", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        m_secondaryNetworkTableSim = tab.add("Secondary", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        m_topNetworkTableSim = tab.add("Top", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

        m_handoffWrapper = SensorActuatorRegistry.get().getDigitalSources().get(Constants.DIGITAL_INPUT_SENSOR_HANDOFF);
        m_secondaryWrapper = SensorActuatorRegistry.get().getDigitalSources().get(Constants.DIGITAL_INPUT_SENSOR_SECONDARY); 
        m_topWrapper = SensorActuatorRegistry.get().getDigitalSources().get(Constants.DIGITAL_INPUT_SENSOR_TOP);
    }

    public void update() {
        boolean handOffSensor = m_handoffNetworkTableSim.getBoolean(false);
        boolean secondarySensor = m_secondaryNetworkTableSim.getBoolean(false);
        boolean topSensor = m_topNetworkTableSim.getBoolean(false);
        // System.out.println(handOffSensor + ", " + secondarySensor + ", " + topSensor);

        m_handoffWrapper.set(handOffSensor);
        m_secondaryWrapper.set(secondarySensor); 
        m_topWrapper.set(topSensor); 
    }
}
