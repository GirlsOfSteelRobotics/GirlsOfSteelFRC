package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Bridge;

public class AutoBridgeDown extends CommandBase {
    private final Bridge m_bridge;

    public AutoBridgeDown(Bridge bridge) {
        m_bridge = bridge;
        requires(bridge);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_bridge.downBridgeArm();
    }

    @Override
    protected boolean isFinished() {
        return timeSinceInitialized() > 3;
    }

    @Override
    protected void end() {
        System.out.println("AutoBridgeDown Done");
    }

    @Override
    protected void interrupted() {
        end();
    }
}
