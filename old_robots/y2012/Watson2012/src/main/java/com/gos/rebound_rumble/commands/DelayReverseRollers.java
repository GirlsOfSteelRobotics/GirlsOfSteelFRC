package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;

public class DelayReverseRollers extends CommandBase {
    private final Collector m_collector;
    private final Shooter m_shooter;

    public DelayReverseRollers(Shooter shooter, Collector collector) {
        m_collector = collector;
        m_shooter = shooter;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if (timeSinceInitialized() > 4.0) {
            m_collector.reverseBrush();
            m_collector.reverseMiddleConveyor();
            m_shooter.topRollersBackward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
        m_shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
