package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class ReleaseSlow extends Command {
    private final Collector m_collector;

    public ReleaseSlow(Collector collector) {
        m_collector = collector;
        addRequirements(m_collector);
    }


    @Override
    public void initialize() {
    }


    @Override
    public void execute() {
        m_collector.release(0.5); //TODO: test this value
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_collector.stop();
    }


}
