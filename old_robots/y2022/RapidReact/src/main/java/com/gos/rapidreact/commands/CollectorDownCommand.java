package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.subsystems.CollectorSubsystem;


public class CollectorDownCommand extends Command {
    private final CollectorSubsystem m_collector;

    public CollectorDownCommand(CollectorSubsystem collectorSubsystem) {
        this.m_collector = collectorSubsystem;
        addRequirements(this.m_collector);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_collector.collectorDown();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.pivotStop();
    }
}
