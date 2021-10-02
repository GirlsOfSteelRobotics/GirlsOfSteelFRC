package com.girlsofsteel.simulator;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;
import edu.wpi.first.hal.sim.SimDeviceSim;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.Robot;

import java.util.TreeMap;

public class ControlPanelSim {

    private static final int NUM_PIECES = Colors.values().length * 2;
    private static final TreeMap<Double, Colors> ANGLE_TO_SENSOR_COLOR; // NOPMD

    static {
        ANGLE_TO_SENSOR_COLOR = new TreeMap<>();

        double angleSlice = 360.0 / NUM_PIECES;

        for (int i = 0; i < NUM_PIECES; ++i) {
            Colors color = Colors.values()[i % Colors.values().length];
            double angleStart = angleSlice * i;
            ANGLE_TO_SENSOR_COLOR.put(angleStart, color);
        }

    }

    public enum Colors {
        RED(1, 0, 0),
        YELLOW(1, 1, 0),
        BLUE(0, 0, 1),
        GREEN(0, 1, 0),
        ;

        public double m_red;
        public double m_green;
        public double m_blue;

        Colors(double red, double green, double blue) {
            m_red = red;
            m_green = green;
            m_blue = blue;

        }
    }

    private final NetworkTable m_networkTable;
    private final IPwmWrapper m_controlPanelMotorSim;
    private final SimDeviceSim m_deviceSim;

    public ControlPanelSim(Robot robot) {
        m_controlPanelMotorSim = SensorActuatorRegistry.get().getSpeedControllers().get(Constants.CONTROL_PANEL_TALON + BaseCanSmartSpeedController.sCAN_SC_OFFSET);

        m_deviceSim = new SimDeviceSim("REV Color Sensor V3[0,82]");

        m_networkTable = NetworkTableInstance.getDefault().getTable("ControlPanel");
        m_networkTable.getEntry(".type").setString("ControlPanel");
    }

    public void update() {
        double angleToTest = boundAngle(m_controlPanelMotorSim.getPosition());
        Colors selectedColor = ANGLE_TO_SENSOR_COLOR.lowerEntry(angleToTest).getValue();

        m_networkTable.getEntry("sim/Wheel Angle").setDouble(m_controlPanelMotorSim.getPosition());

        m_deviceSim.getDouble("Red").set(selectedColor.m_red);
        m_deviceSim.getDouble("Green").set(selectedColor.m_green);
        m_deviceSim.getDouble("Blue").set(selectedColor.m_blue);
    }

    private double boundAngle(double angle) {
        double output = angle;
        while (output <= 0) {
            output += 360;
        }
        while (output > 360) {
            output -= 360;
        }
        return output;
    }
}
