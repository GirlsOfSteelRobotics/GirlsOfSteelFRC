package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Bridge;

public class BridgeUp extends GosCommandBaseBase {
    private final Bridge m_bridge;

    public BridgeUp(Bridge bridge) {
        m_bridge = bridge;
        addRequirements(m_bridge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_bridge.upBridgeArm();
    }

    @Override
    public boolean isFinished() {
        return m_bridge.isFullyUp();
    }

    @Override
    public void end(boolean interrupted) {
        m_bridge.stopBridgeArm();
        System.out.println("Up Bridge Done");
    }


}
