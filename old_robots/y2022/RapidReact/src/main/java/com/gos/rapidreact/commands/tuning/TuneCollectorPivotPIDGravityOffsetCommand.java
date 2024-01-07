package com.gos.rapidreact.commands.tuning;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.subsystems.CollectorSubsystem;

/**
 * figure out extra voltage to overcome the force of gravity to get collector pivot to move
 */

public class TuneCollectorPivotPIDGravityOffsetCommand extends Command {
    private final CollectorSubsystem m_collector;

    public TuneCollectorPivotPIDGravityOffsetCommand(CollectorSubsystem collectorSubsystem) {
        this.m_collector = collectorSubsystem;
        addRequirements(this.m_collector);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_collector.tuneGravityOffset();
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
