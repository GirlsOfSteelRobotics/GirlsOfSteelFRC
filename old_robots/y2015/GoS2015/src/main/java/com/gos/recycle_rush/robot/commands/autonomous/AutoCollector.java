package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/**
 *
 */
public class AutoCollector extends Command {

    private final Collector m_collector;

    public AutoCollector(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setTimeout(2.5);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_collector.collectorToteIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_collector.stopCollecting();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
