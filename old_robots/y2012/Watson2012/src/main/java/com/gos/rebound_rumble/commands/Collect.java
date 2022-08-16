package com.gos.rebound_rumble.commands;


import com.gos.rebound_rumble.subsystems.Collector;

public class Collect extends CommandBase {
    private final Collector m_collector;

    public Collect(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
        m_collector.forwardBrush();
        m_collector.forwardMiddleConveyor();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }



}
