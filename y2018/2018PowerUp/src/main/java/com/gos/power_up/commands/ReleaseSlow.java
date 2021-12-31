package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseSlow extends Command {
    private final Collector m_collector;

    public ReleaseSlow(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }


    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        m_collector.release(0.5); //TODO: test this value
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_collector.stop();
    }


}
