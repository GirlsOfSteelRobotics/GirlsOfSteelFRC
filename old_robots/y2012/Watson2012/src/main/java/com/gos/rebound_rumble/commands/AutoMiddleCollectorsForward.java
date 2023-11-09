package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Collector;

public class AutoMiddleCollectorsForward extends GosCommandBase {
    private final Collector m_collector;

    public AutoMiddleCollectorsForward(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_collector.forwardMiddleConveyor();
    }

    @Override
    public boolean isFinished() {
        System.out.println("Middle Collectors Forward Done");
        return timeSinceInitialized() > 5;
    }

    @Override
    public void end(boolean interrupted) {
    }


}
