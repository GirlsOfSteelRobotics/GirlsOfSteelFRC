package girlsofsteel.commands;

public class BridgeUp extends CommandBase {

    public BridgeUp() {
        requires(bridge);
    }

    protected void initialize() {
    }

    protected void execute() {
        bridge.upBridgeArm();
    }

    protected boolean isFinished() {
        return bridge.isFullyUp();
    }

    protected void end() {
        bridge.stopBridgeArm();
        System.out.println("Up Bridge Done");
    }

    protected void interrupted() {
        end();
    }
}
