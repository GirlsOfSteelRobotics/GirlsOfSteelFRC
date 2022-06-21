package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;

public class MiddleCollectorsReverse extends CommandBase {

    private final Collector m_collector;

    public MiddleCollectorsReverse(Collector collector) {
        m_collector = collector;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_collector.reverseMiddleConveyor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
