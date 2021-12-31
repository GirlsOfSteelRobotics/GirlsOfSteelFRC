package girlsofsteel.commands;

import girlsofsteel.subsystems.Collector;

public class AutoBrushFoward extends CommandBase {
    private final Collector m_collector;

    public AutoBrushFoward(Collector collector) {
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
        System.out.println("Brush Forward Done");
        return timeSinceInitialized() > 5;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
