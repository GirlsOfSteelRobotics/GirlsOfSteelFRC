package com.gos.reefscape.commands;

import com.gos.reefscape.PIE;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.IntakeSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class CombinedCommands {
    private final IntakeSubsystem m_intake;
    private final ElevatorSubsystem m_elevator;
    private final PivotSubsystem m_pivot;

    public CombinedCommands(IntakeSubsystem intake, ElevatorSubsystem elevator, PivotSubsystem pivot) {
        m_intake = intake;
        m_elevator = elevator;
        m_pivot = pivot;
    }

    public Command scoreCoralCommand(PIE combo) {
        return m_elevator.createMoveElevatorToHeightCommand(combo.m_height)
            .alongWith(m_pivot.createMoveArmtoAngleCommand(combo.m_angle))
            .andThen(m_intake.createMoveIntakeOutCommand());

    }

}
