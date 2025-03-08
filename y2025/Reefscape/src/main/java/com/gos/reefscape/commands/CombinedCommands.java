package com.gos.reefscape.commands;

import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.PIESetpoint;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class CombinedCommands {
    private final CoralSubsystem m_coralSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PivotSubsystem m_pivotSubsystem;

    public CombinedCommands(CoralSubsystem coral, ElevatorSubsystem elevator, PivotSubsystem pivot) {
        m_coralSubsystem = coral;
        m_elevatorSubsystem = elevator;
        m_pivotSubsystem = pivot;
    }

    public Command autoPieCommand(PIESetpoint combo) {
        return m_pivotSubsystem.createMovePivotToAngleCommand(combo.m_angle)
            .until(m_pivotSubsystem::isAtGoalAngle)
            .andThen(m_elevatorSubsystem.createMoveElevatorToHeightCommand(combo.m_height))
            .until(m_elevatorSubsystem::isAtGoalHeight);
    }

    public Command pieCommand(PIESetpoint combo) {
        return m_pivotSubsystem.createMovePivotToAngleCommand(combo.m_angle)
            .alongWith(new WaitUntilCommand(m_pivotSubsystem::isAtGoalAngle)
                .andThen(m_elevatorSubsystem.createMoveElevatorToHeightCommand(combo.m_height)));

    }

    public Command autoScoreCoralCommand(PIECoral combo) {

        if (combo == PIECoral.L4) {
            return autoPieCommand(new PIESetpoint(PIECoral.L4.m_setpoint.m_height, PIECoral.L2.m_setpoint.m_angle))
                .andThen(m_elevatorSubsystem.createMoveElevatorToHeightCommand(PIECoral.L4.m_setpoint.m_height).until(m_elevatorSubsystem::isAtGoalHeight))
                .andThen(autoPieCommand(PIECoral.L4.m_setpoint))
                    .andThen(m_coralSubsystem.createScoreCoralCommand()
                        .withTimeout(2));
        }

        return autoPieCommand(combo.m_setpoint)
            .andThen(m_coralSubsystem.createScoreCoralCommand()
                .withTimeout(2));
    }

    public Command scoreCoralCommand(PIECoral combo) {

        if (combo == PIECoral.L4) {
            return autoPieCommand(new PIESetpoint(PIECoral.L4.m_setpoint.m_height, PIECoral.L2.m_setpoint.m_angle))
                .andThen(m_elevatorSubsystem.createMoveElevatorToHeightCommand(PIECoral.L4.m_setpoint.m_height).until(m_elevatorSubsystem::isAtGoalHeight))
                .andThen(autoPieCommand(PIECoral.L4.m_setpoint));
        }

        return autoPieCommand(combo.m_setpoint);
    }


    public Command autoScoreAlgaeCommand(PIEAlgae combo) {
        return autoPieCommand(combo.m_setpoint).andThen(m_coralSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }

    public Command autoScoreAlgaeInProcessorCommand() {
        return autoPieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint).andThen(m_coralSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }

    public Command scoreAlgaeInProcessorCommand() {
        return pieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint).andThen(m_coralSubsystem.createMoveAlgaeOutCommand());
    }

    public Command autoScoreAlgaeInNet() {
        return autoPieCommand(PIEAlgae.SCORE_INTO_NET.m_setpoint).andThen(m_coralSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }
    public Command scoreAlgaeInNet() {
        return pieCommand(PIEAlgae.SCORE_INTO_NET.m_setpoint).andThen(m_coralSubsystem.createMoveAlgaeOutCommand());
    }
    //

    public boolean isAtGoalHeightAngle() {
        return m_elevatorSubsystem.isAtGoalHeight() && m_pivotSubsystem.isAtGoalAngle();
    }



    public Command autoFetchPieceFromHPStation() {
        return autoPieCommand(PIECoral.HUMAN_PLAYER_STATION.m_setpoint)
            .andThen(m_coralSubsystem.createFetchCoralCommand()
                .withTimeout(2));
    }

    public Command fetchPieceFromHPStation() {
        return pieCommand(PIECoral.HUMAN_PLAYER_STATION.m_setpoint)
            .alongWith(m_coralSubsystem.createIntakeUntilCoralCommand());
    }



    public Command autoFetchAlgae(PIEAlgae algaePosition) {
        return autoPieCommand(algaePosition.m_setpoint)
            .alongWith(m_coralSubsystem.createMoveAlgaeInCommand()
                .withTimeout(2));
    }

    public Command fetchAlgae(PIEAlgae algaePosition) {
        return autoPieCommand(algaePosition.m_setpoint)
            .alongWith(m_coralSubsystem.createMoveAlgaeInCommand());
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
        return m_elevatorSubsystem.createMoveElevatorToHeightCommand(0).until(m_elevatorSubsystem::isAtGoalHeight)
            .andThen(m_pivotSubsystem.createMovePivotToAngleCommand(PivotSubsystem.DEFAULT_ANGLE));
    }

    public void createCombinedCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Combined Commands");
        debugTab.add(autoScoreCoralCommand(PIECoral.L1).withName("Level One"));
        debugTab.add(autoScoreCoralCommand(PIECoral.L2).withName("Level Two"));
        debugTab.add(autoScoreCoralCommand(PIECoral.L3).withName("Level Three"));
        debugTab.add(autoScoreCoralCommand(PIECoral.L4).withName("Level Four"));
        debugTab.add(autoScoreAlgaeCommand(PIEAlgae.SCORE_INTO_NET).withName("Score Into net"));
        debugTab.add(autoScoreAlgaeCommand(PIEAlgae.SCORE_INTO_PROCESSOR).withName("Score into processor"));
        debugTab.add(autoFetchPieceFromHPStation().withName("human player station"));
        debugTab.add(autoFetchAlgae(PIEAlgae.FETCH_ALGAE_2).withName("fetch algae two! :)"));
        debugTab.add(autoFetchAlgae(PIEAlgae.FETCH_ALGAE_3).withName("fetch algae three! :)"));
        debugTab.add(moveElevatorAndPivotToTunablePosition().withName("elevator and pivot to tunable position"));
        debugTab.add(elevatorPivotToCoast().withName("pivot & elevator to coast"));
        debugTab.add(goHome().withName("go home"));

    }
}
