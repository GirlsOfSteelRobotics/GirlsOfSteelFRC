package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Bridge;

public class BridgeDownContinuous extends GosCommandBaseBase {
    private final Bridge m_bridge;

    public BridgeDownContinuous(Bridge bridge) {
        m_bridge = bridge;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_bridge.downBridgeArm();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_bridge.stopBridgeArm();
    }



}
