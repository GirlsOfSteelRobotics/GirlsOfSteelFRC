package girlsofsteel.commands;

public class AutoBridgeDown extends CommandBase {

    public AutoBridgeDown() {
        requires(bridge);
    }

    protected void initialize() {
    }

    protected void execute() {
        bridge.downBridgeArm();
    }

    protected boolean isFinished() {
        if(timeSinceInitialized() > 3){
            return true;
        }
        return false;
    }

    protected void end() {
        System.out.println("AutoBridgeDown Done");
    }

    protected void interrupted() {
        end();
    }
}
