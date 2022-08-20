package com.gos.codelabs.pid.commands;

import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorToPositionCommand extends CommandBase {

    private final ElevatorSubsystem m_lift;
    private final double m_position;
    private final boolean m_holdAtPosition;
    private boolean m_finished;

    public ElevatorToPositionCommand(ElevatorSubsystem lift, ElevatorSubsystem.Positions position) {
        this(lift, position, false);
    }

    public ElevatorToPositionCommand(ElevatorSubsystem lift, ElevatorSubsystem.Positions position, boolean holdAtPosition) {
        this(lift, position.m_heightMeters, holdAtPosition);
    }

    public ElevatorToPositionCommand(ElevatorSubsystem lift, double position, boolean holdAtPosition) {
        m_lift = lift;
        m_position = position;
        m_holdAtPosition = holdAtPosition;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        m_finished = m_lift.goToPosition(m_position);
    }

    @Override
    public boolean isFinished() {
        return !m_holdAtPosition && m_finished;
    }

    @Override
    public void end(boolean interrupted) {
        m_lift.setSpeed(0);
    }
}
