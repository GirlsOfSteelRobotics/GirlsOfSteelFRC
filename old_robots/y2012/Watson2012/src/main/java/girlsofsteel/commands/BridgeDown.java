package girlsofsteel.commands;

import girlsofsteel.subsystems.Bridge;

public class BridgeDown extends CommandBase {
    private final Bridge m_bridge;

    public BridgeDown(Bridge bridge) {
        m_bridge = bridge;
        requires(m_bridge);
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
        return m_bridge.hasHitBridge();
    }

    @Override
    protected void end() {
        System.out.println("BridgeDown Done");
    }

    @Override
    protected void interrupted() {
        end();
    }
}
