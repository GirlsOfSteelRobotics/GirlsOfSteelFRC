package girlsofsteel.commands;

public class BridgeUp extends CommandBase {

    public BridgeUp() {
        requires(bridge);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        bridge.upBridgeArm();
    }

    @Override
    protected boolean isFinished() {
        return bridge.isFullyUp();
    }

    @Override
    protected void end() {
        bridge.stopBridgeArm();
        System.out.println("Up Bridge Done");
    }

    @Override
    protected void interrupted() {
        end();
    }
}
