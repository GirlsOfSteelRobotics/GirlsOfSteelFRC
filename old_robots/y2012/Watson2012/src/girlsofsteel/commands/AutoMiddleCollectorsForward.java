package girlsofsteel.commands;

public class AutoMiddleCollectorsForward extends CommandBase {

    public AutoMiddleCollectorsForward() {
        requires(collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.forwardMiddleConveyor();
    }

    @Override
    protected boolean isFinished() {
        System.out.println("Middle Collectors Forward Done");
        return timeSinceInitialized() > 5;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
