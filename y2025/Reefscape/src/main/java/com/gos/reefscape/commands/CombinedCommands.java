package com.gos.reefscape.commands;

import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.PIESetpoint;
import com.gos.reefscape.subsystems.AlgaeSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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

    public Command pieCommand(PIESetpoint combo) {
        return m_pivotSubsystem.createMovePivotToAngleCommand(combo.m_angle)
            .until(m_pivotSubsystem::isAtGoalAngle)
            .andThen(m_elevatorSubsystem.createMoveElevatorToHeightCommand(combo.m_height))
            .until(m_elevatorSubsystem::isAtGoalHeight);
    }

    public Command scoreCoralCommand(PIECoral combo) {

        if (combo == PIECoral.L4) {
            return pieCommand(new PIESetpoint(PIECoral.L4.m_setpoint.m_height, PIECoral.L2.m_setpoint.m_angle))
                .andThen(m_elevatorSubsystem.createMoveElevatorToHeightCommand(PIECoral.L4.m_setpoint.m_height).until(m_elevatorSubsystem::isAtGoalHeight))
                .andThen(pieCommand(PIECoral.L4.m_setpoint))
                    .andThen(m_coralSubsystem.createMoveCoralOutCommand()
                        .withTimeout(2));
        }

        return pieCommand(combo.m_setpoint)
            .andThen(m_coralSubsystem.createMoveCoralOutCommand()
                .withTimeout(2));
    }

    public Command scoreAlgaeCommand(PIEAlgae combo) {
        return pieCommand(combo.m_setpoint).andThen(m_algaeSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }

    public Command scoreAlgaeInProcessorCommand() {
        return pieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint).andThen(m_algaeSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }



    //
    public Command scoreAlgaeInNet() {
        return pieCommand(PIEAlgae.SCORE_INTO_NET.m_setpoint).andThen(m_algaeSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }
    //

    public boolean isAtGoalHeightAngle() {
        return m_elevatorSubsystem.isAtGoalHeight() && m_pivotSubsystem.isAtGoalAngle();
    }


    public Command fetchPieceFromHPStation() {
        return pieCommand(PIECoral.HUMAN_PLAYER_STATION.m_setpoint)
            .andThen(m_coralSubsystem.createMoveCoralInCommand()
                .withTimeout(2));
    }



    public Command fetchAlgae(PIEAlgae algaePosition) {
        return pieCommand(algaePosition.m_setpoint)
            .andThen(m_algaeSubsystem.createMoveAlgaeInCommand()
                .withTimeout(2));
    }

    public Command moveElevatorAndPivotToTunablePosition() {
        return m_pivotSubsystem.createPivotToTunableAngleCommand()
            .alongWith(m_elevatorSubsystem.createELevatorToTunableHeightCommand());
    }


    public Command elevatorPivotToCoast() {
        return m_elevatorSubsystem.createElevatorToCoastModeCommand()
            .alongWith(m_pivotSubsystem.createPivotoCoastModeCommand());
    }

    public Command goHome() {
        return m_elevatorSubsystem.createMoveElevatorToHeightCommand(0)
            .andThen(m_pivotSubsystem.createMovePivotToAngleCommand(PivotSubsystem.DEFAULT_ANGLE));
    }

    public void createCombinedCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Combined Commands");
        debugTab.add(scoreCoralCommand(PIECoral.L1).withName("Level One"));
        debugTab.add(scoreCoralCommand(PIECoral.L2).withName("Level Two"));
        debugTab.add(scoreCoralCommand(PIECoral.L3).withName("Level Three"));
        debugTab.add(scoreCoralCommand(PIECoral.L4).withName("Level Four"));
        debugTab.add(scoreAlgaeCommand(PIEAlgae.SCORE_INTO_NET).withName("Score Into net"));
        debugTab.add(scoreAlgaeCommand(PIEAlgae.SCORE_INTO_PROCESSOR).withName("Score into processor"));
        debugTab.add(fetchPieceFromHPStation().withName("human player station"));
        debugTab.add(fetchAlgae(PIEAlgae.FETCH_ALGAE_2).withName("fetch algae two! :)"));
        debugTab.add(fetchAlgae(PIEAlgae.FETCH_ALGAE_3).withName("fetch algae three! :)"));
        debugTab.add(moveElevatorAndPivotToTunablePosition().withName("elevator and pivot to tunable position"));
        debugTab.add(elevatorPivotToCoast().withName("pivot & elevator to coast"));
        debugTab.add(goHome().withName("go home"));

    }
}
