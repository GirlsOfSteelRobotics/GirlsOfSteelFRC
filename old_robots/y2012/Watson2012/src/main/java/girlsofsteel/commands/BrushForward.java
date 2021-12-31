package girlsofsteel.commands;

import girlsofsteel.subsystems.Collector;

public class BrushForward extends CommandBase {
    private final Collector m_collector;

    public BrushForward(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_collector.forwardBrush();
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
