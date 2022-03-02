package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 *
 */
public class CollectorStop extends InstantCommand {
    private final Collector m_collector;

    public CollectorStop(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
        m_collector.stop();
    }

}
