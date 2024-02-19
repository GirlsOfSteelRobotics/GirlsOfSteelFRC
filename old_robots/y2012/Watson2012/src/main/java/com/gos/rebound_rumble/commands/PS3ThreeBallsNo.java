package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;

public class PS3ThreeBallsNo extends GosCommandBaseBase {

    private final Collector m_collector;

    public PS3ThreeBallsNo(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_collector.stopMiddleConveyor();
        m_collector.reverseBrush();
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
