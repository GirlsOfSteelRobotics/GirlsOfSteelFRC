package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.CollectorSubsystem;


public class CollectorUpCommand extends CommandBase {
    private final CollectorSubsystem m_collector;

    public CollectorUpCommand(CollectorSubsystem collectorSubsystem) {
        this.m_collector = collectorSubsystem;
        addRequirements(this.m_collector);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_collector.collectorUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.collectorUp();
    }
}
