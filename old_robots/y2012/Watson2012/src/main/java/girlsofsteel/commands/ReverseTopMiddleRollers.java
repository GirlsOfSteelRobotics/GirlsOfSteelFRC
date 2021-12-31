package girlsofsteel.commands;

import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;

public class ReverseTopMiddleRollers extends CommandBase {
    private final Collector m_collector;
    private final Shooter m_shooter;

    public ReverseTopMiddleRollers(Collector collector, Shooter shooter) {
        m_collector = collector;
        m_shooter = shooter;
        requires(m_collector);
        requires(m_shooter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_collector.reverseMiddleConveyor();
        m_shooter.topRollersBackward();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_collector.stopMiddleConveyor();
        m_shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
