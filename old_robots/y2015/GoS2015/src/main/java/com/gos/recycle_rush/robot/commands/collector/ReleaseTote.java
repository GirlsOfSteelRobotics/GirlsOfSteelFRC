package com.gos.recycle_rush.robot.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/*
 * Consider using limit switches
 */
public class ReleaseTote extends Command {

    private final Collector m_collector;

    public ReleaseTote(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_collector.collectorToteOut();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_collector.stopCollecting();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
