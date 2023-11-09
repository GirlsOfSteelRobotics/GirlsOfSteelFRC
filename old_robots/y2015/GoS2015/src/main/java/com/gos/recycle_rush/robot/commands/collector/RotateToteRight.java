package com.gos.recycle_rush.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Collector;

/**
 *
 */
public class RotateToteRight extends Command {

    private final Collector m_collector;

    public RotateToteRight(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_collector.collectorToteRotateRight();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollecting();
    }


}
