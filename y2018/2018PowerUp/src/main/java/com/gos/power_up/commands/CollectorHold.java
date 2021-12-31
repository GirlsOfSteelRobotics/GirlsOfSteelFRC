package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectorHold extends Command {
    private final Collector m_collector;

    public CollectorHold(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }


    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        m_collector.runCollector();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
    }


    @Override
    protected void interrupted() {
    }
}
