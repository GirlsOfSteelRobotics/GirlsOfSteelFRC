package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Bridge;

public class BridgeDown extends GosCommand {
    private final Bridge m_bridge;

    public BridgeDown(Bridge bridge) {
        m_bridge = bridge;
        addRequirements(m_bridge);
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
        return m_bridge.hasHitBridge();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("BridgeDown Done");
    }


}
