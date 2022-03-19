package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.CollectorSubsystem;


public class ChangeCollectorDownAngleCommand extends CommandBase {
    private final CollectorSubsystem m_collector;

    public ChangeCollectorDownAngleCommand(CollectorSubsystem collectorSubsystem) {
        this.m_collector = collectorSubsystem;

        addRequirements(this.m_collector);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double currentAngle = m_collector.getIntakeLeftAngleDegrees();
        m_collector.pivotToMagicAngle();
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
