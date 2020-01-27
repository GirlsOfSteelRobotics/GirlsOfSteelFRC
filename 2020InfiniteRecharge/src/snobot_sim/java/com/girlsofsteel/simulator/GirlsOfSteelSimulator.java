package com.girlsofsteel.simulator;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.robot_container.JavaRobotContainer;
import frc.robot.Robot;

public class GirlsOfSteelSimulator extends ASimulator {

    private ControlPanelSim mControlPanelSim;
    private CameraSimulator mCameraSimulator;

    @Override
    public void setRobot(IRobotClassContainer aRobot) {
        setRobot((Robot) ((JavaRobotContainer) aRobot).getJavaRobot());
    }

    public void setRobot(Robot aRobot) {
        mControlPanelSim = new ControlPanelSim(aRobot);
        mCameraSimulator = new CameraSimulator(aRobot);
    }

    @Override
    public void update() {
        mControlPanelSim.update();
        mCameraSimulator.update();
    }
}
