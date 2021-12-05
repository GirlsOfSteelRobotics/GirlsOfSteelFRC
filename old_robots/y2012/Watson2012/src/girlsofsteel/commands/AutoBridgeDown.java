package girlsofsteel.commands;

public class AutoBridgeDown extends CommandBase {

    public AutoBridgeDown() {
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
