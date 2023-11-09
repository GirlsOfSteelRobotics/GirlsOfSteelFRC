package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;

public class DelayReverseRollers extends GosCommand {
    private final Collector m_collector;
    private final Shooter m_shooter;

    public DelayReverseRollers(Shooter shooter, Collector collector) {
        m_collector = collector;
        m_shooter = shooter;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (timeSinceInitialized() > 4.0) {
            m_collector.reverseBrush();
            m_collector.reverseMiddleConveyor();
            m_shooter.topRollersBackward();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
        m_shooter.topRollersOff();
    }



}
