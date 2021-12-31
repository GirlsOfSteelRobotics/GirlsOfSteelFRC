package com.gos.rebound_rumble.commands;


import com.gos.rebound_rumble.subsystems.Collector;

public class Collect extends CommandBase {
    private final Collector m_collector;

    public Collect(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
        m_collector.forwardBrush();
        m_collector.forwardMiddleConveyor();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
