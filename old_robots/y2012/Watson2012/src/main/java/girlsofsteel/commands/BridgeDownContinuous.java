package girlsofsteel.commands;

import girlsofsteel.subsystems.Bridge;

public class BridgeDownContinuous extends CommandBase {
    private final Bridge m_bridge;

    public BridgeDownContinuous(Bridge bridge) {
        m_bridge = bridge;
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
        return false;
    }

    @Override
    protected void end() {
        m_bridge.stopBridgeArm();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
