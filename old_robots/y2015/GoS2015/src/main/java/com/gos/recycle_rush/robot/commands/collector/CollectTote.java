package com.gos.recycle_rush.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/*
 *
 */
public class CollectTote extends Command {

    private final Collector m_collector;

    public CollectTote(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_collector.collectorToteIn();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollecting();
    }



}
