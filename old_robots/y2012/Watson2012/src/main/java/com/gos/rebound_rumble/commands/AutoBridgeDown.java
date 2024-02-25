package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Bridge;

public class AutoBridgeDown extends GosCommandBaseBase {
    private final Bridge m_bridge;

    public AutoBridgeDown(Bridge bridge) {
        m_bridge = bridge;
        addRequirements(bridge);
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
        return timeSinceInitialized() > 3;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("AutoBridgeDown Done");
    }


}
