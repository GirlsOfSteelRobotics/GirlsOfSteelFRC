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

    private final NetworkTable mNetworkTable;
    private IPwmWrapper mControlPanelMotorSim;
    private final SimDeviceSim mDeviceSim;

    public ControlPanelSim(Robot robot)
    {
        mControlPanelMotorSim = SensorActuatorRegistry.get().getSpeedControllers().get(Constants.DRIVE_LEFT_FOLLOWER_SPARK + BaseCanSmartSpeedController.sCAN_SC_OFFSET);
        mNetworkTable = NetworkTableInstance.getDefault().getTable("ControlPanel");

        mNetworkTable.getEntry(".type").setString("ControlPanel");
        mNetworkTable.getEntry("sim/r").setDouble(0.0);
        mNetworkTable.getEntry("sim/g").setDouble(0.0);
        mNetworkTable.getEntry("sim/b").setDouble(0.0);

        mDeviceSim = new SimDeviceSim("REV Color Sensor V3[0,82]");
    }

    public void update()
    {

        mNetworkTable.getEntry("sim/Wheel Angle").setDouble(mControlPanelMotorSim.getPosition());

        double r = mNetworkTable.getEntry("sim/r").getDouble(0.0);
        double g = mNetworkTable.getEntry("sim/g").getDouble(0.0);
        double b = mNetworkTable.getEntry("sim/b").getDouble(0.0);

        mDeviceSim.getDouble("Red").set(r);
        mDeviceSim.getDouble("Green").set(g);
        mDeviceSim.getDouble("Blue").set(b);
    }
}
