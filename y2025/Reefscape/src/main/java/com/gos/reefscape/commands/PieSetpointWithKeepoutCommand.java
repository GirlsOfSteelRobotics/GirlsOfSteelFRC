package com.gos.reefscape.commands;

import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
//hi hanying 😎👌😱😍😂

public class PieSetpointWithKeepoutCommand extends Command {
    public ElevatorSubsystem m_elevatorSubsystem;
    public PivotSubsystem m_pivotSubsystem;
    /*
    pivot cannot be straight when elevator is past certain height

     */

    public PieSetpointWithKeepoutCommand(ElevatorSubsystem elevator, PivotSubsystem pivot) {
        m_elevatorSubsystem = elevator;
        m_pivotSubsystem = pivot;

        addRequirements(m_elevatorSubsystem, m_pivotSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {

    }

    @Override
    public void end(boolean interrupted) {

    }
}
//bye hanyinginginginginginginginging