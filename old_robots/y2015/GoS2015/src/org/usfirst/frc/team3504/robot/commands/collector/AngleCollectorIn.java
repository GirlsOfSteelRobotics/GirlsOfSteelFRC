package org.usfirst.frc.team3504.robot.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Collector;

/*
 * Command order for suckers should be changed based on what
 * the drivers want -> could use a command group
 *
 */
public class AngleCollectorIn extends Command {

    private final Collector m_collector;

    public AngleCollectorIn(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    @Override
    protected void initialize() {
        // Only needs to run once to move collector pneumatic in
        m_collector.collectorIn();
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
