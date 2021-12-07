package org.usfirst.frc.team3504.robot.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Collector;

/*
 *
 */
public class AngleCollectorOut extends Command {

    private final Collector m_collector;

    public AngleCollectorOut(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
        m_collector.collectorOut();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
