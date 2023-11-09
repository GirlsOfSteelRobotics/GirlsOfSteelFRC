package com.gos.recycle_rush.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/*
 *
 */
public class AngleCollectorOut extends Command {

    private final Collector m_collector;

    public AngleCollectorOut(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
        m_collector.collectorOut();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
