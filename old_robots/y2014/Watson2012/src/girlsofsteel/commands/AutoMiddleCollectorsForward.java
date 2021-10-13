package girlsofsteel.commands;

public class AutoMiddleCollectorsForward extends CommandBase {

    public AutoMiddleCollectorsForward() {
        requires(collector);
    }

    protected void initialize() {
    }

    protected void execute() {
        collector.forwardMiddleConveyor();
    }

    protected boolean isFinished() {
        System.out.println("Middle Collectors Forward Done");
        return timeSinceInitialized() > 5;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
