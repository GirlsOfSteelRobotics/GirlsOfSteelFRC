package com.girlsofsteel.simulator;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.robot_container.JavaRobotContainer;
import frc.robot.Robot;

public class GirlsOfSteelSimulator extends ASimulator {

    private ControlPanelSim m_controlPanelSim;
    private CameraSimulator m_cameraSimulator;

    @Override
    public void setRobot(IRobotClassContainer robot) {
        setRobot((Robot) ((JavaRobotContainer) robot).getJavaRobot());
    }

    public void setRobot(Robot robot) {
        m_controlPanelSim = new ControlPanelSim(robot);
        m_cameraSimulator = new CameraSimulator(robot);
    }

    @Override
    public void update() {
        m_controlPanelSim.update();
        m_cameraSimulator.update();
    }
}
