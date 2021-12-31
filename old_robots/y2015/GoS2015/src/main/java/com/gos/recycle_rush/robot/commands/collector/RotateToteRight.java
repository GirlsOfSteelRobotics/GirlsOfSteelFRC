package com.gos.recycle_rush.robot.commands.collector;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/**
 *
 */
public class RotateToteRight extends Command {

    private final Collector m_collector;

    public RotateToteRight(Collector collector) {
        m_collector = collector;
        requires(m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_collector.collectorToteRotateRight();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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
