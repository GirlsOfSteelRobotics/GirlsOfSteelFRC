package girlsofsteel.commands;

public class BridgeDownContinuous extends CommandBase {

    protected void initialize() {
    }

    protected void execute() {
        bridge.downBridgeArm();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        bridge.stopBridgeArm();
    }

    protected void interrupted() {
        end();
    }
    
}
