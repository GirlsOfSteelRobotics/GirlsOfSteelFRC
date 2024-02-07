package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;

public class StopCollectors extends GosCommandBaseBase {

    private final Collector m_collector;

    public StopCollectors(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
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
    }



}
