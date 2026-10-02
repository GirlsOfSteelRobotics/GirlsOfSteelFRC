package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ElevatorToPositionCommand extends Command {
    private final ElevatorSubsystem m_lift;
    private final double m_goal;
    private final boolean m_holdAtPosition;
    private boolean m_finished;


    public ElevatorToPositionCommand(ElevatorSubsystem elevator, ElevatorSubsystem.Positions position) {
        this(elevator, position, false);
    }

    public ElevatorToPositionCommand(ElevatorSubsystem elevator, ElevatorSubsystem.Positions position, boolean holdAtPosition) {
        this(elevator, position.m_heightMeters, holdAtPosition);
    }

    public ElevatorToPositionCommand(ElevatorSubsystem lift, double position) {
        this(lift, position, false);
    }

    /**
     * Moves the elevator to a height.
     *
     * @param lift The elevator subsystem
     * @param position The goal height, in meters
     * @param holdAtPosition If false, the command finishes once the elevator reaches the goal.
     *                       If true, the command never finishes on its own, and keeps holding the elevator at
     *                       the goal height until it is interrupted (for example, by letting go of a button)
     */
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
