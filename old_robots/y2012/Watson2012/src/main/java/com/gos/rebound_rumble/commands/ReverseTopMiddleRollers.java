package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;

public class ReverseTopMiddleRollers extends CommandBase {
    private final Collector m_collector;
    private final Shooter m_shooter;

    public ReverseTopMiddleRollers(Collector collector, Shooter shooter) {
        m_collector = collector;
        m_shooter = shooter;
        addRequirements(m_collector);
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_collector.reverseMiddleConveyor();
        m_shooter.topRollersBackward();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopMiddleConveyor();
        m_shooter.topRollersOff();
    }



}
