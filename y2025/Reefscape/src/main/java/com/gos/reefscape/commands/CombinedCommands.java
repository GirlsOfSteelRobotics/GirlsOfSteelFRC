package com.gos.reefscape.commands;

import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.subsystems.AlgaeSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class CombinedCommands {
    private final CoralSubsystem m_coralSubsystem;
    private final AlgaeSubsystem m_algaeSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PivotSubsystem m_pivotSubsystem;

    public CombinedCommands(AlgaeSubsystem algae, CoralSubsystem coral, ElevatorSubsystem elevator, PivotSubsystem pivot) {
        m_coralSubsystem = coral;
        m_algaeSubsystem = algae;
        m_elevatorSubsystem = elevator;
        m_pivotSubsystem = pivot;
    }

    public Command pieCommand(PIECoral combo) {
        return m_elevatorSubsystem.createMoveElevatorToHeightCommand(combo.m_height)
            .alongWith(m_pivotSubsystem.createMoveArmtoAngleCommand(combo.m_angle))
            .until(this::isAtGoalHeightAngle);
    }

    public Command pieCommand(PIEAlgae combo) {
        return m_elevatorSubsystem.createMoveElevatorToHeightCommand(combo.m_height)
            .alongWith(m_pivotSubsystem.createMoveArmtoAngleCommand(combo.m_angle))
            .until(this::isAtGoalHeightAngle);
    }

    public Command scoreCoralCommand(PIECoral combo) {
        return pieCommand(combo).andThen(m_coralSubsystem.createMoveCoralOutCommand().withTimeout(1));
    }

    public Command scoreAlgaeCommand(PIEAlgae combo) {
        return pieCommand(combo).andThen(m_algaeSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }

    public Command scoreAlgaeInProcessorCommand() {
        return pieCommand(PIEAlgae.SCORE_INTO_PROCESSOR).andThen(m_algaeSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }


    public boolean isAtGoalHeightAngle() {
        return m_elevatorSubsystem.isAtGoalHeight() && m_pivotSubsystem.isAtGoalAngle();
    }


    public Command fetchPieceFromHPStation() {
        return pieCommand(PIECoral.HUMAN_PLAYER_STATION)
            .andThen(m_coralSubsystem.createMoveCoralInCommand()
                .withTimeout(2));
    }



    public Command fetchAlgae(PIEAlgae algaePosition) {
        return pieCommand(algaePosition)
            .andThen(m_algaeSubsystem.createMoveAlgaeInCommand()
                .withTimeout(2));
    }



}
