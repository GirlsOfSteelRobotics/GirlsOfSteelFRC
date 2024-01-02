package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;

public class AutoBrushFoward extends GosCommand {
    private final Collector m_collector;

    public AutoBrushFoward(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_collector.forwardBrush();
    }

    @Override
    public boolean isFinished() {
        System.out.println("Brush Forward Done");
        return timeSinceInitialized() > 5;
    }

    @Override
    public void end(boolean interrupted) {
    }


}
