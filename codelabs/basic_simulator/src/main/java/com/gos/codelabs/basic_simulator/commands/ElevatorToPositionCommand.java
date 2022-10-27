package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorToPositionCommand extends CommandBase {
    private final ElevatorSubsystem m_lift;
    private final double m_goal;
    private final boolean m_holdAtPosition;
    private boolean m_finished;


    public ElevatorToPositionCommand(ElevatorSubsystem elevator, ElevatorSubsystem.Positions position) {
        this(elevator, position.m_heightMeters);
    }

    public ElevatorToPositionCommand(ElevatorSubsystem lift, double position) {
        this(lift, position, true);
    }

    public ElevatorToPositionCommand(ElevatorSubsystem lift, double position, boolean holdAtPosition) {
        m_lift = lift;
        m_goal = position;
        m_holdAtPosition = holdAtPosition;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        // TODO implement
    }

    @Override
    public boolean isFinished() {
        // TODO implement
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO implement
    }
}
