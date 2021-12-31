package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CollectorStop extends InstantCommand {
    private final Collector m_collector;

    public CollectorStop(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
        m_collector.stop();
    }

}
