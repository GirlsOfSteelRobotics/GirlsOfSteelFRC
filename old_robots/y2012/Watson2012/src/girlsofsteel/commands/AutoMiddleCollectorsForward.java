package girlsofsteel.commands;

import girlsofsteel.subsystems.Collector;

public class AutoMiddleCollectorsForward extends CommandBase {
    private final Collector m_collector;

    public AutoMiddleCollectorsForward(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_collector.forwardMiddleConveyor();
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
