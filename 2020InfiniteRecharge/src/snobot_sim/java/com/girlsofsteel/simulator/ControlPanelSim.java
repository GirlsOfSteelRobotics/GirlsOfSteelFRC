package com.girlsofsteel.simulator;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;
import edu.wpi.first.hal.sim.SimDeviceSim;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.Robot;

public class ControlPanelSim {

    private final NetworkTable m_networkTable;
    private final IPwmWrapper m_controlPanelMotorSim;
    private final SimDeviceSim m_deviceSim;

    public ControlPanelSim(Robot robot) {
        m_controlPanelMotorSim = SensorActuatorRegistry.get().getSpeedControllers().get(Constants.CONTROL_PANEL_TALON + BaseCanSmartSpeedController.sCAN_SC_OFFSET);
        m_networkTable = NetworkTableInstance.getDefault().getTable("ControlPanel");

        m_networkTable.getEntry(".type").setString("ControlPanel");
        m_networkTable.getEntry("sim/r").setDouble(0.0);
        m_networkTable.getEntry("sim/g").setDouble(0.0);
        m_networkTable.getEntry("sim/b").setDouble(0.0);

        m_deviceSim = new SimDeviceSim("REV Color Sensor V3[0,82]");
    }

    public void update() {

        m_networkTable.getEntry("sim/Wheel Angle").setDouble(m_controlPanelMotorSim.getPosition());

        double r = m_networkTable.getEntry("sim/r").getDouble(0.0);
        double g = m_networkTable.getEntry("sim/g").getDouble(0.0);
        double b = m_networkTable.getEntry("sim/b").getDouble(0.0);

        m_deviceSim.getDouble("Red").set(r);
        m_deviceSim.getDouble("Green").set(g);
        m_deviceSim.getDouble("Blue").set(b);
    }
}
