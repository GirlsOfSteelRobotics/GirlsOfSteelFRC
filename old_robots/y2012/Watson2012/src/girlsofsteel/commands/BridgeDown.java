package girlsofsteel.commands;

public class BridgeDown extends CommandBase {

    public BridgeDown() {
        requires(bridge);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        bridge.downBridgeArm();
    }

    @Override
    protected boolean isFinished() {
        return bridge.hasHitBridge();
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
