package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Collector;
import edu.wpi.first.wpilibj2.command.Command;

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
        addRequirements(m_collector);
    }


    @Override
    public void initialize() {
        System.out.println("Release");
    }


    @Override
    public void execute() {
        m_collector.release(m_speed);
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
