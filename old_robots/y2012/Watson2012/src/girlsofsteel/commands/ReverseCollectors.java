package girlsofsteel.commands;

import girlsofsteel.subsystems.Collector;

public class ReverseCollectors extends CommandBase {
    private final Collector m_collector;

    public ReverseCollectors(Collector collector){
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_collector.reverseMiddleConveyor();
        m_collector.reverseBrush();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
