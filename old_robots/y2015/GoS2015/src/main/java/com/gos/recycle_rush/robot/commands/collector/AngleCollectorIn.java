package com.gos.recycle_rush.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/*
 * Command order for suckers should be changed based on what
 * the drivers want -> could use a command group
 *
 */
public class AngleCollectorIn extends Command {

    private final Collector m_collector;

    public AngleCollectorIn(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    @Override
    public void initialize() {
        // Only needs to run once to move collector pneumatic in
        m_collector.collectorIn();
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
