package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.subsystems.CollectorSubsystem;


public class RollerInCommand extends Command {
    private final CollectorSubsystem m_collector;

    public RollerInCommand(CollectorSubsystem collectorSubsystem) {
        this.m_collector = collectorSubsystem;

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_collector.rollerIn();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.rollerStop();

    }
}
