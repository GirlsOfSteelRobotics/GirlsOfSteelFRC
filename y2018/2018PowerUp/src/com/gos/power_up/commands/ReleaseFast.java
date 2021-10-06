package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseFast extends Command {
    private final Collector m_collector;

    private final double m_speed;

    public ReleaseFast(Collector collector) {
        this(collector, 0.9);
    }

    public ReleaseFast(Collector collector, double s) {
        m_speed = s;
        m_collector = collector;
        requires(m_collector);
    }


    @Override
    protected void initialize() {
        System.out.println("Release");
    }


    @Override
    protected void execute() {
        m_collector.release(m_speed);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_collector.stop();
    }


}
