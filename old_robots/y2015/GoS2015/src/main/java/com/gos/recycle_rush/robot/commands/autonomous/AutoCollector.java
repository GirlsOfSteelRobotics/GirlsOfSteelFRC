package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Collector;

/**
 *
 */
public class AutoCollector extends CommandBase {

    private final Collector m_collector;
    private final Timer m_timer;

    public AutoCollector(Collector collector) {
        m_collector = collector;
        m_timer = new Timer();
        addRequirements(m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_collector.collectorToteIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(2.5);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_collector.stopCollecting();
        m_timer.stop();
    }


}
