package girlsofsteel.commands;

import girlsofsteel.subsystems.Collector;

public class MiddleCollectorsReverse extends CommandBase {

    private final Collector m_collector;

    public MiddleCollectorsReverse(Collector collector) {
        m_collector = collector;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_collector.reverseMiddleConveyor();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
