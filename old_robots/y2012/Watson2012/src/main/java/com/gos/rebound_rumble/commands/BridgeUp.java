package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Bridge;

public class BridgeUp extends CommandBase {
    private final Bridge m_bridge;

    public BridgeUp(Bridge bridge) {
        m_bridge = bridge;
        requires(m_bridge);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_bridge.upBridgeArm();
    }

    @Override
    protected boolean isFinished() {
        return m_bridge.isFullyUp();
    }

    @Override
    protected void end() {
        m_bridge.stopBridgeArm();
        System.out.println("Up Bridge Done");
    }

    @Override
    protected void interrupted() {
        end();
    }
}
