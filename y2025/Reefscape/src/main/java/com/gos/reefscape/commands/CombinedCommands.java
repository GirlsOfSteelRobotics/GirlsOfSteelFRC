package com.gos.reefscape.commands;

import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.PIESetpoint;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import java.util.function.Consumer;

public class CombinedCommands {
    private static final PIESetpoint PRE_NET_FLING_SETPOINT = new PIESetpoint(PIEAlgae.SCORE_INTO_NET.m_setpoint.m_height, -134);
    private static final PIESetpoint POST_L4_HEIGHT = new PIESetpoint(1.41, -50);
    private static final PIESetpoint GO_HOME_SETPOINT = new PIESetpoint(0, PivotSubsystem.DEFAULT_ANGLE);

    private final CoralSubsystem m_coralSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PivotSubsystem m_pivotSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final Consumer<KeepOutZoneEnum> m_keepoutConsumer;

    public CombinedCommands(CoralSubsystem coral, ElevatorSubsystem elevator, PivotSubsystem pivot, Consumer<KeepOutZoneEnum> consumer, ChassisSubsystem chassis) {
        m_coralSubsystem = coral;
        m_elevatorSubsystem = elevator;
        m_pivotSubsystem = pivot;
        m_keepoutConsumer = consumer;
        m_chassisSubsystem = chassis;
    }

    public Command startLoweringElevatorForAWhile() {
        return goHome().until(() -> m_elevatorSubsystem.getHeight() < 0.6);
    }

    public Command autoPieCommand(PIESetpoint combo) {
        return new KeepoutZonesCommand(m_elevatorSubsystem, m_pivotSubsystem, m_keepoutConsumer, combo, false);
    }

    public Command pieCommand(PIESetpoint combo) {
        return new KeepoutZonesCommand(m_elevatorSubsystem, m_pivotSubsystem, m_keepoutConsumer, combo, true);

    }

    public Command autoScoreCoralCommand(PIECoral combo) {

        if (combo == PIECoral.L4) {
            return autoPieCommand(PIECoral.L4.m_setpoint)
                .andThen(m_coralSubsystem.createScoreCoralCommand()
                    .withTimeout(.1))
                .andThen(autoPieCommand(POST_L4_HEIGHT)
                    .raceWith(m_coralSubsystem.createScoreCoralCommand()));
        }

        return autoPieCommand(combo.m_setpoint)
            .andThen(m_coralSubsystem.createScoreCoralCommand()
                .withTimeout(.75));
    }


    public Command createScoreCoralCommand(PIECoral setpoint) {
        if (setpoint == PIECoral.L4) {
            return m_coralSubsystem.createScoreCoralCommand().withTimeout(0.2)
                .andThen(pieCommand(POST_L4_HEIGHT).alongWith(m_coralSubsystem.createScoreCoralCommand()));

        }
        return m_coralSubsystem.createScoreCoralCommand();
    }

    public Command prepScoreCoralCommand(PIECoral combo) {
        return pieCommand(combo.m_setpoint);
    }


    public Command autoScoreAlgaeCommand(PIEAlgae combo) {
        return autoPieCommand(combo.m_setpoint)
            .andThen(m_coralSubsystem.createMoveAlgaeOutCommand().withTimeout(1));
    }

    public Command autoScoreAlgaeInProcessorCommand() {
        return autoPieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint)
            .andThen(m_coralSubsystem.createMoveAlgaeOutCommand().withTimeout(0.5));
    }

    public Command prepAlgaeInProcessorCommand() {
        return pieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint)
            .alongWith(m_coralSubsystem.createMoveAlgaeInCommand());
    }

    public Command autoScoreAlgaeInNet() {
        return autoPieCommand(PRE_NET_FLING_SETPOINT).deadlineFor(holdAlgaeWhileDriving())
            .andThen(autoPieCommand(PIEAlgae.SCORE_INTO_NET.m_setpoint)
                .alongWith(
                    new WaitUntilCommand(() -> m_pivotSubsystem.getRelativeAngle() > -115)
                        .andThen(m_coralSubsystem.createMoveAlgaeOutCommand().withTimeout(1.5))));
    }

    public Command scoreAlgaeInNet() {
        // Due to how we score in the net, we can always run with the timeout.
        return autoScoreAlgaeInNet();
    }

    public boolean isAtGoalHeightAngle() {
        return m_elevatorSubsystem.isAtGoalHeight() && m_pivotSubsystem.isAtGoalAngle();
    }



    public Command autoFetchPieceFromHPStation() {
        return autoPieCommand(PIECoral.HUMAN_PLAYER_STATION.m_setpoint)
            .andThen(m_coralSubsystem.createIntakeUntilCoralCommand()
                );
    }

    public Command fetchPieceFromHPStation() {
        return m_coralSubsystem.createIntakeUntilCoralCommand()
            .raceWith(pieCommand(PIECoral.HUMAN_PLAYER_STATION.m_setpoint));
    }



    public Command autoFetchAlgae(PIEAlgae algaePosition) {
        return autoPieCommand(algaePosition.m_setpoint)
            .alongWith(m_coralSubsystem.createMoveAlgaeInCommand()
                .withTimeout(0.75));
    }

    public Command fetchAlgae(PIEAlgae algaePosition) {
        return pieCommand(algaePosition.m_setpoint)
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
        return autoPieCommand(GO_HOME_SETPOINT);
    }

    public Command holdAlgaeWhileDriving() {
        return m_coralSubsystem.createMoveAlgaeInCommand();
    }

    public void createCombinedCommand(boolean inComp) {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Combined Commands");
        if (!inComp) {
            debugTab.add(autoScoreCoralCommand(PIECoral.L1).withName("Level One"));
            debugTab.add(autoScoreCoralCommand(PIECoral.L2).withName("Level Two"));
            debugTab.add(autoScoreCoralCommand(PIECoral.L3).withName("Level Three"));
            debugTab.add(autoScoreCoralCommand(PIECoral.L4).withName("Level Four"));
            debugTab.add(autoScoreAlgaeCommand(PIEAlgae.SCORE_INTO_NET).withName("Score Into net"));
            debugTab.add(autoScoreAlgaeCommand(PIEAlgae.SCORE_INTO_PROCESSOR).withName("Score into processor"));
            debugTab.add(autoFetchPieceFromHPStation().withName("human player station"));
            debugTab.add(autoFetchAlgae(PIEAlgae.FETCH_ALGAE_2).withName("fetch algae two! :)"));
            debugTab.add(autoFetchAlgae(PIEAlgae.FETCH_ALGAE_3).withName("fetch algae three! :)"));
            debugTab.add(goHome().withName("go home"));
        }

        debugTab.add(moveElevatorAndPivotToTunablePosition().withName("elevator and pivot to tunable position"));
        debugTab.add(elevatorPivotToCoast().withName("pivot & elevator to coast"));

    }

    public Command createShimmyAndIntakeCommand() {
        return m_chassisSubsystem.createShakeChassisCommand()
            .alongWith(m_coralSubsystem.createIntakeUntilCoralCommand());
    }
}
