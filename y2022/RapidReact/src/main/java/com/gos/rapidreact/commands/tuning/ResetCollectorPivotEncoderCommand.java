package com.gos.rapidreact.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.CollectorSubsystem;


public class ResetCollectorPivotEncoderCommand extends CommandBase {
    private final CollectorSubsystem m_collectorSubsystem;

    public ResetCollectorPivotEncoderCommand(CollectorSubsystem collectorSubsystem) {
        this.m_collectorSubsystem = collectorSubsystem;
        addRequirements(this.m_collectorSubsystem);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {

    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        m_collectorSubsystem.resetPivotEncoder();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
