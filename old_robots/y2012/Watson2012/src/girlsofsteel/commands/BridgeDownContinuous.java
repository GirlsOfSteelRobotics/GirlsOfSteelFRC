package girlsofsteel.commands;

public class BridgeDownContinuous extends CommandBase {

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        bridge.downBridgeArm();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        bridge.stopBridgeArm();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
