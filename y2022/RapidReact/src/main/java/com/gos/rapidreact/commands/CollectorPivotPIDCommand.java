package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.CollectorSubsystem;


public class CollectorPivotPIDCommand extends CommandBase {
    private final CollectorSubsystem m_collector;
    private final double m_pivotAngle;

    public CollectorPivotPIDCommand(CollectorSubsystem collectorSubsystem, double pivotAngleDegrees) {
        this.m_collector = collectorSubsystem;
        m_pivotAngle = pivotAngleDegrees;

        addRequirements(this.m_collector);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_collector.collectorToAngle(m_pivotAngle);

    }

    @Override
    public boolean isFinished() {
        double error = Math.abs(m_pivotAngle - m_collector.getIntakeLeftAngleDegrees());
        //System.out.println(error);
        return error < CollectorSubsystem.ALLOWABLE_ERROR_DEG;
    }

    @Override
    public void end(boolean interrupted) {
        m_collector.pivotStop();
    }
}
